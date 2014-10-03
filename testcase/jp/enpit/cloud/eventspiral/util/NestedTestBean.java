package jp.enpit.cloud.eventspiral.util;

import java.util.ArrayList;


public class NestedTestBean {
	private int id;
	private ArrayList<TestBean> testBeans;
	
	public NestedTestBean() {
		this(-1, new ArrayList<TestBean>());
	}
	public NestedTestBean(int id, ArrayList<TestBean> testBean) {
		setId(id);
		setTestBeans(testBean);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<TestBean> getTestBeans() {
		return testBeans;
	}
	public void setTestBeans(ArrayList<TestBean> testBeans) {
		this.testBeans = testBeans;
	}
	public String toString() {
		return "{id:" + id + ", testBeans:" + testBeans + "}";
	}
	
}