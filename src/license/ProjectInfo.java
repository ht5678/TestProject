/**
 * 
 */
package license;


/**
 * 项目信息<br>
 * 1=项目名称,2=客户名称,3=用户上线,4=在线用户上线,5=注册类型,6=有效期,7=邮箱,8=联系电话,9=联系人,<br>
 * 10=工作目录,11=cpu数量,12=第一块网卡的名称,13=第一块网卡的MAC,14=第2块网卡的名称,15=第2块网卡的MAC
 * 
 * @author ender
 * 
 */
public class ProjectInfo {

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
	private byte[] reg;

	// public static void main(String[] aa) throws Exception {
	// new ProjectInfo().save();
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}

	public Integer getMaxOnlineUsers() {
		return maxOnlineUsers;
	}

	public void setMaxOnlineUsers(Integer maxOnlineUsers) {
		this.maxOnlineUsers = maxOnlineUsers;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public String getMacName1() {
		return macName1;
	}

	public void setMacName1(String macName1) {
		this.macName1 = macName1;
	}

	public String getMac1() {
		return mac1;
	}

	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}

	public String getMacName2() {
		return macName2;
	}

	public void setMacName2(String macName2) {
		this.macName2 = macName2;
	}

	public String getMac2() {
		return mac2;
	}

	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}

	public byte[] getReg() {
		return reg;
	}

	public void setReg(byte[] reg) {
		this.reg = reg;
	}
}
