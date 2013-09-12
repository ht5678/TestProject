package io.bio;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 服务器端
 * @author byht
 *
 */
public class TServer {

	
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8859);
			Socket socket = ss.accept();
			byte[] bytes = new byte[1024];
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			while(true){
				String str = dis.readUTF();
				if(str!=null && str.length()>0){
					System.out.println(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
