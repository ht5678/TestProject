package BillCode;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BillCodeModel {

	public static void main(String args[]) throws Exception{
		//取得xml文件流
		InputStream is = BillCodeModel.class.getResourceAsStream("billcode.xml");
		//初始化解析器
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		for(Iterator localIterator = doc.getRootElement().elements("BillCode").iterator(); localIterator.hasNext();){
			Object o = localIterator.next();
			Element e = (Element)o;
			BillCode.addBillCode(e);
		}
	}
	
}
