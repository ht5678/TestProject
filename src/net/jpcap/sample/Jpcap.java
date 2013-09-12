package net.jpcap.sample;

import jpcap.*;
import jpcap.packet.*;

public class Jpcap {
	private NetworkInterface[] devices;
	private NetworkInterface device;
	private JpcapCaptor jpcap;
	private JpcapSender sender;

	private NetworkInterface getDevice() throws Exception {
		devices = JpcapCaptor.getDeviceList();
		device = devices[0];
		jpcap = JpcapCaptor.openDevice(device, 2000, false, 10000);
		jpcap.setFilter("ip", true);
		// sender = jpcap.getJpcapSenderInstance();
		jpcap.loopPacket(-1, new PacketReceiverImpl());
		return device;
	}

	public Jpcap() {
		try {
			this.getDevice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// getDevice();
	}

	public static void main(String[] args) {
		Jpcap j = new Jpcap();
	}

}

class PacketReceiverImpl implements PacketReceiver {
	public void receivePacket(Packet packet) {
		try {
			System.out.println(new String(packet.data, "gb2312"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}