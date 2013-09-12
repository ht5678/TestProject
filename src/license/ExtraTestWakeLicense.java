package license;

import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import license.blowfish.Blowfish;
import license.rsa.RSACoder;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

/**
 * wake框架练习**扩展练习**
 * 将rsa的公钥和私钥更换成自定义
 * 
 * ----------------------------------------------
 * TODO:这个标志是比较TestWakeLicense相对应的
 * ----------------------------------------------
 * 
 * @author byht
 *
 */
public class ExtraTestWakeLicense {

	//1.模拟用户在页面进行注册
	ProjectInfo pi = new ProjectInfo();
	
	/**
	 * 将测试数据保存到string字符串中
	 */
	private String testData;
	
	/**
	 * 自定义的 *公钥* 和 *私钥*
	 */
	private static String publicKey;
	private String privateKey;
	
	
	@Before
	public void setUp()throws Exception{
		Map<String, Object> keyMap  = RSACoder.initKey();
		
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);     
        System.err.println("私钥： \n\r" + privateKey); 
	}
	
	
	
	
	/**
	 * 对wake框架license部分的模拟练习
	 * @throws Exception
	 */
	@Test
	public void testWake()throws Exception{

		pi.setClient("常熟海关");
		pi.setName("液体散化");
		pi.setContact("段春明");
		pi.setTel("15000100001");
		pi.setEmail("dcm19890101@163.com");
		
		
		String info = projectInfoToString(pi);
		System.out.println("注册时---输出===：(*****这个信息在生产环境下是info.getBytes()的形式输出到Register.data文件中)"+info);
		
		System.out.println();System.out.println();
		
		readProjectInfo(pi,info);
		
		System.out.println();System.out.println();
		
		setInput(pi);

		System.out.println();System.out.println();
		
		//将加密后的数据设置到testData中
		testData = toString(pi);
		System.out.println(testData);
		
		System.out.println(extraData());

		
		
		System.out.println();System.out.println();
		
		//
		//对wake框架的*登录验证*时的license验证进行模拟练习
		//
		String[] ss = testData.split("\n");
		String blowfishInfo = new Blowfish(Date.class.getName()).decryptString(ss[0]);
        byte[] rmd5 = md5(blowfishInfo);
        //先将数据进行base64解码
        byte[] lmd5 = rsa(new Base64().decode(ss[1]));
        System.out.println("这个是测试两个加密数据是否相等 ，，，"+equalsBytes(rmd5, lmd5));
        
        
	}
	
	
	/**
	 * 判断文件中的两个加密数据是否相同 
	 * @param rmd5
	 * @param lmd5
	 * @return
	 */
	  private static boolean equalsBytes(byte[] rmd5, byte[] lmd5)
	  {
	    if (rmd5.length == lmd5.length) {
	      for (int i = 0; i < rmd5.length; i++)
	        if (rmd5[i] != lmd5[i])
	          return false;
	      return true;
	    }
	    return false;
	  }
	
	  
	  /**
	   * 读取公钥
	   * @return
	   * @throws Exception
	   */
	  //TODO:删除杜取公钥方法，，采用自定义公钥
	  static PublicKey readPublicKeyFromFile()throws Exception{
//	    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(
//	      KeyData.publicKey));
//	    try {
//	      BigInteger m = (BigInteger)oin.readObject();
//	      BigInteger e = (BigInteger)oin.readObject();
//	      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
//	      KeyFactory fact = KeyFactory.getInstance("RSA");
//	      return fact.generatePublic(keySpec);
//	    } finally {
//	      oin.close();
//	    }
		  return null;
	  }
	
	/**
	 * 将数据进行rsa解码
	 * @param key
	 * @return
	 * @throws Exception
	 */
	  //TODO:修改为自定义的公钥，，进行解码
	  private static byte[] rsa(byte[] key) throws Exception {
		    return RSACoder.decryptByPublicKey(key, publicKey);
	  }
	
	/**
	 * 将被blowfish解密后的数据进行md5加密
	 * @param info
	 * @return
	 * @throws Exception
	 */
	  private static byte[] md5(String info)
	    throws Exception
	  {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < info.length(); i++)
	      sb.append(Integer.valueOf(info.charAt(i)));
	    return md.digest(sb.toString().getBytes());
	  }
	  
	  
	
	/**
	 * jersey.data中一些额外的显示信息的输出
	 * @return
	 */
	public String extraData(){
		
	    StringBuffer sb = new StringBuffer();
	    sb.append("\n客户:");
	    sb.append(pi.getClient());
	    sb.append("\n项目:");
	    sb.append(pi.getName());
	    sb.append("\n版本:");
	    switch (pi.getType().intValue()) {
	    case 0:
	      sb.append("开发版");
	      break;
	    case 1:
	      sb.append("测试版");
	      break;
	    case 2:
	      sb.append("正式版");
	      break;
	    }

	    sb.append("\n过期：");
	    sb.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date(pi.getPeriod().longValue())));
	    
	    return sb.toString();
	}
	
	
	  public String encryptProjectInfo(ProjectInfo pi)
	  {
	    return new Blowfish(Date.class.getName()).encryptString(projectInfo(pi));
	  }
	  
	  private String projectInfo(ProjectInfo pi)
	  {
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
	    sb.append("11=").append(pi.getCpuNumber()).append("\n");
	    sb.append("12=").append(pi.getMacName1()).append("\n");
	    sb.append("13=").append(pi.getMac1()).append("\n");
	    sb.append("14=").append(pi.getMacName2()).append("\n");
	    sb.append("15=").append(pi.getMac2()).append("\n");
	    
	    System.out.println("文件注册-----写入到文件 : "+sb.toString());
	    
	    return sb.toString();
	  }
	
	/**
	 * 将注册信息写到文件中
	 * 第一行;用blowfish加密后的信息
	 * 第二行;以md5--rsa--base64顺序加密信息
	 * 
	 */
	  public String toString(ProjectInfo pi) {
		  System.out.println("将注册信息写到文件中");
		  
		    try {
		      return encryptProjectInfo(pi) + "\n" + 
		        new Base64().encodeAsString(rsa());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return null;
		 }
	  
	  
	  
	  //TODO: 修改为自定的私钥
	  byte[] rsa()throws Exception
	  {
	    return RSACoder.encryptByPrivateKey(md5(), privateKey);
	  }
	  
	  
	  
	  byte[] md5()
	    throws Exception
	  {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    StringBuilder sb = new StringBuilder();
	    String info = projectInfo(pi);
	    System.out.println("注册文件----写入文件前的准备，，，md5加密：");
	    System.out.println(info);
	    System.out.println();
	    for (int i = 0; i < info.length(); i++)
	      sb.append(Integer.valueOf(info.charAt(i)));
	    return md.digest(sb.toString().getBytes());
	  }
	  

	  
	
	/**
	 * 模拟注册时的生成文件
	 * @param pi
	 */
	private void setInput(ProjectInfo pi)throws Exception{
		System.out.println("模拟注册时的生成文件=====");
		System.out.println();
	    System.out.println("License类型 (0: 开发版, 1: 测试版, 2: 正式版) :------假设为1 ");
	    pi.setType(1);
	    System.out.println("过期时间 (yyyy-MM-dd) :-----假设为2013-06-01 ");
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    pi.setPeriod(Long.valueOf(sdf.parse("2013-06-01").getTime()));
	    System.out.println("最大用户数 : -----假设为100");
	    pi.setMaxUsers(100);
	    System.out.println("最大在线用户数 : -----假设为100");
	    pi.setMaxOnlineUsers(100);
	}
	
	
	/**
	 * 将用河豚算法加密的字符串解析
	 * @param info
	 * @throws Exception
	 */
	 public void readProjectInfo(ProjectInfo pi , String info)
	    throws Exception
	  {
	    String[] arrayOfString1;
	      System.out.println("解析后的信息---输出：");
	    int j = (arrayOfString1 = new Blowfish(Date.class.getName()).decryptString(
	      info).split("\n")).length; for (int i = 0; i < j; i++) {
	      String str = arrayOfString1[i];
	      String[] s = str.split("=");
	      System.out.println(s[0]+"="+s[1]);
	      switch (Integer.valueOf(s[0]).intValue()) {
	      case 1:
	    	  pi.setName(s[1]);
	        break;
	      case 2:
	    	  pi.setClient(s[1]);
	        break;
	      case 7:
	        pi.setEmail(s[1]);
	        break;
	      case 8:
	        pi.setTel(s[1]);
	        break;
	      case 9:
	        pi.setContact(s[1]);
	        break;
	      case 10:
	        pi.setPath(s[1]);
	        break;
	      case 11:
	        pi.setCpuNumber(Integer.valueOf(s[1]));
	        break;
	      case 12:
	        pi.setMacName1(s[1]);
	        break;
	      case 13:
	        pi.setMac1(s[1]);
	        break;
	      case 14:
	        pi.setMacName2(s[1]);
	        break;
	      case 15:
	        pi.setMac2(s[1]);
	      case 3:
	      case 4:
	      case 5:
	      case 6:
	      }
	    }
	  }
	
	
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
		sb.append("10=").append(path()).append("\n");
		sb.append("11=").append(Runtime.getRuntime().availableProcessors())
				.append("\n");
		try {
			mac(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("注册时----注册用户信息---输出====：\n"+sb.toString());
		return new Blowfish(Date.class.getName()).encryptString(sb.toString());
	}
	
	
	/**
	 * 获取mac地址
	 * @param sb
	 * @throws Exception
	 */
	private static void mac(StringBuilder sb) throws Exception {
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();
		int i = 12;
		Base64 base64 = new Base64();
		for (NetworkInterface ni : Collections.list(interfaces)) {
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
	}
	
	
	/**
	 * 获取路径
	 * @return
	 */
	private static String path() {
		return ProjectInfo.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath().split("lib")[0];
	}
	
	
}
