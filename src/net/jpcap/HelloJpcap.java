package net.jpcap;

import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;



/**
 * 我的第一个jpcap测试
 * 作为纪念
 * @author yuezhihua
 */
public class HelloJpcap {

	public static void main(String[] args) throws Exception{
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		System.out.println(InetAddress.getByName("www.baidu.com").getHostAddress());
		
//		TestSender();
		
		TestNi();
		
		JC();

	}
	
	
	/**
	 * 测试 jpcap的发送类
	 * @throws Exception
	 */
	public static void TestSender()throws Exception{
		NetworkInterface[] nis = jpcap.JpcapCaptor.getDeviceList();
		//初始化一个JpcapSender实例
		System.out.println(nis.length);
		JpcapSender sender = JpcapSender.openDevice(nis[2]);
		//填充TCP数据包
		//
//		TCPPacket p = new TCPPacket(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin, rsv1, rsv2, window, urgent);
//		p.setIPv4Parameter(priority, d_flag, t_flag, r_flag, rsv_tos, rsv_frag, dont_frag, more_frag, offset, ident, ttl, protocol, src, dst);
	}
	
	/**
	 * 测试JpCap的 JpcapCaptor
	 * 
	 *该类提供了一系列静态方法实现一些基本的功能。
	 *该类一个实例代表建立了一个与指定设备的链接，可以通过该类的实例来控制设备，
	 *例如设定网卡模式、设定过滤关键字等等。
	 *
	 * @throws Exception
	 */
	public static void JC()throws Exception{
		NetworkInterface[] nis = jpcap.JpcapCaptor.getDeviceList();
		System.out.println(nis.length);
		while(true){
			JpcapCaptor captor = JpcapCaptor.openDevice(nis[0], 65534, false, 10000);
			captor.setFilter("ip", true);
			
			jpcap.packet.Packet pack= captor.getPacket();
			
			if(pack instanceof TCPPacket){
				System.out.println();
				System.out.println("===========TCPacket===========");
				System.out.println();
				TCPPacket tp = (TCPPacket)pack;
				System.out.println("src_port--------------"+tp.src_port);
				System.out.println("dst_port--------------"+tp.dst_port);
				System.out.println("sequence--------------"+tp.sequence);
				System.out.println("ack_num--------------"+tp.ack_num);
				System.out.println("urg---------------------"+tp.urg);
				System.out.println("ack---------------------"+tp.ack);
				System.out.println("psh---------------------"+tp.psh);
				System.out.println("rst----------------------"+tp.rst);
				System.out.println("syn----------------------"+tp.syn);
				System.out.println("fin----------------------"+tp.fin);
				System.out.println("rsv1---------------------"+tp.rsv1);
				System.out.println("rsv2---------------------"+tp.rsv2);
				System.out.println("window------------------"+tp.window);
				System.out.println("urgent_pointer--------"+tp.urgent_pointer);
				
				if(pack instanceof IPPacket){
					System.out.println();
					System.out.println("===========TCPacket & IPPacket===========");
					System.out.println();
					IPPacket ip = (IPPacket)pack;
					System.out.println("priority------------------------"+ip.priority);
					System.out.println("d_flag-------------------------"+ip.d_flag);
					System.out.println("t_flag-------------------------"+ip.t_flag);
					System.out.println("r_flag-------------------------"+ip.r_flag);
					System.out.println("rsv_tos------------------------"+ip.rsv_tos);
					System.out.println("rsv_frag----------------------"+ip.rsv_frag);
					System.out.println("dont_frag---------------------"+ip.dont_frag);
					System.out.println("more_frag---------------------"+ip.more_frag);
					System.out.println("offset-------------------------"+ip.offset);
					System.out.println("ident---------------------------"+ip.ident);
					System.out.println("ttl-----------------------------");
					System.out.println("protocol-----------------------"+ip.protocol);
					System.out.println("src-----------------------------");
					System.out.println("dst-----------------------------");
					System.out.println();
					System.out.println("src_ip--------------------------"+ip.src_ip);
					System.out.println("dst_ip--------------------------"+ip.dst_ip);
					System.out.println("flow_label---------------------"+ip.flow_label);
					System.out.println("hop_limit-----------------------"+ip.hop_limit);
					System.out.println("length--------------------------"+ip.length);
					System.out.println("offset--------------------------"+ip.offset);
				}
				
			}else if(pack instanceof UDPPacket){
				System.out.println();
				System.out.println("===========UDPPacket===========");
				System.out.println();
				UDPPacket up = (UDPPacket)pack;
				System.out.println(up.src_port);
				System.out.println(up.dst_port);
				System.out.println(new String(up.data));
				System.out.println();
				System.out.println(up.src_ip);
				System.out.println(up.dst_ip);
				System.out.println(pack);
			}else{
			System.out.println();
			System.out.println("===========else===========");
			System.out.println();
			System.out.println(bytesToString(pack.header));
			System.out.println(bytesToString(pack.data));
			System.out.println(pack);
			System.out.println(pack.len);
			System.out.println(pack.datalink);
			}
			System.out.println();System.out.println();
			System.out.println("-----------------------华丽丽的分割线------------------------------");
			System.out.println();System.out.println();
		}
	}
	
	
	/**
	 *测试jpcap的 NetworkInterface
	 *
	 *该类的每一个实例代表一个网络设备，一般就是网卡。这个类只有一些数据成员，
	 *除了继承自java.lang.Object的基本方法以外，没有定义其它方法。
	 */
	public static void TestNi(){
		NetworkInterface[] nis = JpcapCaptor.getDeviceList();
		for(int i = 0 ; i < nis.length ; i++){
			//数据链路层的描述。描述所在的局域网是什么网。
			//例如，以太网（Ethernet）、无线LAN网（wireless LAN）、令牌环网(token ring)等等。
			System.out.println(nis[i].datalink_description);
			
			//该网络设备所对应数据链路层的名称。具体来说，例如Ethernet10M、100M、1000M等等。
			System.out.println(nis[i].datalink_name);
			
			//网卡是XXXX牌子XXXX型号之类的描述。例如我的网卡描述：Realtek RTL8169/8110 Family Gigabit Ethernet NIC 
			System.out.println(nis[i].description);
			
			//标志这个设备是否loopback设备。
			System.out.println(nis[i].loopback);
			
			//网卡的MAC地址，6个字节。
			byte[] bytes = nis[i].mac_address;
			System.out.println(bytes.length);
			  //下面代码是把mac地址拼装成String
			 for (byte b : bytes) 
	            {
	                System.out.print(Integer.toHexString(b & 0xff) + ":");
	            }
			System.out.println();
			
			//这个设备的名称
			System.out.println(nis[i].name);
			
			System.out.println();System.out.println();System.out.println();
		}
	}
	
	
	/**
	 * 将byte数组组装成一个字符串
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	private static String bytesToString(byte[] bytes)throws Exception{
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < bytes.length ;i ++){
			sb.append(bytes[i]);
		}
		return sb.toString();
	}
	
}
