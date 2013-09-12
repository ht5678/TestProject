package device.mouse;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.util.Random;


/**
 * 硬件操作----鼠标控制
 * @author 岳志华
 */
public class MouseController implements Runnable{

	private Dimension dim;
	private Random ran;
	private Robot robot;
	private volatile boolean stop = false;
	
	
	
	//构造方法
	public MouseController(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		ran = new Random();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		
	}

	
	public void run(){
		while(!stop){
			int x = ran.nextInt(dim.width);
			int y = ran.nextInt(dim.height);
			robot.mouseMove(x, y);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			
			//输出鼠标坐标
			Point point = MouseInfo.getPointerInfo().getLocation();
			System.out.println("("+point.x+","+point.y+")");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public synchronized void stop(){
		stop = true;
	}
	
	
	//测试方法
	public static void main(String[] args) {
		MouseController mc = new MouseController();
		Thread t = new Thread(mc);
		System.err.println("Mouse Controller Start : ");
		t.start();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		mc.stop();
		System.out.println("Mouse Controller end :");
	}
}
