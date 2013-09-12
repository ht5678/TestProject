package test.test;

public enum CompanyType {

	ENABLE("启用"),
	DIABLE("禁用");
	
	private String value;
	
	private CompanyType(String value){
		this.value = value;
	}

	public int getKey(){
		return this.ordinal();
	}
	
	public String getValue(){
		return this.value;
	}
}
