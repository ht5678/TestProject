package xml.dom4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * dom4j的解析，并且用xpath匹配查询结果
 * @author 岳志华
 *
 */
public class dom4jSax {

	
	
	
	/**
	 * xpath
	 * 
	 * 使用方法简单介绍：
	 * 
	 * 获取Document
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(FileUtil.getFileInputStream(fileName));
		查询Element
		String xpath ="/composites/composite[@type='onDelete']";//查询属性type='ondDelete'的composite
		List<Element> composites = document.selectNodes(xpath);
		xpath语法
		选取节点
		XPath 使用路径表达式在 XML 文档中选取节点。节点是通过沿着路径或者 step 来选取的。
		下面列出了最有用的路径表达式：
		表达式	描述
		nodename	选取此节点的所有子节点
		/	从根节点选取
		//	从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置
		.	选取当前节点
		..	选取当前节点的父节点
		@	选取属性
		实例
		在下面的表格中，我们已列出了一些路径表达式以及表达式的结果：
		路径表达式	结果
		bookstore	选取 bookstore 元素的所有子节点
		/bookstore	
		选取根元素 bookstore
		注释：假如路径起始于正斜杠( / )，则此路径始终代表到某元素的绝对路径！
		bookstore/book	选取所有属于 bookstore 的子元素的 book 元素。
		//book	选取所有 book 子元素，而不管它们在文档中的位置。
		bookstore//book	选择所有属于 bookstore 元素的后代的 book 元素，而不管它们位于 bookstore 之下的什么位置。
		//@lang	选取所有名为 lang 的属性。
		谓语（Predicates）
		谓语用来查找某个特定的节点或者包含某个指定的值的节点。
		谓语被嵌在方括号中。
		实例
		在下面的表格中，我们列出了带有谓语的一些路径表达式，以及表达式的结果：
		路径表达式	结果
		/bookstore/book[1]	选取属于 bookstore 子元素的第一个 book 元素。
		/bookstore/book[last()]	选取属于 bookstore 子元素的最后一个 book 元素。
		/bookstore/book[last()-1]	选取属于 bookstore 子元素的倒数第二个 book 元素。
		/bookstore/book[position()<3]	选取最前面的两个属于 bookstore 元素的子元素的 book 元素。
		//title[@lang]	选取所有拥有名为 lang 的属性的 title 元素。
		//title[@lang='eng']	选取所有 title 元素，且这些元素拥有值为 eng 的 lang 属性。
		/bookstore/book[price>35.00]	选取所有 bookstore 元素的 book 元素，且其中的 price 元素的值须大于 35.00。
		/bookstore/book[price>35.00]/title	选取所有 bookstore 元素中的 book 元素的 title 元素，且其中的 price 元素的值须大于 35.00。
		选取未知节点
		XPath 通配符可用来选取未知的 XML 元素。
		通配符	描述
		*	匹配任何元素节点
		@*	匹配任何属性节点
		node()	匹配任何类型的节点
		实例
		在下面的表格中，我们列出了一些路径表达式，以及这些表达式的结果：
		路径表达式	结果
		/bookstorechild::price	选取当前节点的所有 price 孙。
		XPath 运算符
		下面列出了可用在 XPath 表达式中的运算符：
		运算符	描述	实例	返回值
		|	计算两个节点集	//book | //cd	返回所有带有 book 和 ck 元素的节点集
		+	加法	6 + 4	10
		-	减法	6 - 4	2
		*	乘法	6 * 4	24
		div	除法	8 div 4	2
		=	等于	price=9.80	
		如果 price 是 9.80，则返回 true。
		如果 price 是 9.90，则返回 fasle。
		!=	不等于	price!=9.80	
		如果 price 是 9.90，则返回 true。
		如果 price 是 9.80，则返回 fasle。
		<	小于	price<9.80	
		如果 price 是 9.00，则返回 true。
		如果 price 是 9.90，则返回 fasle。
		<=	小于或等于	price<=9.80	
		如果 price 是 9.00，则返回 true。
		如果 price 是 9.90，则返回 fasle。
		>	大于	price>9.80	
		如果 price 是 9.90，则返回 true。
		如果 price 是 9.80，则返回 fasle。
		>=	大于或等于	price>=9.80	
		如果 price 是 9.90，则返回 true。
		如果 price 是 9.70，则返回 fasle。
		or	或	price=9.80 or price=9.70	
		如果 price 是 9.80，则返回 true。
		如果 price 是 9.50，则返回 fasle。
		and	与	price>9.00 and price<9.90	
		如果 price 是 9.80，则返回 true。
		如果 price 是 8.50，则返回 fasle。
		mod	计算除法的余数	5 mod 2
	 * @param ename 元素名称
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public void xpathSearch(String path) throws FileNotFoundException, DocumentException{
		   SAXReader reader = new SAXReader();
		   Document document = reader.read(new FileReader(new File(path)));
		   //**********************************************************
		 //查找domain元素中的key元素值为1101的数据---------------xpath的简单应用
		 //***********************************************************
		   List<Element> eles = document.selectNodes("//domain[key=1101]");
		   //测试结果输出
		   for(Element ele : eles){
			   System.out.println(ele.elementText("status"));
		   }
	}
	
	/**
	 * 测试数据
	 */
	private void init(){
		Document doc = DocumentHelper.createDocument();
		Element domain = doc.addElement("data");
		Element id = domain.addElement("key");
		id.setText("1101");
		Element time = domain.addElement("time");
		time.setText(new Date().toString());
		Element statu = domain.addElement("status");
		statu.setText("enable");
		//outputformat用于格式化输出
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("utf-8");
		
		try {
			XMLWriter out = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
					new File("c://test//test.xml"))),format);
			out.write(doc);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
