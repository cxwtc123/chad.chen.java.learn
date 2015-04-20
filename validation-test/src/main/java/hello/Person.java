package hello;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {

	@NotNull
	@Size(min=15,max=18)
	private String id;
	
	@NotNull
	@Size(min=1,max=16)
	private String name;
	
	@NotNull	
	@Max(value=200)
	@Min(value=0)
	private Integer age;
	
	@NotNull
	@Pattern(regexp="^male|female$")
	private String sex;
	
	private String addr;
	
	@Size(min=3,max=15)
	private String callNo;
	
	@NotNull
	@Pattern(regexp="^(.+)@(.+)$")
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(
			@NotNull
			Integer age ) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCallNo() {
		return callNo;
	}

	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
