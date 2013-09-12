package io.nio.remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import utils.ImageUtil;


/**
 * 远程控制客户端
 * @author yuezhihua
 *
 */
public class RemoteControlClient {

	/* 标识数字 */
	private static int flag = 0;
	/* 缓冲区大小 */
	private static int BLOCK = 1024*200;
	/* 接受数据缓冲区 */
	private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* 发送数据缓冲区 */
	private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	
	private static SocketChannel client;
	
	private static SelectionKey selectionKey;
	
	private static Selector selector;
	
	private static Robot robot ;
	
	private static Dimension dim;
	
	/* 服务器端地址 */
	private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
			"localhost", 8859);

	public static void main(String[] args) throws IOException {
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		// TODO Auto-generated method stub
		// 打开socket通道
		SocketChannel socketChannel = SocketChannel.open();
		// 设置为非阻塞方式
		socketChannel.configureBlocking(false);
		// 打开选择器
		selector = Selector.open();
		// 注册连接服务端socket动作
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// 连接
		socketChannel.connect(SERVER_ADDRESS);
		// 分配缓冲区大小内存

		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		String receiveText;
		String sendText;
		int count = 0;

		while (true) {
			// 选择一组键，其相应的通道已为 I/O 操作准备就绪。
			// 此方法执行处于阻塞模式的选择操作。
			selector.select();
			// 返回此选择器的已选择键集。
			selectionKeys = selector.selectedKeys();
//			 System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
//				System.out.println(selectionKey.isConnectable());
				if (selectionKey.isConnectable()) {
					System.out.println("client connect");
					client = (SocketChannel) selectionKey.channel();
					// 判断此通道上是否正在进行连接操作。
					// 完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("完成连接!");
						sendbuffer.clear();
						sendbuffer.put("hello".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
						client.register(selector, SelectionKey.OP_READ);
					}
				} else if (selectionKey.isReadable()) {
					client = (SocketChannel) selectionKey.channel();
					// 将缓冲区清空以备下次读取
					receivebuffer.clear();
					// 读取服务器发送来的数据到缓冲区中
					count = client.read(receivebuffer);
					if (count > 0) {
						receiveText = new String(receivebuffer.array(), 0,
								count);
						System.out.println("客户端接受服务器端数据--:" + receiveText);
						client.register(selector, SelectionKey.OP_WRITE);
					}

				} else if (selectionKey.isWritable()) {
					sendMsg();
				}
			}
			selectionKeys.clear();
		}
	}
	
	/**
	 * 发送图像到服务器端
	 * @throws IOException 
	 */
	public synchronized static void sendMsg() throws IOException{
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendbuffer.clear();
			client = (SocketChannel) selectionKey.channel();
			byte[] imgData = ImageUtil.img2bytes(getImage());
			byte[] len = new byte[6]; 
			byte[] sm = (imgData.length+"").getBytes();
			System.out.println("开始长度："+len.length);
			System.out.println(len);
//			sendbuffer.put(len);
			sendbuffer.put(imgData);
			sendbuffer.flip();
			client.write(sendbuffer);
			client.register(selector, SelectionKey.OP_READ);
	}
	
	/**
	 * 获取图像
	 * @return
	 */
	public static BufferedImage getImage(){
		BufferedImage bImage = robot.createScreenCapture(new Rectangle(
				dim.width, dim.height));
		return bImage;
	}

}
