package demo.rest;

public class StudentBean {
	private String name = "";

	private int age = 0;

	private String address = "";

	private String rmk = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	@Override
	public String toString() {
		return "StudentBean [anem=" + name + ", age=" + age + ",address=" + address + ",rmk=" + rmk + "]";
	}
}
