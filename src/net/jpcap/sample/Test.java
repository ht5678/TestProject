package net.jpcap.sample;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;


/**
 * 学习资料
 * @author byht
 *
 */
public class Test implements PacketReceiver
{
      //public Vector<String> CaptureVec = new Vector<String>();
    public NetworkInterface[] devices;
    private JpcapCaptor pcap;

 Test()
 {
      //   pcap = new JpcapCaptor();

        devices = JpcapCaptor.getDeviceList();

        System.out.println("*****************************************************************************");

        for(int i = 0;i < devices.length;i++)
        {
         System.out.println(i + " :" + devices[i].name + "(" + devices[i].description + ")");
                
            System.out.println("    data link:" + devices[i].datalink_name +"(" + devices[i].datalink_description + ")");
                
            System.out.println("    MAC address:");
                
            for (byte b : devices[i].mac_address) 
            {
                System.out.print(Integer.toHexString(b & 0xff) + ":");
            }
            System.out.println("111111111111111111111111111111111111111111111111111111111");
        }        

        


        System.out.println("*****************************************************************************");     

               
 }
 
 private void packetCap()
 {

  try
  {
   pcap = JpcapCaptor.openDevice(devices[1],1028,true,1000);
   pcap.loopPacket(-1,this); 
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
    
 
 }
    
    public void receivePacket(Packet packet)
    {
     System.out.println(packet);
     System.out.println("*****************************************");
    }

    public static void main(String[] args)throws Exception
    {
		  Test test = new Test();
		  test.packetCap();
		  
		  
    }

   
}