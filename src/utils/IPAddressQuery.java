package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 调用webservie接口查询ip地址
 * 
 * 获得客户端IP的方法，这个服务是国外一网站提供的。使用方法如下：
<script type="text/javascript" src="http://www.hashemian.com/js/visitorIP.js.php" language="JavaScript"></script>
	<script type="text/javascript" language="JavaScript">
	VIH_BackColor = "palegreen";
	VIH_ForeColor = "navy";
	VIH_FontPix = "16";
	VIH_DisplayFormat = "You are visiting from:
	IP Address: %%IP%%
	Host: %%HOST%%";
	VIH_DisplayOnPage = "yes";
</script>
 * 
 * 如果你不想在网页上显示，仅仅想获得IP，用于其他用途，
 * 那么需要把VIH_DisplayOnPage = “yes”改为VIH_DisplayOnPage = “no”。IP变量是VIH_HostIP
 * 
 * @author byht
 *
 */
public class IPAddressQuery {

	/**
	 * 取得URL
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getUrl(String url) throws MalformedURLException{
		URL u = new URL(url);
		return u;
	}
	
	public void printResponseMessage(URL url){
		if(url==null){
			return;
		}
		InputStream is = null;
		try {
			is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			StringBuffer temp = new StringBuffer();
			while((line = br.readLine())!=null){
				temp.append(line);
			}
		   String begin = "{";
		   String end = "}";
		   String json = temp.toString();
		   json = json.substring(json.indexOf(begin), json.indexOf(end) + 1);
		   parserSinaLoopkUpIpInfo(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	} 
	
	public void getLocation(String url , String ip) throws MalformedURLException, IOException{
		printResponseMessage(getUrl(url));
	}
	
	
	 public static IpInfo parserSinaLoopkUpIpInfo(String json)
	 {
	  Gson gson = new Gson();
	  IpInfo ipInfo = null;
	  JsonParser parser = new JsonParser();
	  JsonElement jsonElement = parser.parse(json);
	  if(jsonElement.isJsonObject())
	  {
	   JsonObject JsonObject = (JsonObject)jsonElement;
	   ipInfo = gson.fromJson(JsonObject, IpInfo.class);
	   if(ipInfo != null)
	   {
	    System.out.println(ipInfo.getCountry() + "\t" + 
	    		ipInfo.getProvince() + "\t" + ipInfo.getCity()+ 
	    		"\t" + ipInfo.getDistrict()+
	    		"\t" + ipInfo.getIsp());
	   }
	  }
	  return ipInfo;
	 }
	
	 

class IpInfo{
 	
 	 private String start;
 	 private String end;
 	 private String province;
 	 private String country;
 	 private String city;
 	 private String district;
 	 private String isp;
 	 
 	 
 	@Override
	public String toString() {
		return "IpInfo [start=" + start + ", end=" + end + ", province="
				+ province + ", country=" + country + ", city=" + city
				+ ", district=" + district + ", isp=" + isp + "]";
	}
 	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getStart() {
 		return start;
 	}
 	public void setStart(String start) {
 		this.start = start;
 	}
 	public String getEnd() {
 		return end;
 	}
 	public void setEnd(String end) {
 		this.end = end;
 	}
 	public String getProvince() {
 		return province;
 	}
 	public void setProvince(String province) {
 		this.province = province;
 	}
 	 
 }	 
	
	public static void main(String[] args) {
		IPAddressQuery ia = new IPAddressQuery();
		//ip地址
		String ip ="221.2.160.163";
		try {
			ia.getLocation("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="+ip,null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

