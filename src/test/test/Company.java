package test.test;

import java.util.Date;


public class Company {
	
	private Integer id;
	
	private String companyName;
	
	private String companyNo;
	
	private CompanyType companyType;
	
	private int age;
	
	private Date insertDate;
	
	/*----------------构造方法-----------------------*/
	public Company(){}

	
	public Company(Integer id, String companyName, String companyNo,
			CompanyType companyType, Date insertDate) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.companyNo = companyNo;
		this.companyType = companyType;
		this.insertDate = insertDate;
	}

	
	
	/*-----------------get set method-------------------------------*/
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyNo() {
		return companyNo;
	}


	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}


	public CompanyType getCompanyType() {
		return companyType;
	}


	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}


	public Date getInsertDate() {
		return insertDate;
	}


	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName
				+ ", companyNo=" + companyNo + ", companyType=" + companyType
				+ ", age=" + age + ", insertDate=" + insertDate + "]";
	}


	
}
