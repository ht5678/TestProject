package io.nio.remote;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


/**
 * 服务器端
 * @author 岳志华
 */
public class RemoteServer extends JFrame implements Runnable{

	private Canvas screenCanvas ;
	private volatile boolean stop;
	private Robot robot ;
	private Dimension dim;
	
	/**
	 * 构造函数
	 */
	public RemoteServer(){
		initComponents();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		dim = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	/**
	 * 初始化组件
	 */
	public void initComponents(){
		
		screenCanvas = new Canvas();
		screenCanvas.setBackground(Color.red);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//设置窗口初始大小
		this.setMinimumSize(new Dimension(600, 400));
		//设置窗口可见
		this.setVisible(true);
		stop = true;
		//setResizable(false);
		JScrollPane scroll = new JScrollPane(screenCanvas);
		
		//分别设置水平和垂直滚动条自动出现
		scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scroll);
		
		
		//分别设置水平和垂直滚动条总是出现
//		scroll.setHorizontalScrollBarPolicy(
//		                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scroll.setVerticalScrollBarPolicy(
//		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//分别设置水平和垂直滚动条总是隐藏scroll.setHorizontalScrollBarPolicy(
//        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setVerticalScrollBarPolicy(
//		        JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		pack();
	}
	
	public void run() {
		stop = false;
		while (!stop) {
			BufferedImage bImage = robot.createScreenCapture(new Rectangle(
					dim.width, dim.height));
			
			Graphics g = this.screenCanvas.getGraphics();
			g.drawImage(bImage, 0, 0, getWidth() , getHeight() , this);
			try {
				Thread.sleep(41);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private synchronized void stop(){
		stop = true;
	}
	
	
	public static void main(String[] args) {
		final RemoteServer rs = new RemoteServer();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				rs.setVisible(true);
			}
		});
		Thread t = new Thread(rs);
		t.start();
	}
	
}
