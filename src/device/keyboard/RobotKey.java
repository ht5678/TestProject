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
			
			//定义三秒延迟
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_H);
			robot.keyPress(KeyEvent.VK_E);
			robot.keyPress(KeyEvent.VK_L);
			robot.keyPress(KeyEvent.VK_L);
			robot.keyPress(KeyEvent.VK_O);
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
