package license.regist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectInfo
{
  private String name;
  private String client;
  private Integer maxUsers;
  private Integer maxOnlineUsers;
  private Integer type;
  private Long period;
  private String email;
  private String tel;
  private String contact;
  private String path;
  private Integer cpuNumber;
  private String macName1;
  private String mac1;
  private String macName2;
  private String mac2;
  private String to;

  public ProjectInfo(String from)
    throws Exception
  {
    this.to = (from.split("Register")[0] + "License.data");
    FileReader reader = new FileReader(from);
    BufferedReader br = new BufferedReader(reader);
    String[] arrayOfString1;
    int j = (arrayOfString1 = new Blowfish(Date.class.getName()).decryptString(
      br.readLine()).split("\n")).length; for (int i = 0; i < j; i++) {
      String str = arrayOfString1[i];
      String[] s = str.split("=");
      switch (Integer.valueOf(s[0]).intValue()) {
      case 1:
        this.name = s[1];
        break;
      case 2:
        this.client = s[1];
        break;
      case 7:
        this.email = s[1];
        break;
      case 8:
        this.tel = s[1];
        break;
      case 9:
        this.contact = s[1];
        break;
      case 10:
        this.path = s[1];
        break;
      case 11:
        this.cpuNumber = Integer.valueOf(s[1]);
        break;
      case 12:
        this.macName1 = s[1];
        break;
      case 13:
        this.mac1 = s[1];
        break;
      case 14:
        this.macName2 = s[1];
        break;
      case 15:
        this.mac2 = s[1];
      case 3:
      case 4:
      case 5:
      case 6:
      }
    }
  }

  public String encryptProjectInfo()
  {
    return new Blowfish(Date.class.getName()).encryptString(projectInfo());
  }

  private String projectInfo()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("1=").append(this.name).append("\n");
    sb.append("2=").append(this.client).append("\n");
    sb.append("3=").append(this.maxUsers).append("\n");
    sb.append("4=").append(this.maxOnlineUsers).append("\n");
    sb.append("5=").append(this.type).append("\n");
    sb.append("6=").append(this.period).append("\n");
    sb.append("7=").append(this.email).append("\n");
    sb.append("8=").append(this.tel).append("\n");
    sb.append("9=").append(this.contact).append("\n");
    sb.append("10=").append(this.path).append("\n");
    sb.append("11=").append(this.cpuNumber).append("\n");
    sb.append("12=").append(this.macName1).append("\n");
    sb.append("13=").append(this.mac1).append("\n");
    sb.append("14=").append(this.macName2).append("\n");
    sb.append("15=").append(this.mac2).append("\n");

    return sb.toString();
  }

  public String toString() {
    try {
      return encryptProjectInfo() + "\n" + 
        new Base64().encodeAsString(rsa());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void save()
    throws Exception
  {
    FileOutputStream fos = new FileOutputStream(this.to);
    fos.write(toString().getBytes());

    StringBuffer sb = new StringBuffer();
    sb.append("\n客户:");
    sb.append(this.client);
    sb.append("\n项目:");
    sb.append(this.name);
    sb.append("\n版本:");
    switch (this.type.intValue()) {
    case 0:
      sb.append("开发版");
      break;
    case 1:
      sb.append("测试版");
      break;
    case 2:
      sb.append("正式版");
      break;
    }

    sb.append("\n过期：");
    sb.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.period.longValue())));
    fos.write(sb.toString().getBytes());
    fos.close();
  }

  private static PrivateKey readPrivateKeyFromFile()
    throws Exception
  {
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(
      ProjectInfo.class.getClassLoader().getResourceAsStream(
      "private.key")));
    try {
      BigInteger m = (BigInteger)oin.readObject();
      BigInteger e = (BigInteger)oin.readObject();
      RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
      KeyFactory fact = KeyFactory.getInstance("RSA");
      return fact.generatePrivate(keySpec);
    } finally {
      oin.close();
    }
  }

  byte[] md5()
    throws Exception
  {
    MessageDigest md = MessageDigest.getInstance("MD5");
    StringBuilder sb = new StringBuilder();
    String info = projectInfo();
    for (int i = 0; i < info.length(); i++)
      sb.append(Integer.valueOf(info.charAt(i)));
    return md.digest(sb.toString().getBytes());
  }

  byte[] rsa()
    throws Exception
  {
    PrivateKey privateKey = readPrivateKeyFromFile();
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(1, privateKey);
    return cipher.doFinal(md5());
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public Integer getMaxUsers() {
    return this.maxUsers;
  }

  public void setMaxUsers(Integer maxUsers) {
    this.maxUsers = maxUsers;
  }

  public Integer getMaxOnlineUsers() {
    return this.maxOnlineUsers;
  }

  public void setMaxOnlineUsers(Integer maxOnlineUsers) {
    this.maxOnlineUsers = maxOnlineUsers;
  }

  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getPeriod() {
    return this.period;
  }

  public void setPeriod(Long period) {
    this.period = period;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getCpuNumber() {
    return this.cpuNumber;
  }

  public void setCpuNumber(Integer cpuNumber) {
    this.cpuNumber = cpuNumber;
  }

  public String getMacName1() {
    return this.macName1;
  }

  public void setMacName1(String macName1) {
    this.macName1 = macName1;
  }

  public String getMac1() {
    return this.mac1;
  }

  public void setMac1(String mac1) {
    this.mac1 = mac1;
  }

  public String getMacName2() {
    return this.macName2;
  }

  public void setMacName2(String macName2) {
    this.macName2 = macName2;
  }

  public String getMac2() {
    return this.mac2;
  }

  public void setMac2(String mac2) {
    this.mac2 = mac2;
  }

  public String getTo() {
    return this.to;
  }

  public void setTo(String to) {
    this.to = to;
  }
}