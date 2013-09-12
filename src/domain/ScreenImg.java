package domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 用于传输图像的序列化类
 * @author 岳志华
 *
 */
public class ScreenImg implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//构造函数
	public ScreenImg(){
		
	}
	
	public ScreenImg(BufferedImage image){
		this.bimg = bimg;
	}
	
	//图像类
	private BufferedImage bimg;
	

	public BufferedImage getBimg() {
		return bimg;
	}

	public void setBimg(BufferedImage bimg) {
		this.bimg = bimg;
	}

}
