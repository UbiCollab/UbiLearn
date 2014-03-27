package no.ntnu.stud.ubilearn.models;

import java.util.Date;

public class Patient {
	
	private String name;
	private String age;
	private String gender;
	private String info;
	private int level;
	private String objectId;
	private Date createdAt;
	
	
	
	public Patient(String name, String age, String gender, String info, String level){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.info = info;
		this.level = Integer.parseInt(level);	
	}
	public Patient(String objectId, String name, String age, String gender, String info, int level, Date createdAt){
		this.objectId = objectId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.info = info;
		this.level = level;
		this.createdAt = createdAt;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public String getObjectId() {
		return objectId;
	}
	public Date getCreatedAt(){
		return createdAt;
	}

}
