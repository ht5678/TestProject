package webservice.soap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.junit.Test;

/**
 * 使用编程方式用soap请求webservice
 * @author byht
 *
 */
public class SoapClient {

	/**
	 * 根据请求的内容响应结果
	 */
	@Test
	public void testResponse(){
//		try {
//			URL url = new URL("http://localhost:8989/ws?wsdl");
//			QName sname = new QName(namespaceURI, "");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
	}
	
	
	/**
	 * 测试创建一个soap请求信息
	 */
	@Test
	public void testCreate(){
		try {
			//创建消息工厂
			MessageFactory factory = MessageFactory.newInstance();
			//根据消息工厂创建SoapMessage
			SOAPMessage message = factory.createMessage();
			//创建soap part
			SOAPPart part = message.getSOAPPart();
			//获取SoapEnvelope
			SOAPEnvelope envelope = part.getEnvelope();
			//可以通过SoapEnvelope有效获取body和header信息
			SOAPBody body = envelope.getBody();
			//可以根据qname创建相应的节点(Qname就是一个带有命名空间的)
			QName qname = new QName("http://server.webservice/", "add", "q0");//<q0:add xmlns="http://server.webservice/">
			//如果使用以下方式进行设置，会见<>转换为&lt;和&gt
			//body.addBodyElement(qname).setValue("<a>1</a><b>2</b>");
			SOAPBodyElement element = body.addBodyElement(qname);
			element.addChildElement("a").setTextContent("11");
			element.addChildElement("b").setTextContent("22");
			//打印消息内容
			message.writeTo(System.out);
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
