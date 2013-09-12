package test.test;

import dto.annotation.DtoClass;
import dto.annotation.DtoProperty;

@DtoClass(entities={Company.class})
public class CompanyDto {

	@DtoProperty(entityProperty="id",entityClass=Company.class, nullable = false, readOnly = false, trim = false)
	private Integer id;
	
	@DtoProperty(entityProperty="companyName",entityClass=Company.class, nullable = false, readOnly = false, trim = false)
	private String companyName;
	
	@DtoProperty(entityProperty="companyNo",entityClass=Company.class, nullable = false, readOnly = false, trim = false)
	private String companyNo;
	
	@DtoProperty(entityProperty="CompanyType.value",entityClass=Company.class, nullable = false, readOnly = false, trim = false)
	private String companyType;
	
	@DtoProperty(entityProperty="insertDate",entityClass=Company.class, nullable = false, readOnly = false, trim = false)
	private String insertDate;
	
	
	@DtoProperty(entityClass=Company.class,entityProperty="age", nullable = false, readOnly = false, trim = false)
	private int age;
	

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

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
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
		return "CompanyDto [id=" + id + ", companyName=" + companyName
				+ ", companyNo=" + companyNo + ", companyType=" + companyType
				+ ", insertDate=" + insertDate + ", age=" + age + "]";
	}


	
}
