package net;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 消息服务器端
 * @author byht
 *
 */
public class MsgServer {

	
	public void start()throws Exception{

		ServerSocket ss  = new ServerSocket(9999);
		Socket socket = ss.accept();
		
		SenderThread sender = new SenderThread(socket);
		sender.start();
		ReceiverThread receiver = new ReceiverThread(socket);
		receiver.start();
	}
	
	
	public static void main(String[] args) {
		System.out.println("server : ");
		MsgServer msgServer = new MsgServer();
		try {
			msgServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
