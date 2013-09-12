package net;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 接收线程
 * @author byht
 *
 */
public class ReceiverThread extends Thread{

	private Socket socket ;
	
	public ReceiverThread(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try{
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			while(true){
				String str = dis.readUTF();
				if(str!=null){
					System.out.println("inetAddress======"+socket.getInetAddress());
					System.out.println("keepalive========="+socket.getKeepAlive());
					System.out.println("localPort========="+socket.getLocalPort());
					System.out.println("port============="+socket.getPort());
					System.out.println("receiveBufferSize="+socket.getReceiveBufferSize());
					System.out.println("remoteSocketAddress="+socket.getRemoteSocketAddress());
					System.out.println(str);
				}
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
}
