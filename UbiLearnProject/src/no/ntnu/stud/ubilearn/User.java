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
	
	//-------------------------------------------------------------------------
	/**
	 * Retrieves the name of the user that is currently logged in the
	 * application.
	 * 
	 * @return The name of the user that is currently logged in.
	 */
/*	public String getName()
	{
		
	}
*/	//-------------------------------------------------------------------------
	/**
	 * Retrieves a list of all the levels in the training part.
	 * 
	 * @return A list of the levels in the training part 
	 */
	/* N�r det gjelder denne s� er jeg usikker p� hvordan det har blitt tenkt
		med hensyn til � lagre niv� og hus. Men det jeg har gjort er � lage to
		klasser; TrainingLevel og TrainingHouse som kan brukes. Slik at denne 
		metoden kan returnere alle levels/niv�, hvor hvert niv� inneholder data
		for det spesifikke niv�, samt en liste med objekter av type 
		TrainingHouse. TrainingHouse inneholder data for det spesifikke huset.
		Denne metoden vil da hente alle data med en gang og kanskje dette er
		litt dumt, at det kan ta litt tid f�r data blir hentet. Kunne kanskje
		ha gjort det s�nn at man kaller en metode som henter data for et
		spesifikt hus n�r man velger et niv� fra listen i "Home"-siden, men
		dette kan sikkert endres senere hvis det blir aktuelt.  
	*/
/*	public List<TrainingLevels> getLevels()
	{
		
	}
*/	//-------------------------------------------------------------------------
}
