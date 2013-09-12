package license.regist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Command
{
  private void createRegisterInfo(String infoFile)
    throws Exception
  {
    System.out.println("生成注册文件");
    System.out.println("项目信息文件: " + infoFile);
    System.out.println("注册输出文件: " + infoFile.split("Register")[0] + 
      "License.data");
    ProjectInfo pi = new ProjectInfo(infoFile);
    System.out.println("客户 = " + pi.getClient());
    System.out.println("项目 = " + pi.getName());
    System.out.println("联系人 = " + pi.getContact());
    System.out.println("联系电话 = " + pi.getTel());
    System.out.println("联系邮箱 = " + pi.getEmail());
    System.out.println("工作目录 = " + pi.getPath());

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("License类型 (0: 开发版, 1: 测试版, 2: 正式版) : ");
    int type = Integer.parseInt(readInput(in));
    if ((type < 0) || (type > 2))
      throw new RuntimeException("请输入正确的License类型:0,1,2");
    pi.setType(Integer.valueOf(type));
    System.out.print("过期时间 (yyyy-MM-dd) : ");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    pi.setPeriod(Long.valueOf(sdf.parse(readInput(in)).getTime()));
    System.out.print("最大用户数 : ");
    pi.setMaxUsers(Integer.valueOf(Integer.parseInt(readInput(in))));
    System.out.print("最大在线用户数 : ");
    pi.setMaxOnlineUsers(Integer.valueOf(Integer.parseInt(readInput(in))));
    System.out.print("信息是否正确，可以生成项目信息文件(Y/N): ");
    if (readInput(in).equalsIgnoreCase("Y"))
      pi.save();
  }

  private String readInput(BufferedReader in)
    throws IOException
  {
    String str = in.readLine();
    if (str == null)
      throw new RuntimeException("stop");
    return str;
  }

  private void help() {
    System.out.println("帮助信息");
    System.out.println("根据提示输入许可信息信息");
    System.out.println("java -jar license.jar Register.data");
    System.out.println("根据提示输入验证许可信息信息");
    System.out.println("java -jar license.jar c License.data");
  }

  public void check(String file) {
    System.out.println("当前时间：" + System.currentTimeMillis());
    System.out.println("验证：" + file);
    ReadProjectInfo.rf = file;
    System.out.println("结果:" + ReadProjectInfo.isRight());
    System.out.println("类型:" + ReadProjectInfo.type);
    System.out.println("过期:" + ReadProjectInfo.period);
    System.out.println("用户量:" + ReadProjectInfo.maxUsers);
    System.out.println("在线用户:" + ReadProjectInfo.maxOnlineUsers);
  }

  public static void main(String[] args)
    throws Exception
  {
	  
    Command c = new Command();
    switch (args.length) {
    case 1:
      c.createRegisterInfo(args[0]);
      break;
    case 2:
      c.check(args[1]);
      break;
    default:
      c.help();
    }
  }
}