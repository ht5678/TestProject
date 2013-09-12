package dto.annotation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateHelper {

	/**
	 * 将属性根据表达式转换成date类型
	 * @param changeDate	属性值
	 * @param pattern		表达式
	 * @return
	 */
	public static String formatDateToString(Date changeDate , String pattern){
		if(changeDate == null){
			return null;
		}
		return new SimpleDateFormat(pattern).format(changeDate);
	}
	
	/**
	 * 将属性根据正则表达式匹配返回date
	 * @param changeDate
	 * @return
	 * @throws ParseException
	 */
	private static Date autoFormatStringToDate(String changeDate) throws ParseException{
		if(changeDate == null){
			return null;
		}
		
		DateFormat dateFormat = null;
		if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
			dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
		}else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2}")){
			dateFormat = new SimpleDateFormat("yy-M-d");
		}else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")){
			dateFormat = new SimpleDateFormat("yy-M-d H:m");
		}else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}")){
			dateFormat = new SimpleDateFormat("yy-M-d H");
		}else {
		    dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
	    }
		return dateFormat.parse(changeDate);
	}
	
	/**
	 * 将属性值转换成date类型
	 * @param changeDate	需要转换的属性值
	 * @return
	 * @throws ParseException 
	 */
	public static Date autoFormatExtToDate(String changeDate) throws ParseException{
		if(changeDate == null){
			return null;
		}
		
		changeDate = changeDate.replaceAll("T", " ");
		return autoFormatStringToDate(changeDate);
	}
}
