package domain;

import java.io.Serializable;

public class Sequence implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Integer value;
	
	
	/*-----------------------------业务逻辑-----------------------------------*/
	
	public static Integer getInteger(String name){
		System.out.println("数据库中查询sequence数据，查询的数据ID为===："+name);
		return 23;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
