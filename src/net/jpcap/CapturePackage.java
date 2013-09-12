package net.jpcap;

import java.io.*;
import jpcap.*;
import jpcap.packet.*;   
public class CapturePackage implements PacketReceiver{

	TCPPacket tcp; //每次抓到一个包时，tcp是其引用
    static String name;//txt文件名
    int count=0;//抓到的包的个数
    String temp;
    public void receivePacket(Packet packet){
     	if(packet instanceof TCPPacket){
    		count++;
    		System.out.println("NO."+count);
    		tcp=(TCPPacket)packet;
    		temp=new String(tcp.data);//获取TCPPacket中的数据部分
    		System.out.println(temp);//打印数据，即This is my network…….
	       	try{
	    		    System.out.println(tcp.src_port+"->"+tcp.dst_port+"\t");
	    		    System.out.println(tcp.src_ip+"->"+tcp.dst_ip+"\t");
	    			RandomAccessFile file = new RandomAccessFile(name, "rw"); 
	    			//把IP数据包写入到文本文件
//	    			file.seek(rf.length());   //定位文件指针在文件中的最后
//	    			file.writeBytes(tcp.src_port+"->"+tcp.dst_port+"\t");
//	    			file.close();
	         }
    		catch (Exception e) {
    			e.printStackTrace();
    		 }
      }
    }
    public static void main(String[] args) throws java.io.IOException{
    	name = "test";  //args[0]中包含txt文件名
    	NetworkInterface[] devices =  JpcapCaptor.getDeviceList(); //获取设备
		//
		//默认使用第3个网卡
		//
    	NetworkInterface deviceName = devices[1];    // 打开网卡设备
    	JpcapCaptor jpcap =  JpcapCaptor.openDevice(deviceName, 1028,true, 100);
    	jpcap.loopPacket(1000, new CapturePackage()); //循环获取1000个数据包, 
    }
}

