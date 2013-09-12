package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 图像工具
 * @author 岳志华
 *
 */
public class ImageUtil {

	/**
	 * byte数组转换成对象
	 * @param imgDatas
	 * @return
	 */
	public static BufferedImage bytes2img(byte[] imgDatas){
		if(imgDatas == null){
			throw new RuntimeException("解析-参数为空");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(imgDatas);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			System.out.println("image工具类解析图像失败："+e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 将图像转换成byte数组
	 * @return
	 */
	public static byte[] img2bytes(BufferedImage bimg){
		if(bimg == null){
			throw new RuntimeException("参数为空");
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bimg, "jpg", baos);
		} catch (IOException e) {
			System.out.println("ImageUtil工具类转换失败:"+e.getMessage());
		}
		return baos.toByteArray();
	}
	
}
