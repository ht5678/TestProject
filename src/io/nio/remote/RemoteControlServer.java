package io.nio.remote;

import io.nio.NIOServer;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import utils.ImageUtil;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
 * 远程控制服务器端
 * @author yuezhihua
 *
 */
public class RemoteControlServer {

	// 标识数字
	private int flag = 0;
	// 缓冲区大小
	private int BLOCK = 1024*200;
	// 接受数据缓冲区
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	// 发送数据缓冲区
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);

	private Selector selector;
	
	private SocketChannel client = null;
	
	private SelectionKey selectionKey;
	
	private int i =0;
	
	private int count = 0 ; 
	
	private BufferedImage[] bimgs;

	/**
	 * 初始化
	 */
	public RemoteControlServer(int port){
		try {
			// 打开服务器套接字通道
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// 服务器配置为非阻塞
			serverSocketChannel.configureBlocking(false);
			// 检查与此通道关联的服务套接字
			ServerSocket serverSocket = serverSocketChannel.socket();
			// 进行服务的绑定
			serverSocket.bind(new InetSocketAddress(port));
			// 通过open方法找到selector
			selector = Selector.open();
			// 注册到selector，等待连接
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Server Start----:");
		} catch (IOException e) {
			System.out.println("message:"+e.getMessage());
		}
		
	}
	
	
	// 监听
	private void listen() throws IOException {
		while (true) {
			// 选择一组键，并且相应的通道已经打开
			selector.select();
			// 返回此选择器的已选择键集
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			while (iter.hasNext()) {
				selectionKey = iter.next();
				iter.remove();
				handleKey(selectionKey);
			}
		}
	}

	/**
	 * 处理请求
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// 接收请求
		ServerSocketChannel server = null;
		String receiveText;
		String sendText;

		// 测试此键的通道是否已经准备好接受新的套接字连接
		if (selectionKey.isAcceptable()) {
			// 返回为之创建此键的通道
			server = (ServerSocketChannel) selectionKey.channel();
			// 接收到此通道套接字的连接
			// 此方法返回的套接字通道（如果有）将处于阻塞模式
			client = server.accept();
			// 配置为非阻塞
			client.configureBlocking(false);
			// 注册到selector，等待连接
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			receiveMsg();
		} else if (selectionKey.isWritable()) {
			// 将缓存区清空
			sendbuffer.clear();
			// 返回为之创建此键的通道
			client = (SocketChannel) selectionKey.channel();
			sendText = "message from server : " + flag++;
			// 向缓冲区输入数据
			sendbuffer.put(sendText.getBytes());
			// 将缓冲区各标志复位,因为向里边put了数据标志被改变要想从中读取数据发向服务器，就要复位
			sendbuffer.flip();
			// 输出到通道
			client.write(sendbuffer);
			System.out.println("服务器端向客户端发送数据--：" + sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
	}
	
	
	/**
	 * 接收服务器端传输的图像信息
	 */
	public void receiveMsg(){
		bimgs = new BufferedImage[10];
		try{
			// 返回为之创建此键的通道
			client = (SocketChannel) selectionKey.channel();
			// 将缓冲区清空以备下次读取
			receivebuffer.clear();
			int count = 0;
			count = client.read(receivebuffer);
			if (count > 0) {
				try{
					i++;
//					ImageIO.write(ImageUtil.bytes2img(receivebuffer.array()), "jpg", new FileOutputStream(new File("G://test//"+i+".jpg")));
					getPackage(receivebuffer);
//					ImageIO.write(ImageUtil.bytes2img(receivebuffer.array()),
//							"jpg", new FileOutputStream(new File("G://test//"
//									+ i + ".jpg")));
				}catch(Exception e){
					System.out.println("---------"+e.getMessage());
				}
				client.register(selector, SelectionKey.OP_WRITE);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 分析数据包，解决断包、粘包
	 */
	public BufferedImage getPackage(ByteBuffer buffer){
		count = 0;
		byte[] temp = new byte[200*1024];
		byte[] len = new byte[6];
		if(buffer.array().length>1024){
			buffer.get(len, count, 6);
			count = count +6;
				System.out.println("================test============="+new String(len));
			bimgs[0] = ImageUtil.bytes2img(buffer.get(temp, count, Integer.valueOf(len.toString())).array());
			count = count + Integer.valueOf(len.toString());
			try {
				ImageIO.write(bimgs[bimgs.length-1],
						"jpg", new FileOutputStream(new File("G://test//"
								+ i + ".jpg")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port = 8859;
		RemoteControlServer server;
		try {
			server = new RemoteControlServer(port);
			server.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
