package reflect;

import java.util.Date;

import classloader.MyClassLoader;




/**
 * 测试三个系统默认三个主要类加载器
 * BootStrap,					-->ExtClassLoader的父类			管辖范围 -->JRE/lib/rt.jar
 * ExtClassLoader,		-->AppClassLoader的父类							-->JRE/lib/ext/*.jar
 * AppClassLoader			-->																-->CLASSPATH所指定的所有jar或目录
 * 
 * @author byht
 *
 */
public class ClassLoaderTest {

	public static void main(String[] args) throws Exception{
		
		System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
		
		System.out.println(System.class.getClassLoader());

		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while(loader!=null){
			System.out.println(loader.getClass().getName());
			loader = loader.getParent();
		}
		
//		System.out.println(loader);
//		System.out.println(new ClassLoaderAttachment());
		
		Class clazz = new MyClassLoader("classloadertest").loadClass("ClassLoaderAttachment");
		//注意：这里的ClassLoaderAttachment类必须继承Date，因为在这里强制转换ClassLoaderAttachment会有错误
		Date date = (Date)clazz.newInstance();
		System.out.println(date);
	}
	
}
