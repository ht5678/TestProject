package dto.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyHelper {

	
	/**
	 * 返回propertyName属性的set方法
	 * @param clazz			实体类
	 * @param propertyName  属性名
	 * @return
	 */
	public static Method findSetMethod(Class<?> clazz , String propertyName){
		return findMethod(clazz, 1, new String[]{nameForPrefix(propertyName, "set")});
	}
	
	/**
	 *为普通属性set值 
	 * @param obj			实体
	 * @param name			属性名
	 * @param value			属性的类型
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setSimpleProperty(Object obj , String name , Object value) throws Exception, IllegalAccessException, InvocationTargetException{
		if(obj == null){
			return;
		}
		
		Class objClass = obj.getClass();
		Method method = findSetMethod(objClass , name);
		if(method == null){
			return;
		}
		Class clazz = method.getParameterTypes()[0];
		if(value==null || clazz.isInstance(value)){
			method.invoke(obj, new Object[]{value});
		}else if(value instanceof String){
			method.invoke(obj, new Object[]{StringHelper.str2obj(clazz , (String)value)});
		}
	}
	
	/**
	 * 为实体属性set值
	 * @param obj		实体
	 * @param name		属性名
	 * @param value		属性类型
	 * @throws Exception
	 */
	public static void setProperty(Object obj , String name , Object value)throws Exception{
		int index = name.lastIndexOf(".");
		if(index == -1){
			setSimpleProperty(obj , name , value);
			return;
		}
		
	}
	
	/**
	 * 取得obj实体中普通name属性的值
	 * @param obj			实体
	 * @param name			属性名
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getSimpleProperty(Object obj , String name) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(obj == null){
			return null;
		}
		Class clazz = obj.getClass();
		Method method = findGetMethod(clazz, name);
		if(method == null){
			throw new NoSuchMethodException(name);
		}
		System.out.println("取得obj实体中普通name属性的值:"+method.invoke(obj, null)+"   "+method.getName());
		return method.invoke(obj, null);
	}
	
	/**
	 * 取得实体中name属性的值
	 * @param obj		实体
	 * @param name		实体属性名
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getProperty(Object obj , String name) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		String[] names = name.split("\\.");
		Object ret = obj;
		for(int i = 0 ; i< names.length ; i++){
			ret = getSimpleProperty(ret , names[i]);
		}
		System.out.println("取得实体中name属性的值:"+ret);
		return ret;
	}
	
	/**
	 * 返回实体propertyName的属性类型
	 * @param obj			实体
	 * @param propertyName	属性名
	 * @return
	 */
	public static Class<?> getPropertyClass(Object  obj , String propertyName){
		return getClassPropertyClass(obj.getClass(), propertyName);
	}
	
	
	/**
	 * 组装方法名
	 * @param name			属性名
	 * @param prefix		set,get,is
	 * @return
	 */
	private static String nameForPrefix(String name , String prefix){
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(name.substring(0, 1).toUpperCase());
		sb.append(name.substring(1));
		return sb.toString();
	}
	
	/**
	 * 获取实体中的方法
	 * @param clazz					实体
	 * @param parameterLength		方法的参数长度
	 * @param methodNames			方法名数组（getXxx,isXxx,hasXxx）
	 * @return
	 */
	private static Method findMethod(Class<?> clazz , int parameterLength , String[] methodNames){
		Method ret = null;
		for(Method m : clazz.getMethods()){
			for(String methodName : methodNames){
				if(m.getName().equals(methodName) && (m.getParameterTypes().length == parameterLength)){
					if(m.getReturnType().equals(Object.class)){
						ret = m;
					}else{
						return m;
					}
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 根据条件在实体中查找get方法
	 * @param clazz			实体类
	 * @param propertyName	属性名
	 * @return
	 */
	public static Method findGetMethod(Class<?> clazz , String propertyName){
		return findMethod(clazz , 0 , new String[]{nameForPrefix(propertyName , "get"),
				nameForPrefix(propertyName,"is"),
				nameForPrefix(propertyName,"has")});
	}
	
	/**
	 * 获取实体中<p>普通属性</p>的类型
	 * @param objectClass		实体
	 * @param propertyName		属性名
	 * @return
	 */
	public static Class<?> getClassSimplePropertyClass(Class<?> objectClass , String propertyName){
		Method method = findGetMethod(objectClass , propertyName);
		if(method == null){
			return null;
		}
		System.out.println("获取实体中<p>普通属性</p>的类型:"+method.getName());
		return method.getReturnType();
	}
	
	/**
	 * 获取实体中属性的类型
	 * @param objectClass		实体
	 * @param propertyName		属性名
	 * @return
	 */
	public static Class<?> getClassPropertyClass(Class<?> objectClass , String propertyName){

		String[] names = propertyName.split("\\.");
		Class clazz = objectClass;
		for(int i = 0 ; i < names.length ; i++){
			clazz = getClassSimplePropertyClass(clazz , names[i]);
		}
		
		return clazz;
	}
	
	
}
