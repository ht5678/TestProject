package net.jpcap.arpsoft;

//A BindTable is a class contains IP-MAC COUPLEs of experiment computers

public class BindTable {
	private String ipAddr;
	private byte[] macAddr;
	
	public BindTable(String ip, byte[] mac) {
		this.ipAddr = ip;
		this.macAddr = mac;
	}
	
	public String GetIP() {
		return ipAddr;
	}
	
	public byte[] GetMac() {
		return macAddr;
	}
}
