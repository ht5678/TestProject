package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * jdk---nio练习
 * 服务器端
 * @author 岳志华
 *
 */
public class TestServer {

	private Selector selector;
	
	
	public void init()throws IOException{
		//打开通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//设置为非阻塞
		serverSocketChannel.configureBlocking(false);
		//检查与此通道关联的服务套接字
		ServerSocket serverSocket = serverSocketChannel.socket();
		//绑定服务端口8859
		serverSocket.bind(new InetSocketAddress(8859));
		
		selector = Selector.open();
		//注册selector
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server:");
	}
	
	/**
	 * 监听
	 * @throws IOException
	 */
	private void listen()throws IOException{
		while(true){
			selector.select();
			Set<SelectionKey> selectionKeys = selector.keys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			while(iter.hasNext()){
				SelectionKey selectionKey = iter.next();
				iter.remove();
				handleKey(selectionKey);
			}
		}
	}
	
	/**
	 * 处理信息
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleKey(SelectionKey selectionKey)throws IOException{
		//接收请求
		ServerSocketChannel server = null;
		
	}
	
}
