package utils;

import java.lang.reflect.Field;
import java.util.Date;

import domain.PackageType;

/**
 * model,store自动生成类
 * @author 岳志华
 */
public class AutoUtil {

	/**
	 * 返回属性的类型
	 * @param field
	 * @return
	 */
	private static String getFieldType(Field field){
		String type = "string";
		if(field.getType().isAssignableFrom(Integer.class)){
			type="int";
		}
		if(field.getType().isAssignableFrom(Date.class)){
			type="date";
		}
		return type;
	}
	
	/**
	 * 取得类的store名称
	 * @param clazzName
	 * @return
	 */
	private static String getStoreName(String clazzName){
		StringBuffer modelName = new StringBuffer(); 
		String n = clazzName.substring(0,1).toLowerCase()+clazzName.substring(1);
		modelName.append(n).append(".");
		modelName.append("store").append(".");
		modelName.append(n+"Store");
		return modelName.toString();
	}	
	
	/**
	 * 取得类的model名称
	 * @param clazzName
	 * @return
	 */
	private static String getModelName(String clazzName){
		StringBuffer modelName = new StringBuffer(); 
		String n = clazzName.substring(0,1).toLowerCase()+clazzName.substring(1);
		modelName.append(n).append(".");
		modelName.append("model").append(".");
		modelName.append(n+"Model");
		return modelName.toString();
	}
	
	/**
	 * 自动生成model
	 * @param clazz
	 * @return
	 */
	public static String buildModel(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		sb.append("Ext.define").append("('");
		sb.append(getModelName(clazz.getSimpleName()));
		sb.append("',{").append("\n");
		sb.append("extend:'Ext.data.Model',").append("\n");
		sb.append("fields").append(":[");
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			sb.append("{name:'"+field.getName()+"',type:'"+getFieldType(field)+"'");
			if(getFieldType(field).equals("date")){
				sb.append(",dateFormat:'Y-m-d H:i:s'");
			}
			sb.append("},\n");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		sb.append("]").append("\n");
		sb.append("});");
		return sb.toString();
	}
	
	/**
	 * 构造store文件
	 * @return
	 */
	public static String buildStore(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		sb.append("Ext.define").append("('");
		sb.append(getStoreName(clazz.getSimpleName()));
		sb.append("',{").append("\n");
		sb.append("extend:'Ext.data.Store',").append("\n");
		sb.append("model:'").append(getModelName(clazz.getSimpleName())).append("'\n");
		sb.append("});");
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		AutoUtil au = new AutoUtil();
//		System.out.println(au.buildModel(CompanyDto.class));
		System.out.println(au.buildModel(PackageType.class));
		System.out.println(au.buildStore(PackageType.class));
	}
	
}
