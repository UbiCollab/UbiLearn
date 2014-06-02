package no.ntnu.stud.ubilearn.models;

import java.util.Date;


/**
 * model class for case patient
 * @author ingeborgoftedal
 *
 */

public class CasePatient implements Comparable<CasePatient> {
	
	private String objectId;
	private String name;
	private String age;
	private String gender;
	private String info;
	private int level;
	private Date createdAt;
	
	
	/**
	 * constructor for case patient
	 * @param name 
	 * @param age
	 * @param gender
	 * @param info
	 * @param level
	 */
	public CasePatient(String name, String age, String gender, String info, String level){
		objectId = name + age;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.info = info;
		this.level = Integer.parseInt(level);
		createdAt = new Date();
	}
	/**
	 * constructor for case patient
	 * @param objectId
	 * @param name
	 * @param age
	 * @param gender
	 * @param info
	 * @param level
	 * @param createdAt
	 */
	public CasePatient(String objectId, String name, String age, String gender, String info, int level, Date createdAt){
		this.objectId = objectId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.info = info;
		this.level = level;
		this.createdAt = createdAt;
	}
	
	/**
	 * this returns the name
	 * @return name of the patient
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * this sets the name
	 * @param name name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * this returns the age
	 * @return age of the patient
	 */
	public String getAge() {
		return age;
	}
	
	/**
	 * this sets the age
	 * @param age age to be set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * this returns the gender
	 * @return gender of the patient
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * this sets the gender
	 * @param gender gender to be set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * this return info about the patient
	 * @return info about the patient
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * this sets the info
	 * @param info info to be set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	/**
	 * returns the level the patient is in
	 * @return level of the patient
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * this sets the level of the patient
	 * @param level level to be set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * returns the objectId of the patients
	 * @return objectId of the patient
	 */
	public String getObjectId() {
		return objectId;
	}
	
	/**
	 * return when the patient was created
	 * @return when the patient was created
	 */
	public Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * method for comparing this patient to another for sorting
	 */
	@Override
	public int compareTo(CasePatient another) {
		if (this.level < another.getLevel()) {
			return -1;
		}
		if (this.level > another.getLevel()) {
			return 1;
		}
		return 0;
	}

}
