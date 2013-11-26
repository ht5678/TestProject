package xml.stax;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * stax练习，
 * 和xtream有一定的关联，据说xstream这种东西可以处理大数据量的xml文件
 * @author byht
 *
 */
public class TestStax {
	
	/**
	 * 第八个练习：使用xpath定位，然后用Transformer修改节点的文本
	 */
	@Test
	public void test08(){
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			//创建文件builder
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//创建文件处理对象
			Document document = builder.parse(is);
			//创建xpath
			XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//查找title为Learning XML的元素节点
			NodeList list = (NodeList) xpath.evaluate("//book[title='Learning XML']", document , XPathConstants.NODESET);
			for(int i = 0 ; i < list.getLength() ; i++){
				Element e = (Element)list.item(0);
				//输出书的价格
				System.out.println(e.getElementsByTagName("price").item(0).getTextContent());
				
				 e = (Element)(e.getElementsByTagName("price").item(0));
				 
				 e.setTextContent("333.9");
				 
				 Result result = new StreamResult(System.out);
				//通过tranformer修改节点
				 transformer.transform(new DOMSource(document), result);
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			try {
				if(is !=null)
				is.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 第七个练习：写出一个xml文件，并且添加一个xsi的命名空间
	 */
	@Test
	public void test07(){
		try {
			XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeEndDocument();
			String ns = "http://11:dd";
			writer.writeStartElement("xsi","person",ns);
			//添加命名空间
			writer.writeNamespace("xsi", ns);
			writer.writeNamespace("", "http://22:ss");
			
			writer.writeStartElement(ns, "id");
			writer.writeCharacters("23");
			writer.writeEndElement();
			writer.writeEndElement();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 第六个练习：练习使用xpath查找节点元素
	 */
	@Test
	public void test06(){
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			//创建文档处理对象
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//通过DocumentBuilder创建doc的文档对象
			Document document = builder.parse(is);
			//创建xpath
			XPath xPath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
			//第一个参数就是xpath,第二个参数是文档，第三个参数是返回类型
			NodeList list = (NodeList) xPath.evaluate("//book[@category='WEB']", document, XPathConstants.NODESET);
			for(int i = 0 ; i < list.getLength() ; i ++){
//				Node node = list.item(i);
//				for(int j = 0 ; j < node.getChildNodes().getLength() ; j++){
//					System.out.println(node.getChildNodes().item(j).getNodeName());
//				}
				//遍历输出相应的结果
				Element e = (Element)list.item(i);
				System.out.println(e.getElementsByTagName("title").item(0).getTextContent());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		}
		
	}
	
	
	/**
	 * 第五个练习：练习使用XmlEventReader过滤器
	 */
	@Test
	public void test05(){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			//基于Filter的过滤方式，可以有效的过滤掉不用进行操作的节点，效率会高一些
			XMLEventReader reader = factory.createFilteredReader(factory.createXMLEventReader(is), 
					new EventFilter() {
						@Override
						public boolean accept(XMLEvent event) {
							if(event.isStartElement()){
								String name = event.asStartElement().getName().toString();
								//返回true会显示，返回false不会显示
								if("title".equals(name) || "price".equals(name)){
									return true;
								}
							}
							return false;
						}
					});
			int num = 0 ;
			while(reader.hasNext()){
				//通过XMLEvent来获取是否是某种节点类型
				XMLEvent event = reader.nextEvent();
				if(event.isStartElement()){
					//通过event.asXXX来转换节点
					String name = event.asStartElement().getName().toString();
					if("title".equals(name)){
						System.out.println(reader.getElementText()+":");
					}
					if("price".equals(name)){
						System.out.println(reader.getElementText()+"\n");
					}
				}
				num++;
			}
			System.out.println(num);
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 第四个练习:使用XmlEventReader,遍历所有的书名和书价格
	 */
	@Test
	public void test04(){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		int num = 0;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			//基于迭代模型的操作方式
			XMLEventReader eventReader = factory.createXMLEventReader(is);
			while(eventReader.hasNext()){
				//通过XmlEvent来获取是否是某种节点类型
				XMLEvent event = eventReader.nextEvent();
				if(event.getEventType() == XMLStreamConstants.START_ELEMENT){
					//通过event.asXXX来转换节点
					String name = event.asStartElement().getName().toString();
					if("title".equals(name)){
						System.out.println(eventReader.getElementText()+":");
					}
					if("price".equals(name)){
						System.out.println(eventReader.getElementText()+"\n");
					}
				}
				num++;
			}
			System.out.println(num);
			eventReader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		}
	}
	
	
	
	/**
	 * 第三个练习：输出xml中书名和书的价格
	 */
	@Test
	public void test03(){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			XMLStreamReader reader  =  factory.createXMLStreamReader(is);
			while(reader.hasNext()){
				int type = reader.next();
				//如果是开始元素
				if(type == XMLStreamConstants.START_ELEMENT){
					String name = reader.getName().toString();
					//如果开始元素是  title
					if("title".equals(name)){
						//输出书名
						System.out.print(reader.getElementText()+":");
					}
					//如果开始元素是price
					if("price".equals(name)){
						//输出价格
						System.out.print(reader.getElementText()+"\n");
					}
				}
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * 第二个练习:查找book元素的属性attribute(category)
	 */
	@Test
	public void test02(){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			//遍历所有的xml元素
			while(reader.hasNext()){
				int type = reader.next();
				//如果这个xml元素是一个开始元素
				if(type == XMLStreamConstants.START_ELEMENT){
					//如果这个开始元素是book
					if("book".equals(reader.getName().toString())){
						//输出book元素的category属性
						System.out.println(reader.getAttributeName(0)+":"+reader.getAttributeValue(0));
					}
				}
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		}
		
	}
	
	
	
	/**
	 * 第一个练习，练习基本的使用
	 */
	@Test
	public void test01(){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try{
			is = TestStax.class.getClassLoader().getResourceAsStream("xml/stax.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while(reader.hasNext()){
				int type = reader.next();
				//判断节点类型是否是开始或者结束或者文本节点，之后根据情况来处理
				if(type==XMLStreamConstants.START_ELEMENT){
					System.out.println(reader.getName());
				}else if(type == XMLStreamConstants.CHARACTERS){
					System.out.println(reader.getText().trim());
				}else if(type == XMLStreamConstants.END_ELEMENT){
					System.out.println("/"+reader.getName());
				}
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{//关闭流
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					System.out.println("流关闭错误");
				}
		}
	}

}
