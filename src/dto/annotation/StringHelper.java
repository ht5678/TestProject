package dto.annotation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class StringHelper {

	public static Object str2obj(Class<?> clazz , String str){
		if(str == null){
			return null;
		}
		//布尔值转换
		try{
			if(Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)){
				return Boolean.valueOf(str);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new RuntimeException("布尔值格式转换错误");
		}
		//整数类型转换
		try{
			if(Integer.class.equals(clazz) || Integer.TYPE.equals(clazz)){
				return Integer.valueOf(str);
			}
			if(Long.class.equals(clazz) || Long.TYPE.equals(clazz)){
				return Long.valueOf(str);
			}
			if(Short.class.equals(clazz) || Short.TYPE.equals(clazz)){
				return Short.valueOf(str);
			}
			if(clazz.isEnum()){
				return clazz.getEnumConstants()[Integer.valueOf(str).intValue()];
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		//数值类型转换
		try{
			if(Double.class.equals(clazz) || Double.TYPE.equals(clazz)){
				return Double.valueOf(str);
			}
			if(Float.class.equals(clazz) || Float.TYPE.equals(clazz)){
				return Float.valueOf(str);
			}
			if(BigDecimal.class.equals(clazz)){
				return new BigDecimal(str);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new RuntimeException("数值格式转换错误");
		}
		
		String format = null;
		try{
			if(Date.class.equals(clazz)){
				if(str.equalsIgnoreCase("&nbsp;")){
					return null;
				}
				switch (str.length()) {
				case 4:
					format = "yyyy";
					return new SimpleDateFormat(format).parse(str);
				case 7:
					format = "yyyy-MM";
					return new SimpleDateFormat(format).parse(str);
				case 6:
					format = "yyyyMM";
					return new SimpleDateFormat(format).parse(str);
				case 10:
					format = "yyyy-MM-dd";
					return new SimpleDateFormat(format).parse(str);
				case 8:
					format = "yyyyMMdd";
					return new SimpleDateFormat(format).parse(str);
				case 13:
					format = "yyyy-MM-dd HH";
					return new SimpleDateFormat(format).parse(str);
				case 11:
			          format = "yyyyMMdd HH";
			          return new SimpleDateFormat(format).parse(str);
			    case 16:
			          format = "yyyy-MM-dd HH:mm";
			          return new SimpleDateFormat(format).parse(str);
			    case 19:
			          format = "yyyy-MM-dd HH:mm:ss";
			          return new SimpleDateFormat(format).parse(str);
			        case 5:
			        case 9:
			        case 12:
			        case 14:
			        case 15:
			        case 17:
			        case 18:}return null;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return str;
	}
}
