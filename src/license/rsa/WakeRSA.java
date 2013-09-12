package license.rsa;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;

import javax.crypto.Cipher;

import license.ProjectInfo;
import license.blowfish.Blowfish;

import org.apache.commons.codec.binary.Base64;

public class WakeRSA {
	
	
	private static final String rsaStr = "lgNBiT1L042Y1aNm9UY+DvVfsPJgziFD+669BH3Nk0EEyarzUdv/dmvlSF5b8oUJfjXtBeGqlAzJrFZ42FR4EDibm7a3lDdBCsQIld+uJFRoIjyr+n302TD1fRmgJkD3hVVyZIUf50GKahyDKww4tWwnQZKC/YV3q6fvcmhxKcyuOukygfQ4kYypM2i/3Xf3PG5W4qNLtimZvs1h6aA9zscrHsdlxk8gOhd605qhGyNYLCBpPifXWDn/DvFPn1D4IFibKPckFTdH5H4LLIIWeETQtVMCaCDGh2TFoxmBPZ75b6RgfawGfXTRjQohJyPBFEIVq10mYtGHwSSIgEn8MA==";
	
	
	public static void main(String[] args) throws Exception{
		
		
		
		System.out.println();
		System.out.println("**********************md5**************************");
		
		int len = md5(null).length;
		for(int i=0 ; i < len ; i++){
			System.out.print(md5(null)[i]);
		}
		
		System.out.println();
		System.out.println("**********************rsa**************************");
		byte[] lmd5 = rsa(new Base64().decode(rsaStr));
		for(int i = 0 ; i < lmd5.length ; i ++){
			System.out.print(lmd5[i]);
		}
		
	}
	
	
	
	/**
	 * 读取公开密钥文件
	 * 
	 * @param keyFileName
	 * @return
	 * @throws Exception
	 */
	static PublicKey readPublicKeyFromFile() throws Exception {
		ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(
				KeyData.publicKey));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			System.out.println();System.out.println();
			System.out.println("=======mmm====="+m);
			System.out.println("=======eeee====="+e);
			System.out.println();System.out.println();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			return fact.generatePublic(keySpec);
		} finally {
			oin.close();
		}
	}
	
	/**
	 * rsa解密
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] rsa(byte[] key) throws Exception {
		PublicKey pubKey = readPublicKeyFromFile();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(key);
	}
	
	/**
	 * 信息转换MD5
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private static byte[] md5(String info) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < info.length(); i++)
//			sb.append(Integer.valueOf(info.charAt(i)));
//		
		
		StringBuilder sb = new StringBuilder("{1=液体散化, 2=常熟海关, 3=100, 4=100, 5=1, 6=1370016000000, 7=dcm19890101@163.com, 8=15000100001, 9=段春明, 10=/E:/wake/dev/wake/WebContent/WEB-INF/classes/org/wakeframework/license/ProjectInfo.class, 11=4, 12=eth0, 13=null, 14=eth1, 15=null}");
		
		return md.digest(sb.toString().getBytes());
	}
	
	
	/**
	 * 将ProjectInfo对象用河豚算法进行加密
	 * @param pi
	 * @return
	 */
	  private String projectInfoToString(ProjectInfo pi) {
		    StringBuilder sb = new StringBuilder();
		    sb.append("1=").append(pi.getName()).append("\n");
		    sb.append("2=").append(pi.getClient()).append("\n");
		    sb.append("3=").append(pi.getMaxUsers()).append("\n");
		    sb.append("4=").append(pi.getMaxOnlineUsers()).append("\n");
		    sb.append("5=").append(pi.getType()).append("\n");
		    sb.append("6=").append(pi.getPeriod()).append("\n");
		    sb.append("7=").append(pi.getEmail()).append("\n");
		    sb.append("8=").append(pi.getTel()).append("\n");
		    sb.append("9=").append(pi.getContact()).append("\n");
		    sb.append("10=").append(pi.getPath()).append("\n");
		    sb.append("11=").append(Runtime.getRuntime().availableProcessors())
		      .append("\n");
		    try {
		      mac(sb);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return new Blowfish(Date.class.getName()).encryptString(sb.toString());
		  }
	
	  
	  /**
	   * 取得mac
	   * @param sb
	   * @throws Exception
	   */
	  private static void mac(StringBuilder sb) throws Exception {
		    Enumeration<NetworkInterface> interfaces = 
		      NetworkInterface.getNetworkInterfaces();
		    int i = 12;
		    Base64 base64 = new Base64();
		    for (NetworkInterface ni : Collections.list(interfaces))
		      if (ni.getName().substring(0, 1).equalsIgnoreCase("e")) {
		        sb.append(String.valueOf(i)).append('=').append(ni.getName())
		          .append('\n');
		        i++;
		        byte[] mac = ni.getHardwareAddress();
		        sb.append(String.valueOf(i)).append('=')
		          .append(base64.encodeAsString(mac)).append('\n');
		        i++;
		      }
		  }
	
	/**
	 * 取得测试用的ProjectInfo对象
	 * @return
	 */
	public ProjectInfo getProjectInfo(){
		ProjectInfo pi = new ProjectInfo();
		pi.setClient("常熟海关");
		pi.setName("液体散化");
		pi.setMaxUsers(100);
		pi.setMaxOnlineUsers(100);
		pi.setType(1);
		pi.setPeriod(System.currentTimeMillis());	//有效日期，，
		pi.setEmail("dcm19890101@163.com");
		pi.setTel("15000100001");
		pi.setContact("段春明");
		pi.setPath("/E:/wake/dev/wake/WebContent/WEB-INF/classes/org/wakeframework/license/ProjectInfo.class");
		pi.setCpuNumber(Runtime.getRuntime().availableProcessors());
//		12=eth0, 13=null, 14=eth1, 15=null}
		pi.setMacName1("eth0");
		pi.setMac1(null);
		pi.setMacName2("eth1");
		pi.setMac2(null);
		
		return pi;
	}
	

}
