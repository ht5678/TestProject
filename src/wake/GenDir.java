package wake;

import java.io.File;
import java.io.IOException;



/**
 * 自动生成目录和文件
 * @author byht
 *
 */
public class GenDir {
	
	/**
	 * 模块目录名
	 */
	private String dir;
	
	/**
	 * 模块名称
	 */
	private String moduleName;

	public GenDir(String dir , String moduleName){
		this.dir = dir;
		this.moduleName = moduleName;
	}

	
	public void generate() throws IOException{
		File dir = new File(this.dir);
		if(!dir.exists()){//检查目录是否存在
			System.out.println("目录:"+dir+"-不存在");
		}
		//创建文件夹
		String module = this.dir+File.separator+moduleName.toLowerCase();
		//创建控制层文件及目录
		dir = new File(module+File.separator+"controller"+File.separator+moduleName+"Ctrl.js");
		if(!dir.getParentFile().exists())  		dir.getParentFile().mkdirs();
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================控制层");
		//创建model层目录及文件
		dir = new File(module+File.separator+"model"+File.separator+moduleName+"Model.js");
		if(!dir.getParentFile().exists())  		dir.getParentFile().mkdirs();
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================model层");
		//创建store层目录和文件
		dir = new File(module+File.separator+"Store"+File.separator+moduleName+"Store.js");
		if(!dir.getParentFile().exists())  		dir.getParentFile().mkdirs();
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================store层");
		//创建索引页目录和文件
		dir = new File(module+File.separator+"view"+File.separator+moduleName+"Index.js");
		if(!dir.getParentFile().exists())  		dir.getParentFile().mkdirs();
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================索引页");
		//创建编辑页目录和文件
		dir = new File(module+File.separator+"view"+File.separator+moduleName+"Edit.js");
		if(!dir.getParentFile().exists())  		dir.getParentFile().mkdirs();
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================编辑页");
		//创建配置文件
		dir = new File(module+File.separator+moduleName+".js");
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================配置文件");
		//创建按钮 控制文件
		dir = new File(module+File.separator+moduleName+"_config.js");
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================按钮文件");
		//创建jsp
		dir = new File(module+File.separator+moduleName+".jsp");
		if(!dir.exists())									dir.createNewFile();
		System.out.println("====================jsp");
		
		System.out.println();
		System.out.println("目录文件生成成功");
	}
	
	
	
	/**
	 * 入口函数
	 * @param args
	 */
	public static void main(String[] args) {
		//例子
//		GenDir genDir = new GenDir("d://dst", "oilCard");
//		try {
//			genDir.generate();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		GenDir genDir = new GenDir("J://关键词//框架技术//extjs//ExtjsTest//WebContent//define", "oilCard");
		try {
			genDir.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
