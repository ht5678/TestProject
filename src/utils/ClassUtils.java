package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassUtils
{
  public static void main(String[] paramArrayOfString)
  {
    Set localSet = getDtoClasses("E:\\workspace\\hlcy\\WebContent\\WEB-INF\\classes", "com.beiyanght.ferry");
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      System.out.println(str);
    }
  }

  public static Set<String> getDtoClasses(String paramString1, String paramString2)
  {
    Set localSet = getClasses(paramString1, paramString2);
    HashSet localHashSet = new HashSet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = str1.substring(0, str1.lastIndexOf("."));
      String str3 = str2.substring(0, str2.lastIndexOf("."));
      String str4 = str3.substring(0, str3.lastIndexOf("."));
      if ((!".dto".equalsIgnoreCase(str2.substring(str2.lastIndexOf(".")))) || (!".business".equalsIgnoreCase(str4.substring(str4.lastIndexOf(".")))) || (!str1.endsWith("Dto")))
        localHashSet.add(str1);
    }
    localSet.removeAll(localHashSet);
    return localSet;
  }

  public static Set<String> getClasses(String paramString1, String paramString2)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    boolean bool = true;
    String str1 = paramString2;
    String str2 = str1.replace('.', '/');
    System.out.println("目录:" + str2);
    System.out.println("search dir:" + paramString1 + File.separator + str2);
    File localFile1 = new File(paramString1 + File.separator + str2);
    for (File localFile2 : localFile1.listFiles())
      findAndAddClassesInPackageByFile(str1 + "." + localFile2.getName(), localFile2.getAbsolutePath(), bool, localLinkedHashSet);
    return localLinkedHashSet;
  }

  public static void findAndAddClassesInPackageByFile(String paramString1, String paramString2, boolean paramBoolean, Set<String> paramSet)
  {
    File localFile1 = new File(paramString2);
    if ((!localFile1.exists()) || (!localFile1.isDirectory()))
      return;
    File[] arrayOfFile1 = localFile1.listFiles(new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
//        return ((this.val$recursive) && (paramAnonymousFile.isDirectory())) || (paramAnonymousFile.getName().endsWith(".class"));
    	  return true;
      }
    });
    for (File localFile2 : arrayOfFile1)
      if (localFile2.isDirectory())
      {
        findAndAddClassesInPackageByFile(paramString1 + "." + localFile2.getName(), localFile2.getAbsolutePath(), paramBoolean, paramSet);
      }
      else
      {
        String str = localFile2.getName().substring(0, localFile2.getName().length() - 6);
        paramSet.add(paramString1 + '.' + str);
      }
  }
}