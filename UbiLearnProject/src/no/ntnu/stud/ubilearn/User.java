package no.ntnu.stud.ubilearn;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.models.*;

public class User {
	private ArrayList<CasePatient> patientList;
	
	private static User instance;
	private int points;
	
	private User(){
	}
	
	public static User getInstance(){
		if(instance != null){
			return instance;
		}else{
			instance = new User();
			return instance;
		}
	}
	//pasient listen
	public ArrayList<CasePatient> getPatientList() {
		return patientList;
	}

	public void setPatientList(ArrayList<CasePatient> patientList) {
		this.patientList = patientList;
	}
	//poeng til quizen
	public int addPoints(){
		return points++;
	}
	public int getPoints(){
		return points;
	}
	
}
