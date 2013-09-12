package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * jdk---nio练习
 * 客户端
 * @author 岳志华
 *
 */
public class TestClient {

	private Selector selector;
	

	/**
	 * 客户端初始化
	 */
	public void init(){
		try {
			SocketChannel socketChannel = SocketChannel.open();
			
			socketChannel.configureBlocking(false);
			
			selector= Selector.open();
			
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
			socketChannel.connect(new InetSocketAddress("localhost", 8859));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
