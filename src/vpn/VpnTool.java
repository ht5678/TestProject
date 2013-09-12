package vpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * vpn工具
 * 
 * 开启连接
 *				vpnName   密码
 * rasdial test test test 
 * 					  用户名
 * 
 * 关闭连接
 * 			vpnName
 * rasdial test /DISCONNECT
 * 
 * @author 岳志华
 *
 */
public class VpnTool {

	/**
	 * 执行dos指令
	 * @param cmd	dos指令
	 * @return
	 * @throws Exception
	 */
	private synchronized static String executeCmd(String cmd)throws Exception{
		Process process = Runtime.getRuntime().exec(cmd);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
		String line = "";
		while((line = br.readLine()) != null){
			sb.append(line+"\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * 开启指定的vpn连接
	 * @param vpnName		vpn名称
	 * @param username		用户名
	 * @param password		密码
	 * @return	命令的执行结果
	 * @throws Exception
	 */
	public synchronized static boolean connectVpn(String vpnName , String username , String password)throws Exception{
		String cmd = "rasdial" +" "+ vpnName +" " + username +" "+password;
		String result = executeCmd(cmd);
		if((result == null || !result.contains("已连接")))
			return false;
		return true;
	}
	  
	/**
	 * 取消连接vpn
	 * @param vpnName		vpn名称
	 * @return	取消连接vpn的结果
	 * @throws Exception
	 */
	public synchronized static boolean disConnectVpn(String vpnName)throws Exception{
		String cmd = "rasdial" +" "+ vpnName+" "+"/DISCONNECT";
		String result = executeCmd(cmd);
		System.out.println(result);
		if((result == null || !result.contains("命令已完成")))
			return false;
		return true;
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(connectVpn("test", "test", "test"));
//			System.out.println(disConnectVpn("test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
