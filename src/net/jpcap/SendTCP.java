package net.jpcap;

import java.net.InetAddress;
import jpcap.*;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
class SendTCP
{
		public static void main(String[] args) throws java.io.IOException{
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();//获取设备
		//
		//默认使用第3个网卡
		//
		JpcapSender sender=JpcapSender.openDevice(devices[1]);//使用网卡
         //填充TCP数据包
		TCPPacket p=new TCPPacket(8989,9999,56,78,false,false,false,false,true,true,true,true,10,10); 
		
//		TCPPacket p=new TCPPacket(80,6273,1105326815,1030293934,false,false,false,false,true,true,true,true,65280,0); 
//        //填充IP数据包
//		p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,100,IPPacket.IPPROTO_TCP,
//		InetAddress.getLocalHost(),InetAddress.getByName("www.baidu.com"));
		
		
        //填充IP数据包
		p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,100,IPPacket.IPPROTO_TCP,
		InetAddress.getLocalHost(),InetAddress.getLocalHost());
        //填充TCP数据包数据
		p.data=("This is my homework of network,I am happy!").getBytes();
        //帧格式设置
		EthernetPacket ether=new EthernetPacket();
		ether.frametype=EthernetPacket.ETHERTYPE_IP;
        //源MAC设置B8-70-F4-06-39-C5
		ether.src_mac=new byte[]{(byte)0xB8,(byte)0x70,(byte)0xF4,(byte)0x06,(byte)0x39,(byte)0xC5};
        //目的MAC设置
		ether.dst_mac=new byte[]{(byte)0xB8,(byte)0x70,(byte)0xF4,(byte)0x06,(byte)0x39,(byte)0xC5};
		p.datalink=ether;
		for(int i=0;i<1000;i++)  //循环发送1000次，便于接受检测
			sender.sendPacket(p);
		System.out.println("Send Ok!");//发送成功
	}
}