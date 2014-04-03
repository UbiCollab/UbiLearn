package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Date;

public class Patient {
	private String objectId;
	private int id;
	private String name;
	private String age;
	private String problems;
	private String comment;
	private ArrayList<SPPB> tests;
	private ArrayList<Exercise> exercises;
	private Date createdAt;
	
	public Patient(String objectId, String name, String age, String problems, String comment, Date createdAt) {
		super();
		this.objectId = objectId;
		this.name = name;
		this.age = age;
		this.problems = problems;
		this.comment = comment;
		tests = new ArrayList<SPPB>();
		exercises = new ArrayList<Exercise>();
		this.createdAt = createdAt;
	}
	public Patient(String objectId, String name, String age, String problems, String comment,ArrayList<SPPB> tests, Date createdAt) {
		super();
		this.objectId = objectId;
		this.name = name;
		this.age = age;
		this.problems = problems;
		this.comment = comment;
		this.tests = tests;
		exercises = new ArrayList<Exercise>();
		this.createdAt = createdAt;
	}
	public Patient(int id, String name, String age, String problems, String comment, Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.problems = problems;
		this.comment = comment;
		tests = new ArrayList<SPPB>();
		exercises = new ArrayList<Exercise>();
		this.createdAt = createdAt;
	}
	public Patient(int id, String name, String age, String problems, String comment,ArrayList<SPPB> tests, Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.problems = problems;
		this.comment = comment;
		this.tests = tests;
		exercises = new ArrayList<Exercise>();
		this.createdAt = createdAt;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
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

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ArrayList<SPPB> getTests() {
		return tests;
	}

	public void setTests(ArrayList<SPPB> tests) {
		this.tests = tests;
	}

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
