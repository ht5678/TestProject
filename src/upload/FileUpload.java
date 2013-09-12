package upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * cos实现文件的上传demo
 * http://localhost:8080/TestProject/upload/upload.jsp
 */
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FileUpload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final int permitedSize = 3 * 1024 * 1024;
		MultipartRequestWrapper multipartRequest = new MultipartRequestWrapper(request,"c:\\", permitedSize, "UTF-8");
		File file = multipartRequest.getFile("upload");
		System.out.println(file.getParentFile());
		System.out.println(multipartRequest.getContentType("upload"));
		System.out.println(multipartRequest.getOriginalFileName("upload"));
		System.out.println(multipartRequest.getFilesystemName("upload"));
	}

}
