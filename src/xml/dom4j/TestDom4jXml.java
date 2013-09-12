package xml.dom4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * dom4j的练习，测试
 * @author byht
 */
public class TestDom4jXml {

	public static void main(String[] args) {
//		createXml();
		createXml2();
	}
	
	
	/**
	 * 利用xpath搜寻xml文件中的元素
	 */
	public void searchXml(){
		
	}
	
	
	public static void createXml2(){
		//生成document文件
		Document doc = DocumentHelper.createDocument();
		Element XMLObject = doc.addElement("XMLObject");
		XMLObject.addNamespace("xsd",
				"http://www.sdeport.gov.cn/xsd/ArrivalSchema.xsd");
		XMLObject.addElement("CLASS_NAME").setText("XMLObject");
		XMLObject.addElement("CLASS_VER").setText("1.0.0.0");
		XMLObject.addElement("XML_TYPE").setText("W3C");
		XMLObject.addElement("XML_VER").setText("1");
		XMLObject.addElement("XML_XSD_URL");
		XMLObject.addElement("APP_CODE").setText("QDCMTArrival");
		XMLObject.addElement("APP_VER").setText("1");
		XMLObject.addElement("APP_STEP_ID").setText("type");
		XMLObject.addElement("APP_PARA");
		XMLObject.addElement("APP_EXT_PARA");
		XMLObject.addElement("FILE_FROM_URL");
		XMLObject.addElement("FILE_TO_URL");
		XMLObject.addElement("FILE_DATE_TIME").setText(new Date().toString());
		XMLObject.addElement("FILE_GERATER").setText("QDSLIM");
		XMLObject.addElement("FILE_ORDER").setText("0");
		XMLObject.addElement("FILE_ORIGINAL_NAME").setText("test.ARRX");
		XMLObject.addElement("FILE_SIZE").setText("0");
		XMLObject.addElement("FIGURE_SIGNATURE");
		
		
		//outputformat用于格式化输出
//		OutputFormat format = OutputFormat.createCompactFormat();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		try {
			XMLWriter out = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
					new File("g:/test.xml"))),format);
			out.write(doc);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * dom4j生成文件测试一
	 */
	public static void createXml(){
		//生成document文件
		Document document = DocumentHelper.createDocument();
		//生成根元素
		Element rootElement = document.addElement("student-list");
		rootElement.addAttribute("classname", "sd1004");
		//生成叶子元素
		Element stuElement = rootElement.addElement("student");
		stuElement.addAttribute("id", "100");
		
		Element nameElement = stuElement.addElement("name");
		Element scoreElement = stuElement.addElement("score");
		Element sexElement = stuElement.addElement("sex");
		
		nameElement.setText("zhangsan");
		scoreElement.setText("100");
		sexElement.setText("man");
		
		//outputformat用于格式化输出
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("utf-8");
		
		try {
			XMLWriter out = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
					new File("g:/test.xml"))),format);
			out.write(document);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
