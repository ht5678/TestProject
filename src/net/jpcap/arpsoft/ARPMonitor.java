package net.jpcap.arpsoft;

import java.net.InetAddress;
import java.util.Date;
import java.io.*;

import jpcap.*;
import jpcap.packet.*;

public class ARPMonitor {
	
	private jpcap.JpcapCaptor cap;
	private jpcap.NetworkInterface device;
	private JpcapSender sender;
	
	public ARPMonitor() throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		device = devices[1];
		cap = JpcapCaptor.openDevice(device, 2000, false, 5000);
		sender = JpcapSender.openDevice(device);
	}
	
	public void Monitor(final String monitoring_addr , byte[] gateMac , final byte[] pcMac) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
			try {
				Date time = new Date();
				String fileStore = "statout"+time.getTime()+".txt";
				PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileStore)));
				while(true) {
					Packet recvPacket = cap.getPacket();
					if(recvPacket instanceof IPPacket) {
						IPPacket ipPacket = (IPPacket)recvPacket;
						//...
						parseTCPPack(recvPacket);
						//...
						if(ipPacket.src_ip.toString().compareTo(
							InetAddress.getByName(monitoring_addr).toString()) == 0)
								fileOut.println("源IP:"+ipPacket.src_ip
										+ "   目的IP:"
										+ ipPacket.dst_ip
										+ "   数据包长度:"
										+ ipPacket.length
										+ "   源MAC:"
										+ ((EthernetPacket) ipPacket.datalink).src_mac
										+ "    目的MAC:"
										+ ((EthernetPacket) ipPacket.datalink).dst_mac);
						
						fileOut.flush();
						
						//条件过滤，将捕获的目标主机的数据包转发给目标主机或者网关
						if(monitoring_addr.equals(ipPacket.src_ip) || monitoring_addr.equals(ipPacket.dst_ip)){
							//将捕获到的目标主机的数据包转发给网关
							//...
							//修改包的以太头，转发
							//...
							if(ipPacket.datalink instanceof EthernetPacket){
								((EthernetPacket)ipPacket.datalink).dst_mac=pcMac;
								((EthernetPacket)ipPacket.datalink).src_mac=device.mac_address;
								sender.sendPacket(ipPacket);
							}
						}
						
					} 
				}
				}catch (Exception e) {System.out.println(e + "! Who cares!");}
			}
		});
		
		t.start();
		
	}
	
	
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
}
