package com.mocredit.testcase;

public class CopyClass {

	public static void main(String args[]) {
		NewPeople per1 = new NewPeople("lucy", 22); // declare two entity
		NewPeople per2 = new NewPeople("1", 1);
		per2 = per1; // per2 equal per1
		per2.setName("James"); // change the attribute of per2
		per1.getInfo(); // display and check the result
		per2.getInfo();
	}
}

/**
 * test class
 * 
 * @author zendao
 */
class NewPeople {

	private String name; // define test attributes
	private int age;

	/**
	 * a constructor
	 * 
	 * @param name
	 * @param age
	 */
	public NewPeople(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * this method for printing the result
	 * 
	 * @return
	 */
	public String getInfo() {
		System.out.println("Name of this is :" + this.name + ",and age is :"
				+ this.age);
		return null;
	}

	/**
	 * this method is for change the attribute
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}