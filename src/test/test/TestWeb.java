package test.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

public class TestWeb {

	public static void main(String[] args) {
		
		String property = "voyage.id";
		System.out.println((StringUtils.right(property, 3)));
		System.out.println((!property.equalsIgnoreCase("id")) && (property.length() <= 3)
				|| (!StringUtils.right(property, 3).equalsIgnoreCase(".id")));
		
		URL url = null;
			
			try {
				url = new URL("http://baidu.com");
				try {
					InputStream in = url.openStream();
					in.close();
					System.out.println("网络连接正常！");
				} catch (IOException e) {
					System.out.println("网络连接失败！");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
}
