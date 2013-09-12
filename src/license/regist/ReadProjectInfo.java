package license.regist;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

class ReadProjectInfo
{
  private static final char[] reg_name = { 'L', 'i', 'c', 'e', 
    'n', 's', 'e', '.', 'd', 'a', 't', 'a' };

  static String rf = null;
  static Long type;
  static Long maxUsers;
  static Long maxOnlineUsers;
  static Long period;
  static String project;

  private static String registerFile()
  {
    if (rf == null) {
      StringBuilder sb = new StringBuilder();
      for (char b : reg_name)
        sb.append(b);
      rf = path() + sb.toString();
    }

    return rf;
  }

  private static String path()
  {
    return ReadProjectInfo.class.getProtectionDomain().getCodeSource()
      .getLocation().getPath().split("lib")[0];
  }

  private static byte[] md5(String info)
    throws Exception
  {
    MessageDigest md = MessageDigest.getInstance("MD5");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < info.length(); i++)
      sb.append(Integer.valueOf(info.charAt(i)));
    return md.digest(sb.toString().getBytes());
  }

  private static Map<Integer, String> readInfo(String info)
  {
    Map map = new HashMap();
    for (String str : info.split("\n")) {
      String[] ss = str.split("=");
      map.put(Integer.valueOf(ss[0]), ss[1]);
    }
    return map;
  }

  static boolean isRight()
  {
    BufferedReader br = null;
    try {
      File f = new File(registerFile());
      if (f.isFile()) {
        FileReader reader = new FileReader(f);
        br = new BufferedReader(reader);
        String info = new Blowfish(Date.class.getName()).decryptString(br
          .readLine());
        String md5 = br.readLine();
        Base64 base64 = new Base64();
        System.out.println(info.length());
        byte[] rmd5 = md5(info);
        byte[] lmd5 = rsa(base64.decode(md5));
        System.out.println(base64.encodeToString(rmd5));
        System.out.println(base64.encodeToString(lmd5));
        if (equalsBytes(rmd5, lmd5)) {
          Map infoMap = readInfo(info);
          if (checkPeriod(infoMap)) {
            if (type.longValue() < 2L)
              return true;
            return (checkCpu(infoMap)) && (checkPath(infoMap)) && 
              (checkMac(infoMap));
          }
        }
        return false;
      }
    }
    catch (Exception e) {
      return false;
    } finally {
      if (br != null)
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    if (br != null) {
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

private static boolean checkMac(Map<Integer, String> infoMap) throws SocketException
  {
    Enumeration<NetworkInterface> interfaces = 
      NetworkInterface.getNetworkInterfaces();
    Base64 base64 = new Base64();
    int i = 12;
    for (NetworkInterface ni : Collections.list(interfaces)) {
      if (ni.getName().substring(0, 1).equalsIgnoreCase("e")) {
        if (!((String)infoMap.get(Integer.valueOf(i))).equalsIgnoreCase(ni.getName()))
          return false;
        i++;
        byte[] mac = ni.getHardwareAddress();

        if (!((String)infoMap.get(Integer.valueOf(i)))
          .equalsIgnoreCase(base64.encodeAsString(mac))) {
          return false;
        }
        i++;
      }
    }
    return true;
  }

  private static boolean checkPath(Map<Integer, String> infoMap)
  {
    System.out.println((String)infoMap.get(Integer.valueOf(10)));
    System.out.println(ReadProjectInfo.class.getProtectionDomain()
      .getCodeSource().getLocation().getPath().split("lib")[0]);
    return ((String)infoMap.get(Integer.valueOf(10))).equalsIgnoreCase(
      ReadProjectInfo.class.getProtectionDomain().getCodeSource()
      .getLocation().getPath().split("lib")[0]);
  }

  private static boolean checkPeriod(Map<Integer, String> infoMap)
  {
    period = Long.valueOf((String)infoMap.get(Integer.valueOf(6)));
    type = Long.valueOf((String)infoMap.get(Integer.valueOf(5)));
    maxOnlineUsers = Long.valueOf((String)infoMap.get(Integer.valueOf(4)));
    maxUsers = Long.valueOf((String)infoMap.get(Integer.valueOf(3)));
    project = (String)infoMap.get(Integer.valueOf(1));
    System.out.println(period);
    System.out.println(type);
    System.out.println(maxOnlineUsers);
    System.out.println(maxUsers);
    System.out.println(project);
    return period.longValue() > System.currentTimeMillis();
  }

  private static boolean checkCpu(Map<Integer, String> infoMap)
  {
    return ((String)infoMap.get(Integer.valueOf(11))).equals(
      String.valueOf(Runtime.getRuntime().availableProcessors()));
  }

  private static boolean equalsBytes(byte[] rmd5, byte[] lmd5)
  {
    if (rmd5.length == lmd5.length) {
      for (int i = 0; i < rmd5.length; i++)
        if (rmd5[i] != lmd5[i])
          return false;
      return true;
    }
    return false;
  }

  private static byte[] rsa(byte[] key) throws Exception {
    PublicKey pubKey = readPublicKeyFromFile();
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(2, pubKey);
    return cipher.doFinal(key);
  }

  static PublicKey readPublicKeyFromFile()
    throws Exception
  {
    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(
      KeyData.publicKey));
    try {
      BigInteger m = (BigInteger)oin.readObject();
      BigInteger e = (BigInteger)oin.readObject();
      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
      KeyFactory fact = KeyFactory.getInstance("RSA");
      return fact.generatePublic(keySpec);
    } finally {
      oin.close();
    }
  }
}