package net;

import java.net.Socket;

/**
 * 消息客户端
 * @author byht
 *
 */
public class MsgClient {

	public void start()throws Exception{
		Socket socket = new Socket("127.0.0.1", 9999);
		
		SenderThread sender = new SenderThread(socket);
		sender.start();
		ReceiverThread receiver = new ReceiverThread(socket);
		receiver.start();
	}
	
	public static void main(String[] args) {
		System.out.println("client : ");
		MsgClient msgClient = new MsgClient();
		
		try{
			msgClient.start();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
}
