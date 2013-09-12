package domain;

import java.io.Serializable;


public final class RegularRunData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long key;  //主键

	private long time;  //时间

	private byte status;  //状态
	
	

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	
	
}
