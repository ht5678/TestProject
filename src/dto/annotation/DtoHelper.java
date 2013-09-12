package dto.annotation;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public final class DtoHelper {

	private static final Map<Field, DtoProperty> DTO_PROPERTY_MAP = new ConcurrentHashMap<Field, DtoProperty>();
	private static final Map<Class<?>, DtoClass> DTO_CLASS_MAP = new ConcurrentHashMap<Class<?>, DtoClass>();
	
	/**
	 * 将entity实体转换成dto类
	 * @param <E>	实体类型
	 * @param <D>	dto类型
	 * @param entity	实体
	 * @param dto		dto
	 */
	public static <E,D> void dismantle(E entity , D dto){
		readClass(dto.getClass());
		
		for(Field field : dto.getClass().getDeclaredFields()){
			if(DTO_PROPERTY_MAP.containsKey(field)){
				DtoProperty dp = field.getAnnotation(DtoProperty.class);
				if(dp.entityClass().equals(entity.getClass())){
					try{
						if(!dp.readOnly()){
							Object value = PropertyHelper.getProperty(dto, field.getName());
							System.out.println("ge zhong value:"+value);
							if(value!=null || dp.nullable()){	//如果属性值不为空或者可以为空
								String property = dp.entityProperty();
								//如果没有设置entityProperty属性，默认设置property值
								if(property == null || property.length()==0){
									property = field.getName();
								}
								
								if((!property.equalsIgnoreCase("id")) && (property.length() <= 3)
										|| (!StringUtils.right(property, 3).equalsIgnoreCase(".id"))){
									
									//给String类型的 value去掉空格
									if(value instanceof String && dp.trim()){
										value = ((String)value).trim();
									}
									if((!field.getType().equals(List.class)) && (!field.getType().equals(Set.class))){
										if(isSame(field, entity, property)){
											PropertyHelper.setProperty(entity, property, value);
										}else if(field.getType().equals(String.class) && PropertyHelper.getPropertyClass(entity, property).equals(Date.class)){
											Date propValue = null;
											try{
												propValue = DateHelper.autoFormatExtToDate(value.toString());
											}catch(Exception e){
												propValue = null;
											}
											
											PropertyHelper.setProperty(entity, property, propValue);
											
										}else if(field.getType().equals(Date.class) && PropertyHelper.getPropertyClass(entity, property).equals(String.class)){
											String propValue = DateHelper.formatDateToString((Date)value, "yyyy-MM-dd HH:mm:ss");
											PropertyHelper.setProperty(entity, property, propValue);
										}
									}
								}
								
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
			}
		}
		
	}
	
	
	/**
	 * 判断field属性类型和实体中property属性的类型是否相同
	 * @param field			dto的声明字段
	 * @param entity		实体
	 * @param property		属性
	 * @return
	 * @throws Exception
	 */
	private static boolean isSame(Field field ,Object entity , String property)throws Exception{
		return field.getType().equals(PropertyHelper.getPropertyClass(entity, property));
	}
	
	
	/**
	 * 将map中的实体转换dto实体
	 * @param <D>		
	 * @param dto		dto类
	 * @param map		盛放实体的map
	 * @throws Exception
	 */
	private static <D> void buildDTO(D dto , Map<String, Object> map)throws Exception{
		readClass(dto.getClass());
		DtoClass dtoClass = DTO_CLASS_MAP.get(dto.getClass());
		if(dtoClass==null){
			return ;
		}
		for(Class dtoEntity : dtoClass.entities()){
			Object domainEntity = map.get(dtoEntity.getCanonicalName());
			if(domainEntity != null){
				for(Field dtoField : dto.getClass().getDeclaredFields()){
					System.out.println(dtoField);
					if(DTO_PROPERTY_MAP.get(dtoField)!=null){
						if(DTO_PROPERTY_MAP.get(dtoField).entityClass().equals(dtoEntity)){
							String property = DTO_PROPERTY_MAP.get(dtoField).entityProperty();
							if(property==null || property.length() == 0){
								property = dtoField.getName();
							}
							
							try{
								Object value = PropertyHelper.getProperty(domainEntity , property);
								if(value != null){
									if((!dtoField.getType().equals(List.class)) && (!dtoField.getType().equals(Set.class))){
										if(isSame(dtoField,domainEntity,property)){
											PropertyHelper.setProperty(dto , dtoField.getName() ,value );
										}else if((dtoField.getType().equals(String.class)) && PropertyHelper.getPropertyClass(domainEntity , property).equals(Date.class)){
											String propValue = DateHelper.formatDateToString((Date)value , "yyyy-MM-dd HH:mm:ss");
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										}else if((dtoField.getType().equals(Date.class)) && PropertyHelper.getPropertyClass(domainEntity , property).equals(String.class)){
											Date propValue = null;
											try{
												propValue = DateHelper.autoFormatExtToDate((String)value);
											}catch(Exception e){
												propValue = null;
											}
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										}
									}
								}
								
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 将entities 转换成 dtoBeanClass 类
	 * @param <D>
	 * @param dtoBeanClass	dto类
	 * @param entities		要转换的实体类
	 * @return
	 * @throws Exception
	 */
	public static <D> D build(Class<D> dtoBeanClass , Object... entities)throws Exception{
		if(entities == null){
			return null;
		}
		
		if(!dtoBeanClass.isAnnotationPresent(DtoClass.class)){
			throw new RuntimeException();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		for(Object o : entities){
			if(o!=null){
				map.put(o.getClass().getCanonicalName(), o);
			}
			
			try{
				Object dto = dtoBeanClass.newInstance();
				buildDTO(dto,map);
				return (D) dto;
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	
	/**
	 * 将dto类的类注解取得并放入DTO_CLASS_MAP中，将dto类中的属性放入到DTO_PROPERTY_MAP中
	 * @param dtoClass
	 */
	public static void readClass(Class<?> dtoClass){
		if(!DTO_CLASS_MAP.containsKey(dtoClass)){
			DtoClass dc = dtoClass.getAnnotation(DtoClass.class);
			if(dc != null){
				DTO_CLASS_MAP.put(dtoClass, dc);
			}
		}
		
		System.out.println("dto.getDeclaredFields:"+dtoClass.getDeclaredFields());
		
		for(Field f : dtoClass.getDeclaredFields()){
			if((f.isAnnotationPresent(DtoProperty.class))&& (!DTO_PROPERTY_MAP.containsKey(f))){
				DTO_PROPERTY_MAP.put(f, f.getAnnotation(DtoProperty.class));
			}
		}
		
	}
	
	/**
	 * 检测dto类和对应的实体是否匹配，返回匹配结果
	 * @param dtoClass
	 * @return
	 */
	public static boolean checkDto(Class<?> dtoClass){
		readClass(dtoClass);
		
		for(Field dtoField : dtoClass.getDeclaredFields()){
			if(DTO_PROPERTY_MAP.containsKey(dtoField)){
				DtoProperty dp = DTO_PROPERTY_MAP.get(dtoField);
				String property = dp.entityProperty();
				if((property==null)||(property.length()==0))
					property = dtoField.getName();
					
					try{
					Class dtoType = dtoField.getType();
					Class entityType = PropertyHelper.getClassPropertyClass(dp.entityClass(), property);
					if(!dtoType.equals(entityType)){
						if(dtoType.equals(Date.class) && entityType.equals(String.class))
							return true;
						if(dtoType.equals(String.class) && entityType.equals(Date.class))
							return true;
						System.out.println("属性---类型---错误");
						return false;
					}
					}catch(Exception e){
						System.out.println("属性---类型----异常");
						return false;
					}
			}
		}
		
		return true;
	}
	
}
