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
	/* Når det gjelder denne så er jeg usikker på hvordan det har blitt tenkt
		med hensyn til å lagre nivå og hus. Men det jeg har gjort er å lage to
		klasser; TrainingLevel og TrainingHouse som kan brukes. Slik at denne 
		metoden kan returnere alle levels/nivå, hvor hvert nivå inneholder data
		for det spesifikke nivå, samt en liste med objekter av type 
		TrainingHouse. TrainingHouse inneholder data for det spesifikke huset.
		Denne metoden vil da hente alle data med en gang og kanskje dette er
		litt dumt, at det kan ta litt tid før data blir hentet. Kunne kanskje
		ha gjort det sånn at man kaller en metode som henter data for et
		spesifikt hus når man velger et nivå fra listen i "Home"-siden, men
		dette kan sikkert endres senere hvis det blir aktuelt.  
	*/
/*	public List<TrainingLevels> getLevels()
	{
		
	}
*/	//-------------------------------------------------------------------------
}
