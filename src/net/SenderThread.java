package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 发送信息线程
 * @author byht
 *
 */
public class SenderThread extends Thread{

	private Socket socket ;
	
	private BufferedReader br;
	
	public SenderThread(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try{
			br = new BufferedReader(new InputStreamReader(System.in));
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			while(true){
				String str = br.readLine();
				dos.writeUTF(str);
				dos.flush();
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	
	
}
