package xml.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 出口空箱申请EDI格式
 * @author 曲丹丹
 *
 */
@XmlRootElement(name="Manifest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpEmpEDI {
	
/*----命名空间----*/
	
	/**
	 * XMLNS
	 */
	// 报文在<Manifest>部分定义名命空间为urn:Declaration:datamodel:standard:CN: [name]:1, 其中[name]部分标注报文的类型，具体请参考《中国海关进出境舱单报文类型编码表》，“1”代表主版本号。
	@XmlAttribute(name="xmlns")
	private String xmlns = "urn:Declaration:datamodel:standard:CN:MTECEX:1";
	
	// 报文头
	@XmlElement(name="Head", required = true)
	private Head head = new Head();
	
	
	/**
	 * 报文头
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Head{
		/**
		 * 报文编号
		 * 值：发送方代码_当前时间
		 */
		@XmlElement(name="MessageID", required = true)
		private String messageID ;
		/**
		 * 报文功能代码
		 * 值：与报文类型有关
		 */
		@XmlElement(name="FunctionCode", required = true)
		private String functionCode;
		/**
		 * 报文类型代码
		 * 值：当前报文类型
		 */
		@XmlElement(name="MessageType", required = true)
		private String messageType;
		/**
		 * 发送方代码
		 * 值：手动赋值
		 */
		@XmlElement(name="SenderID", required = true)
		private String senderID;
		/**
		 * 接受方代码
		 * 值：手动赋值
		 */
		@XmlElement(name="ReceiverID", required = true)
		private String receiverID;
		/**
		 * 发送时间
		 * 值：当前时间
		 */
		@XmlElement(name="SendTime", required = true)
		private String sendTime;
		/**
		 * 报文版本号
		 * 值：1.0
		 */
		@XmlElement(name="Version", required = true)
		private String version = "1.0";
	}

	 // 报文体
	@XmlElement(name="Declaration", required = true)
	private Declaration declaration = new Declaration();
	
	/**
	 * 报文体
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Declaration{
		//监管场所经营人数据段
		@XmlElement(name="RepresentativePerson", required = true)
		private RepresentativePerson representativePerson = new RepresentativePerson();
		//申报地海关代码
		@XmlElement(name="DeclarationOfficeID", required = true)
		private String declarationOfficeID;
		//运输工具
		@XmlElement(name="BorderTransportMeans", required = true)
		private BorderTransportMeans borderTransportMeans = new BorderTransportMeans();
		//提（运）单数据段
		@XmlElement(name="Consignment", required = true)
		private List<Consignment> consignmentList = new ArrayList<Consignment>();
		
		//监管场所经营人数据段
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class RepresentativePerson{
			//监管场所经营人名称
			@XmlElement(name="Name", required = true)
			private String name;
		}
		//运输工具
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class BorderTransportMeans{
			//航次航班编号
			@XmlElement(name="JourneyID", required = true)
			private String journeyID;
			//运输工具代码
			@XmlElement(name="ID", required = true)
			private String id;
		}
		//提（运）单数据段
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Consignment{
			//运输合同信息
			@XmlElement(name="TransportContractDocument", required = true)
			private TransportContractDocument transportContractDocument = new TransportContractDocument();
			//运输合同信息
			@XmlAccessorType(XmlAccessType.FIELD)
			public static class TransportContractDocument{
				//总提（运）单号
				@XmlElement(name="ID", required = true)
				private String id;
			}
		}
	}
	
	public ExpEmpEDI(){
		
	}
	
	/**
	 * 测试出口空箱
	 */
	public static void main(String[] args) {
//		try {
//			JAXBContext context = JAXBContext.newInstance(ExpEmpEDI.class);
//			Marshaller marshaller = context.createMarshaller();
//			//这句代码就是控制格式的，屏蔽代码会使得文件内容变成一行
//	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//	        marshaller.marshal(test, System.out);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}

	}
	/**
	 * 获取MessageID
	 * @return
	 */
	public String getMessageId(){
		return this.head.messageID;
	}
}
