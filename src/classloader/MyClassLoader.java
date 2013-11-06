package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 类加载器自定义练习
 * @author byht
 *
 */
public class MyClassLoader extends ClassLoader{

	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//学习步骤一：
		//1.点击右键，Run As->Run Configurations-->Arguments
		//2.填写参数：
		//E:\workspace\TestProject\src\classloader\ClassLoaderAttachment.java
		//E:\workspace\TestProject\classloadertest\ClassLoaderAttachment.class
		//3.运行MyClassLoader方法****然后将classloadertest文件夹中加密后的ClassLoaderAttachment.class和
		//		TestProject\build\classes\classloader中的该文件调换
		//4.运行ClassLoaderTest的main方法进行测试
		//5.出现
		//Exception in thread "main" java.lang.ClassFormatError: Incompatible magic value 2409536660 in class file classloader/ClassLoaderAttachment
		//异常，说明加密成功
		
		
//		String srcPath = args[0];
//		String destPath = args[1];
//		try {
//			FileInputStream fis =new FileInputStream(new File(srcPath));
//			FileOutputStream fos = new FileOutputStream(new File(destPath)); 
//			encrypt(fis, fos);
//			fis.close();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//步骤一测试
		//System.out.println(new ClassLoaderAttachment());
		
		//步骤二：
		//1.MyClassLoader继承ClassLoader,并且重写findClass方法
		//2.把TestProject\build\classes\classloader\ClassLoaderAttachment.class删除，
		//	目的是不让它的父类使用委托机制，然后就可以加载classloadertest目录下面的class文件了
		//3.注意loaderclass的时候要写上包名
		Class clazz = new MyClassLoader("classloadertest").loadClass("classloader.ClassLoaderAttachment");
		//注意：这里的ClassLoaderAttachment类必须继承一个父类(这里随即选择了date)，因为在这里强制转换ClassLoaderAttachment会有错误
		Date date = (Date)clazz.newInstance();
		System.out.println(date);
	}
	
	
	/**
	 * 加密
	 * @param is
	 * @param os
	 * @throws IOException 
	 */
	public static void encrypt(InputStream is , OutputStream os) throws IOException{
		int b = -1;
		while((b = is.read())!=-1){
			os.write(b ^ 0xff);//让0变成1，让1变成0,然后就可以加密解密用同一个方法了
		}
	}
	
	private String classDir;
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classPath = classDir + "\\"  + name.substring(name.lastIndexOf('.')+1) + ".class";
		System.out.println(classPath);
		try {
			FileInputStream fis = new FileInputStream(new File(classPath));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			encrypt(fis, baos);
			fis.close();
			byte[] bytes = baos.toByteArray();
			baos.close();
			return defineClass(null,bytes, 0, bytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//开始的时候会使用委托机制，所以返回null
//		return super.findClass(name);
		return null;
	}
	
	public MyClassLoader(){
		
	}
	
	public MyClassLoader(String classDir){
		this.classDir = classDir;
	}
	
}
