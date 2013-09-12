package test.test;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import dto.annotation.DtoHelper;

import utils.MD5;


public class test01 {

	private static Integer TEM_POSITIVE = 0;
	private static Integer TEM_NEGATIVE = 1;
	
	@Test
	public void testOne(){
		byte[] bytes = ((1024*200)+"").getBytes();
		System.out.println(bytes.length);
	}
	
	@Test
	public void testPass(){
		System.out.println(MD5.MD5Encode("p@ssw0rd"));
	}
	
	@Test
	public void testString(){
		String ds = "20020304";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		try{
			System.out.println(String.format(ds,"yyyy-MM-dd"));
			System.out.println(String.format(new Date().toString(),"MM"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 温度转二进制测试
	 */
	@Test
	public void test1(){
		String firstUpDoorTem = "11";
		String secondUpDoorTem = "00";
		//温度 前两位转为2进制，补位8，看最高位，0是正，1是负，然后把后面的数转成10进制a,再把a14转成10进制
		String firstValue = Integer.toBinaryString(Integer.parseInt(firstUpDoorTem,16));
		//补位
		while(firstValue.length() <8 ){
			firstValue = 0 + firstValue;
		}
		Integer realValue = 0;
		//判定 0 是正，1是负
		if(Integer.valueOf(firstValue.substring(0, 1)) == TEM_POSITIVE){
			//把剩余的转成10机制
			String tenValue = Integer.valueOf(firstValue.substring(1, 8),2).toString(); 
			String sexValue = tenValue + secondUpDoorTem;
			//转成10进制
			realValue = Integer.parseInt(sexValue,16)/10;
			
		}else if(Integer.valueOf(firstValue.substring(0, 1)) == TEM_NEGATIVE){
			//把剩余的转成10机制
			String tenValue = Integer.valueOf(firstValue.substring(1, 8),2).toString(); 
			String sexValue = tenValue + secondUpDoorTem;
			//转成10进制
			realValue = Integer.parseInt(sexValue,16)/10;
		}
		System.out.println(realValue);
	}
	
	@Test
	public void testDto(){
		Company company = new Company();
		company.setId(1);
		company.setCompanyName("zhangsan");
		company.setCompanyNo("22334");
		company.setCompanyType(CompanyType.ENABLE);
		company.setInsertDate(new Date());
		company.setAge(23);
		
		
		try {
			
			CompanyDto dto  = DtoHelper.build(CompanyDto.class, company);
			System.out.println("dto:"+dto.toString());
			
			Company c1 = new Company(); 
			DtoHelper.dismantle(c1, dto);
			System.out.println("c1: "+c1.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
