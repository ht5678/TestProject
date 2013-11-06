package device.keyboard;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;


/**
 * 硬件操作----键盘控制
 * @author 岳志华
 *
 */
public class RobotKey {

	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec("notepad");//打开记事本
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Robot robot = new Robot();
			
//			//定义三秒延迟
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			//释放资源
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			for(int i = 0 ;i<500 ;i++){
				robot.keyPress(KeyEvent.VK_N);
				robot.keyPress(KeyEvent.VK_I);
				robot.keyPress(KeyEvent.VK_H);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyPress(KeyEvent.VK_O);
				robot.keyPress(KeyEvent.VK_SPACE);
				//释放资源
				robot.keyRelease(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_I);
				robot.keyRelease(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_O);
				robot.keyRelease(KeyEvent.VK_SPACE);
			}
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
