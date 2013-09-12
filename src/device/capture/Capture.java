package device.capture;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * 
 * 将BufferedImage转换成图片并且输出
 * 
 * BufferedImage是Image的直接子类
 *Image yourImg   =    Toolkit.getDefaultToolkit().createImage(ri.data);  // ri.data就是个byte数组
 *BufferedImage   bi=new   BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_BGR);  
 *Graphics2D   g2d=bi.createGraphics();  
 *g2d.drawImage(yourimg,0,0,null);  
 *这样把yourimg画在了bi
 *
 *----------------------------------------------------
 *
 *把BufferedImage输出
 *BufferedImage   img   =   new   BufferedImage(width,   height,   BufferedImage.TYPE_INT_RGB);   
 *FileOutputStream   fos   =   new   FileOutputStream("img.jpg");     
 * JPEGImageEncoder   encoder   =   JPEGCodec.createJPEGEncoder(fos);     
 *encoder.encode(img);     
 * out.close();
 * 
 * ------------------------------------------------------------------
 * 
 * robot---截图
 * @author 岳志华
 *
 */
public class Capture extends JFrame implements Runnable{

	private Canvas screenCanvas ;
	private volatile boolean stop;
	private Robot robot ;
	private Dimension dim;
	
	public Capture(){
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		stop = true;
		setResizable(false);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(screenCanvas, GroupLayout.PREFERRED_SIZE, 519,
						GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(screenCanvas, GroupLayout.PREFERRED_SIZE, 434,
						GroupLayout.PREFERRED_SIZE));
		
		pack();
	}
	
	public void run() {
		stop = false;
		while (!stop) {
			BufferedImage bImage = robot.createScreenCapture(new Rectangle(
					dim.width, dim.height));
			Graphics g = this.screenCanvas.getGraphics();
			g.drawImage(bImage, 0, 0, this);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private synchronized void stop(){
		stop = true;
	}
	
	//test
	public static void main(String[] args) {
		final Capture capture = new Capture();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				capture.setVisible(true);
			}
		});
		Thread t = new Thread(capture);
		t.start();
	}
	
	
}