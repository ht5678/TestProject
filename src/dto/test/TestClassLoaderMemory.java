package dto.test;

import org.junit.Test;

public class TestClassLoaderMemory {

	  public static long freeMemory() {
	    return Runtime.getRuntime().totalMemory() - 
	      Runtime.getRuntime().freeMemory();
	  }

	  public static long freeMemoryM(){
	    return freeMemory() / 1048576L;
	  }
	
	@Test
	public void testStringFormat(){
		System.out.println("totalMemory : "+Runtime.getRuntime().totalMemory()/ 1048576L);
		System.out.println("freeMemory : "+Runtime.getRuntime().freeMemory()/ 1048576L);
		System.out.println(String.format("内存使用: %,d M", new Object[] { Long.valueOf(freeMemoryM()) }));
		System.out.println(
				TestClassLoaderMemory.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath()
				.split("WEB-INF")[0]
		);
	}
	
	
}
