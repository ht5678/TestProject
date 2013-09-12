package test.test;

import java.lang.reflect.Method;

import org.junit.Test;

import dto.annotation.MyAnnotion1;
import dto.annotation.MyAnnotion2;

public class TestAnnotation {

	@Test
	public void test() throws ClassNotFoundException, SecurityException, NoSuchMethodException{
		Class clazz = Class.forName("org.wakeframework.dto.annotion.AnnotationDemo");
		boolean flag = clazz.isAnnotationPresent(MyAnnotion1.class);
		if(flag){
			System.out.println("判断是annotion");
			MyAnnotion1 annotion1 = (MyAnnotion1) clazz.getAnnotation(MyAnnotion1.class);
			System.out.println(annotion1.value());
		}
		
		Method method = clazz.getMethod("sayHello");
		
		flag = method.isAnnotationPresent(MyAnnotion2.class);
		if(flag){
			System.out.println("判断方法是annotation类");
			MyAnnotion2 annotion2 = (MyAnnotion2) method.getAnnotation(MyAnnotion2.class);
			System.out.println(annotion2.description());
			System.out.println(annotion2.isAnnotation());
		}
		
		
	}
}
