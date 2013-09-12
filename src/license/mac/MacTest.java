package license.mac;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * mac地址的测试
 * @author byht
 *
 */
public class MacTest {
	
	public static void main(String[] args) throws Exception{
		Map<Integer, String> infoMap = new HashMap<Integer, String>();
		infoMap.put(1, "液体散化");
		infoMap.put(2, "常熟海关");
		infoMap.put(2, "常熟海关");
		infoMap.put(2, "常熟海关");
		infoMap.put(12, "eth0");
		
		
		MacTest mac = new MacTest();
		
		
		System.out.println();System.out.println();
		System.out.println("************获取mac地址*********");
		System.out.println();System.out.println();
        InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象
        System.out.println("IP .........: "+ia.getHostAddress());
        System.out.println("MAC .........: "+getMACAddress(ia));
        
        
        System.out.println();System.out.println();
        mac.checkMac();
	}
	
	
	     
	     //获取MAC地址的方法
	     private static String getMACAddress(InetAddress ia)throws Exception{
	         //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
	         byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
	         
	         //下面代码是把mac地址拼装成String
	         StringBuffer sb = new StringBuffer();
	         
	         for(int i=0;i<mac.length;i++){
	             if(i!=0){
	                 sb.append("-");
	             }
	             //mac[i] & 0xFF 是为了把byte转化为正整数
	             String s = Integer.toHexString(mac[i] & 0xFF);
	             sb.append(s.length()==1?0+s:s);
	         }
	         
	         //把字符串所有小写字母改为大写成为正规的mac地址并返回
	         return sb.toString().toUpperCase();
	     }

	
	/**
	 * 检查mac地址
	 * @return
	 * @throws SocketException
	 */
	public  boolean checkMac()throws SocketException {
		StringBuffer sb = new StringBuffer();
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
		System.out.println(sb.toString());
		return true;
	}
	
}
