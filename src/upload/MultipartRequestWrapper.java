package upload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 文件上传组件
 * @author byht
 *
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper{

	/**
	 * 将文件重命名
	 */
	public static FileRenamePolicy defaultFileRenamePolicy = new FileRenamePolicy() {
		
		public File rename(File file) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = sdf.format(new Date()) + file.getName();
			File newFile = new File(file.getParent()+File.separator+fileName);
			return newFile;
		}
	};
	
	private MultipartRequest multipartRequest;
	
	  public MultipartRequestWrapper(HttpServletRequest req, String upload_tmp_path, int MAX_FILE_SIZE, String enc) throws IOException {
		    super(req);
		    this.multipartRequest = new MultipartRequest(req, upload_tmp_path, MAX_FILE_SIZE, enc, defaultFileRenamePolicy);
		  }

		  public MultipartRequestWrapper(HttpServletRequest req, String upload_tmp_path, int MAX_FILE_SIZE, String enc, FileRenamePolicy fileRenamePolicy) throws IOException
		  {
		    super(req);
		    this.multipartRequest = new MultipartRequest(req, upload_tmp_path, MAX_FILE_SIZE, enc, fileRenamePolicy);
		  }
	
	
	public Map<String, Object> getParameterMap(){
		Map map = new HashMap();
		Enumeration names = getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			String[] values = getParameterValues(name);
			map.put(name, (values!=null)&&(values.length==1)?values[0]:values);
		}
		return map;
	}
	
	public String[] getParameterValues(String name){
		String[] v = super.getParameterValues(name);
		if(v == null){
			v = this.multipartRequest.getParameterValues(name);
		}
		return v;
	}

	public Enumeration<String> getParameterNames(){
		return this.multipartRequest.getParameterNames();
	}
	
	public String getParameter(String name){
		String v = super.getParameter(name);
		if(v == null){
			v = this.multipartRequest.getParameter(name);
		}
		return v;
	}
	
	/**
	 *获取文件的原名字 
	 * @param name
	 * @return
	 */
	public String getOriginalFileName(String name){
		System.out.println("original file name :  "+this.multipartRequest.getOriginalFileName(name));
		return this.multipartRequest.getOriginalFileName(name);
	}
	
	/**
	 * 获取文件重命名后的名字
	 * @param name
	 * @return
	 */
	public String getFilesystemName(String name){
		System.out.println("filesystemname :  "+this.multipartRequest.getFilesystemName(name));
		return this.multipartRequest.getFilesystemName(name);
	}
	
	/**
	 * 取得所有的文件名
	 * @return
	 */
	public Enumeration<String> getFileNames(){
		return this.multipartRequest.getFileNames();
	}
	
	/**
	 * 根据文件名取得文件
	 * @param name
	 * @return
	 */
	public File getFile(String name){
		return this.multipartRequest.getFile(name);
	}
	
	/**
	 * 根据文件名取得文件的类型
	 * @param name
	 * @return
	 */
	public String getContentType(String name) {
		return this.multipartRequest.getContentType(name);
	}

	
	
}
