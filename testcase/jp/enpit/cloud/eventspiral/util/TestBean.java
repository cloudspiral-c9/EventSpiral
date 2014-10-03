package jp.enpit.cloud.eventspiral.util;

public class TestBean {
	private int age;
	private String name;
	
	public TestBean() {
		this(-1, "");
	}
	public TestBean(int age, String name) {
		setAge(age);
		setName(name);
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "{age:" + age + ", name:" + name + "}";
	}
}
