package license.blowfish;

import org.junit.Test;

public class BlowfishTest {
	
	/**
	 * blowfish(河豚)算法二
	 */
	@Test
	public void test02(){
    	Blowfish chiper = new Blowfish("sQNWUEHRPW2j963");
    	
    	String	temp = chiper.encryptString("1");
    	
    	System.out.println(temp);
    	System.out.println(chiper.decryptString("996ecc7c75215df1a3fcccf0d8600759c93b92f3"));
    	System.out.println(chiper.decryptString(temp));
	}
	
	
	/**
	 * blowfish(河豚)算法一
	 */
	@Test
	 public void test01(){
		  String keyString = "closewbq";
		  String str="blowfish";
		  BlowfishUtil crypt = new BlowfishUtil(keyString);
		  String tempStr=crypt.encryptString(str);
		     System.out.println(tempStr);
		     System.out.println(crypt.decryptString(tempStr));
	 }
	 
	 
	}
