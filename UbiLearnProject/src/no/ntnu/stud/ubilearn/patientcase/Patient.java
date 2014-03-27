package no.ntnu.stud.ubilearn.patientcase;

public class Patient {
	
	private String name;
	private String age;
	private String gender;
	private String info;
	private String level;
	private long id;
	private String objectId;
	
	
	
	public Patient(String name, String age, String gender, String info, String level){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.info = info;
		this.level = level;	
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
