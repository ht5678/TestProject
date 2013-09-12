package utils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import domain.RegularRunData;

/**
 *	20个非常有用的Java程序片段
 *
 *1. 字符串有整型的相互转换
 *2. 向文件末尾添加内容
 *3. 得到当前方法的名字
 *4. 转字符串到日期
 *5. 使用JDBC链接Oracle
 *6. 把 Java util.Date 转成 sql.Date
 *7. 使用NIO进行快速的文件拷贝
 *8. 创建图片的缩略图
 *9. 创建 JSON 格式的数据
 *10. 使用iText JAR生成PDF
 *11. HTTP 代理设置
 *12.单实例Singleton 示例
 *13. 抓屏程序
 *14. 列出文件和目录
 *15. 创建ZIP和JAR文件
 *16. 解析/读取XML 文件
 *17. 把 Array 转换成 Map 
 *18. 发送邮件
 *19. 发送代数据的HTTP 请求
 *20. 改变数组的大小
 *21.21.生成规则字符串(X代表字符串，9代表数字)
 *22.用XSD校验XML格式
 *23.自动将字符串格式化成时间，用于未知格式，效率较低
 *24.webservice--jersey-client,jersey-server
 *25.dom4j--xpath查询xml中的节点,,,***本项目的xml包中含有更加详细的介绍和例子
 *26.按主键大小顺序插入或更新数据
 * @author 岳志华
 *
 */
public class CommonUtils {
	
	   /**
	    * 按主键大小顺序插入或更新数据
	    * RegularRunData data 需要插入或更新的数据
	    * @throws DocumentException 
	   **/
	   public void util26(final RegularRunData data)throws IOException, DocumentException
	   {
		   SAXReader reader = new SAXReader();
		   org.dom4j.Document document = reader.read(new FileInputStream(new File("")));
		   List<Element> eles = document.selectNodes("//domain[key=1101]");
//		   for(Element ele : eles){
//			   System.out.println(ele.elementText("status"));
//		   }
		   if(eles!=null && eles.size()==1){//说明记录存在，更新
			   List<Element> elements = eles.get(0).elements();
			   for(Element ele : elements){
				   if("time".equals(ele.getName())){
					   ele.setText(String.valueOf(new Date().getTime()));
				   }else if("status".equals(ele.getName())){
					   ele.setText("d");
				   }
			   }
		   }else{//记录不存在，插入
			   Element dst = DocumentHelper.createElement("domain");
			   dst.addElement("key").setText(String.valueOf(data.getKey()));
			   dst.addElement("time").setText(String.valueOf(data.getTime()));
			   dst.addElement("status").setText(String.valueOf(data.getStatus()));
			   //
			   List<Element> all = document.content();
			   Element select = (Element)document.selectSingleNode("//domain[key=1101]");
			   all.add(all.indexOf(select), dst);
			   document.setContent(all);
		   }
			//OutputFormat 用于格式化输出
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			try {
				XMLWriter out = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
						new File("c://test//test.xml"))),format);
				out.write(document);
				out.flush();
				out.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		   //TODO:关闭连接
	   }

	   /**
	    * 查询数据
	    * long key 主键
	 * @throws DocumentException 
	   **/
	   public RegularRunData util25(final long key)throws IOException, DocumentException
	   {
		   RegularRunData data = null;
		   SAXReader reader = new SAXReader();
		   org.dom4j.Document document = reader.read(new FileInputStream(new File("")));
		   List<Element> eles = document.selectNodes("//domain[key=1101]");
		   if(eles!=null && eles.size()==1){//返回数据
			   data = new RegularRunData();
			   data.setKey(Long.valueOf(eles.get(0).elementText("key")));
			   data.setTime(Long.valueOf(eles.get(0).elementText("time")));
			   data.setStatus(eles.get(0).elementText("status").getBytes()[0]);
		   }
		   //TODO:关闭连接
		   	return data;
	   }  
	
	/**
	 * 网络接口测试
	 * jersey
	 * 主要是jackson将返回的json数据转换成实体比较有意思
	 */
	public void util24(){
		// 构造客户端发出远程请求
//		ClientConfig config = new DefaultClientConfig();
//		config.getClasses().add(JacksonJsonProvider.class);
//		Client client = Client.create(config);
//		client.setReadTimeout(60000);
//		client.resource(UriBuilder.fromUri(
//	    "http://localhost:8080/TestWebService").build());
//		
//		//发送请求，取得数据
//		WebResource webResoure = RomoteSetting.getWebService(service);
//		ClientResponse upResponse = webResoure.path("/expMF/getSrCtnBySrId")
//				.queryParam("srId", srId).entity(null)
//				.type(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		//将jason串转换成实体
//		return upResponse.getEntity(new GenericType<List<ExpMFCtnDto>>(){});
	}
	
	
	/**
	 * 自动将字符串格式化成时间，用于未知格式，效率较低
	 * @param changeDate 要转换的字符串
	 * @param pattern 时间格式
	 * @return
	 * @throws ParseException 
	 */
	public static Date autoFormatStringToDate(String changeDate) throws ParseException{
		if(changeDate == null){
			return null;
		} else {
			DateFormat dateFormat = null;
			if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H:m");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H");
			} else {
				dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
			}
			return dateFormat.parse(changeDate);
		}
	}
	
	
	//22.用XSD校验XML格式
	/**
	 * 用XSD校验XML格式
	 */
	public static Boolean validateByXSD(String schemaLocaltion, String request) throws SAXException, IOException{
        // 获取Schema工厂类  
        // 这里的XMLConstants.W3C_XML_SCHEMA_NS_URI的值就是：//http://www.w3.org/2001/XMLSchema  
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);  
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
        // 获取XSD文件，以流的方式读取到Source中  
        // XSD文件的位置相对于类文件位置  
        Source schemaSource = new StreamSource(new FileInputStream(schemaLocaltion));  
        Schema schema = factory.newSchema(schemaSource);  
        // 获取验证器，验证器的XML Schema源就是之前创建的Schema  
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes("UTF-8"));  
        Validator validator = schema.newValidator();  
        Source source = new StreamSource(inputStream);  
        // 执行验证  
        validator.validate(source);
    	// 关闭输入流
    	inputStream.close();
    	return true;
	}
	
	//21.生成规则字符串(X代表字符串，9代表数字)
	/**
	 * 生成规则字符串
	 * 0-9  48-57
	 * a-z	97-122
	 * A-Z 65-90
	 * @return
	 * @throws BusinessException 
	 */
	private String generate(String rule,String gen) {
		char[] chs = rule.toCharArray();
		StringBuffer sb = new StringBuffer();
		//初始化
		if("0".equals(gen)){
			for(int i = 0 ; i < chs.length  ; i++){
				if('X' == chs[i]){
					sb.append("a");
				}
				if('9' == chs[i]){
					sb.append("0");
				}
			}
		}else{
			char[] gens = gen.toCharArray();
			for(int i = (chs.length - 1); i >=0 ; i--){
				if('X' == chs[i]){//字母
					if(gens[i]=='z'){//极限
						gens[i] = 'a';//重置
					}else{
						gens[i]++;
						return new String(gens);//返回规则
					}
				}else if('9' == chs[i]){//数字
					if(gens[i]=='9'){//极限
						gens[i] = '0';//重置
					}else{
						gens[i]++;
						return new String(gens);//返回规则
					}
				}
			}
		}
		return sb.toString();
	}
	
	//20. 改变数组的大小
	/**  
	* Reallocates an array with a new size, and copies the contents  
	* of the old array to the new array.  
	* @param oldArray  the old array, to be reallocated.  
	* @param newSize   the new array size.  
	* @return          A new array with the same contents.  
	*/  
	private static Object resizeArray (Object oldArray, int newSize) {   
	   int oldSize = java.lang.reflect.Array.getLength(oldArray);   
	   Class elementType = oldArray.getClass().getComponentType();   
	   Object newArray = java.lang.reflect.Array.newInstance(   
	         elementType,newSize);   
	   int preserveLength = Math.min(oldSize,newSize);   
	   if (preserveLength > 0)   
	      System.arraycopy (oldArray,0,newArray,0,preserveLength);   
	   return newArray;   
	}   
	   
	// Test routine for resizeArray().   
	public static void main (String[] args) {   
	   int[] a = {1,2,3};   
	   a = (int[])resizeArray(a,5);   
	   a[3] = 4;   
	   a[4] = 5;   
	   for (int i=0; i<a.length; i++)   
	      System.out.println (a[i]);   
	} 
	
	
	//19. 发送代数据的HTTP 请求
	public void util19(){
	        try {   
	            URL my_url = new URL("http://coolshell.cn/");   
	            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));   
	            String strTemp = "";   
	            while(null != (strTemp = br.readLine())){   
	            System.out.println(strTemp);   
	        }   
	        } catch (Exception ex) {   
	            ex.printStackTrace();   
	        }   
	}
	
	
	//18. 发送邮件
	//import javax.mail.*;   
	//import javax.mail.internet.*;   
	public void postMail( String recipients[ ], String subject, String message , String from) throws Exception   
	{   
//	    boolean debug = false;   
//	   
//	     //Set the host smtp address   
//	     Properties props = new Properties();   
//	     props.put("mail.smtp.host", "smtp.example.com");   
//	   
//	    // create some properties and get the default Session   
//	    Session session = Session.getDefaultInstance(props, null);   
//	    session.setDebug(debug);   
//	   
//	    // create a message   
//	    Message msg = new MimeMessage(session);   
//	   
//	    // set the from and to address   
//	    InternetAddress addressFrom = new InternetAddress(from);   
//	    msg.setFrom(addressFrom);   
//	   
//	    InternetAddress[] addressTo = new InternetAddress[recipients.length];   
//	    for (int i = 0; i < recipients.length; i++)   
//	    {   
//	        addressTo[i] = new InternetAddress(recipients[i]);   
//	    }   
//	    msg.setRecipients(Message.RecipientType.TO, addressTo);   
//	   
//	    // Optional : You can also set your custom headers in the Email if you Want   
//	    msg.addHeader("MyHeaderName", "myHeaderValue");   
//	   
//	    // Setting the Subject and Content Type   
//	    msg.setSubject(subject);   
//	    msg.setContent(message, "text/plain");   
//	    Transport.send(msg);   
	}  
	
	
	//17. 把 Array 转换成 Map 
	public void util17() {
		String[][] countries = { { "United States", "New York" },
				{ "United Kingdom", "London" }, { "Netherland", "Amsterdam" },
				{ "Japan", "Tokyo" }, { "France", "Paris" } };

		Map countryCapitals = ArrayUtils.toMap(countries);

		System.out.println("Capital of Japan is "
				+ countryCapitals.get("Japan"));
		System.out.println("Capital of France is "
				+ countryCapitals.get("France"));
	}
	
	/**
	 * 16. 解析/读取XML 文件
	 * @see xml.W3cDom#getAllUserNames(String)
	 */
	public void util16(){
		
	}
	
	//15. 创建ZIP和JAR文件
	class ZipIt {   
	    public void main(String args[]) throws IOException {   
	        if (args.length < 2) {   
	            System.err.println("usage: java ZipIt Zip.zip file1 file2 file3");   
	            System.exit(-1);   
	        }   
	        File zipFile = new File(args[0]);   
	        if (zipFile.exists()) {   
	            System.err.println("Zip file already exists, please try another");   
	            System.exit(-2);   
	        }   
	        FileOutputStream fos = new FileOutputStream(zipFile);   
	        ZipOutputStream zos = new ZipOutputStream(fos);   
	        int bytesRead;   
	        byte[] buffer = new byte[1024];   
	        CRC32 crc = new CRC32();   
	        for (int i=1, n=args.length; i < n; i++) {   
	            String name = args[i];   
	            File file = new File(name);   
	            if (!file.exists()) {   
	                System.err.println("Skipping: " + name);   
	                continue;   
	            }   
	            BufferedInputStream bis = new BufferedInputStream(   
	                new FileInputStream(file));   
	            crc.reset();   
	            while ((bytesRead = bis.read(buffer)) != -1) {   
	                crc.update(buffer, 0, bytesRead);   
	            }   
	            bis.close();   
	            // Reset to beginning of input stream   
	            bis = new BufferedInputStream(   
	                new FileInputStream(file));   
	            ZipEntry entry = new ZipEntry(name);   
	            entry.setMethod(ZipEntry.STORED);   
	            entry.setCompressedSize(file.length());   
	            entry.setSize(file.length());   
	            entry.setCrc(crc.getValue());   
	            zos.putNextEntry(entry);   
	            while ((bytesRead = bis.read(buffer)) != -1) {   
	                zos.write(buffer, 0, bytesRead);   
	            }   
	            bis.close();   
	        }   
	        zos.close();   
	    }   
	}  
	
	
	//14. 列出文件和目录
	public void util14(){
		File dir = new File("directoryName");   
		  String[] children = dir.list();   
		  if (children == null) {   
		      // Either dir does not exist or is not a directory   
		  } else {   
		      for (int i=0; i < children.length; i++) {   
		          // Get filename of file or directory   
		          String filename = children[i];   
		      }   
		  }   
		   
		  // It is also possible to filter the list of returned files.   
		  // This example does not return any files that start with `.'.   
		  FilenameFilter filter = new FilenameFilter() {   
		      public boolean accept(File dir, String name) {   
		          return !name.startsWith(".");   
		      }   
		  };   
		  children = dir.list(filter);   
		   
		  // The list of files can also be retrieved as File objects   
		  File[] files = dir.listFiles();   
		   
		  // This filter only returns directories   
		  FileFilter fileFilter = new FileFilter() {   
		      public boolean accept(File file) {   
		          return file.isDirectory();   
		      }   
		  };   
		  files = dir.listFiles(fileFilter); 
	}
	
	
	//13. 抓屏程序
	public void util13(String fileName) throws Exception {   
		   
		   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
		   Rectangle screenRectangle = new Rectangle(screenSize);   
		   Robot robot = new Robot();   
		   BufferedImage image = robot.createScreenCapture(screenRectangle);   
		   ImageIO.write(image, "png", new File(fileName));   
		   
		}   
	
	
	//12.单实例Singleton 示例
	//内部类:
		class SimpleSingleton {   
		    private SimpleSingleton singleInstance =  new SimpleSingleton();   
		   
		    //Marking default constructor private   
		    //to avoid direct instantiation.   
		    private SimpleSingleton() {   
		    }   
		   
		    //Get instance for class SimpleSingleton   
		    public SimpleSingleton getInstance() {   
		   
		        return singleInstance;   
		    }   
		}  
	
	//11. HTTP 代理设置
	public void util11(){
		System.getProperties().put("http.proxyHost", "someProxyURL");   
		System.getProperties().put("http.proxyPort", "someProxyPort");   
		System.getProperties().put("http.proxyUser", "someUserName");   
		System.getProperties().put("http.proxyPassword", "somePassword"); 
	}
	
	//10. 使用iText JAR生成PDF
	 public void util10() {   
	        try {   
	            OutputStream file = new FileOutputStream(new File("C:\\Test.pdf"));   
	   
	            Document document = new Document();   
	            PdfWriter.getInstance(document, file);   
	            document.open();   
	            document.add(new Paragraph("Hello Kiran"));   
	            document.add(new Paragraph(new Date().toString()));   
	   
	            document.close();   
	            file.close();   
	   
	        } catch (Exception e) {   
	   
	            e.printStackTrace();   
	        }   
	    }   
	
	//9. 创建 JSON 格式的数据
	public void util9(){
		JSONObject json = new JSONObject();   
		json.put("city", "Mumbai");   
		json.put("country", "India");   
		String output = json.toString();  
	}
	
	
	//8. 创建图片的缩略图
	private void util8(String filename, int thumbWidth,
			int thumbHeight, int quality, String outFilename)
			throws InterruptedException, FileNotFoundException, IOException {
		// load image from filename
		Image image = Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);
		// use this to test for errors at this point:
		// System.out.println(mediaTracker.isErrorAny());

		// determine thumbnail size from WIDTH and HEIGHT
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}

		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

		// save thumbnail image to outFilename
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(outFilename));
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
		quality = Math.max(0, Math.min(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(thumbImage);
		out.close();
	}
	
	
	//7. 使用NIO进行快速的文件拷贝
	public static void util7(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			// inChannel.transferTo(0, inChannel.size(), outChannel); //
			// original -- apparently has trouble copying large files on Windows

			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel
						.transferTo(position, maxCount, outChannel);
			}
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

	//6. 把 Java util.Date 转成 sql.Date
	public void util6(){
		java.util.Date utilDate = new java.util.Date();   
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	}
	
	//5. 使用JDBC链接Oracle
	public void util5(){
		
	}
	
	
	//4. 转字符串到日期
	public void util4(){
		//伪代码
//		java.util.Date = java.text.DateFormat.getDateInstance().parse(date String); 
		
		//方法二
		String str = "";
		SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy" );   
		try {
			Date date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//3. 得到当前方法的名字
	public void util3(){
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();  
	}
	
	//2. 向文件末尾添加内容
	public void util2() throws IOException{
		BufferedWriter out = null;   
		try {   
		    out = new BufferedWriter(new FileWriter("filename", true));   
		    out.write("aString");   
		} catch (IOException e) {   
		    // error processing code   
		} finally {   
		    if (out != null) {   
		        out.close();
		    }   
		} 
	}
	
	//1. 字符串有整型的相互转换
	public void str2int(){
		String a = String.valueOf(2);   //integer to numeric string   
		int i = Integer.parseInt(a); //numeric string to an int  
	}

}
