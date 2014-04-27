package no.ntnu.stud.ubilearn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.stud.ubilearn.models.*;


public class User {
	private ArrayList<CasePatient> patientList;
	
	private static User instance;
	private int points;
	
	private String _name;
	
	// Delete when testing is done and data can be retrieved from database
	private List<TrainingLevel> _levelList;
	

	//#########################################################################
	private User()
	{
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//Test using initialized objects of type TrainingHouse and 
		// TrainingLevel. Delete this when actual data can be retrieved from
		// database.
		_name = "Ola Nordman";	// Test data.
		
		_levelList = new ArrayList<TrainingLevel>();		
				
		
		List<TrainingHouse> houseListLevel1	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("Dagmar", false, 5, 11), 
						new TrainingHouse("Hallvard", false, 9, 12),
						new TrainingHouse("Fredrik", false, 8, 10),
						new TrainingHouse("Ragnhild", false, 2, 10),
						new TrainingHouse("Kyrre", false, 11, 11),
						new TrainingHouse("Espen", false, 13, 15),
						new TrainingHouse("Ingeborg", false, 2, 4),
						new TrainingHouse("Vegard", false, 0, 9),
						new TrainingHouse("Haldis", false, 0, 11),
						new TrainingHouse("Alfredo", true, 0, 9),
						new TrainingHouse("Kristian", true, 0, 15))); 
				
		List<TrainingHouse> houseListLevel2	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("House 1", false, 10, 10),
						new TrainingHouse("House 2", false, 10, 10),
						new TrainingHouse("House 3", false, 10, 10),
						new TrainingHouse("House 4", false, 10, 10),
						new TrainingHouse("House 5", false, 10, 10)
						));
		
		List<TrainingHouse> houseListLevel3	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("House 1", false, 3, 10),
						new TrainingHouse("House 2", false, 0, 10),
						new TrainingHouse("House 3", true, 0, 10),
						new TrainingHouse("House 4", true, 0, 10),
						new TrainingHouse("House 5", true, 0, 10)
						));
			
		List<TrainingHouse> houseListLevel4	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("House 1", true, 0, 10),
						new TrainingHouse("House 2", true, 0, 10),
						new TrainingHouse("House 3", true, 0, 10),
						new TrainingHouse("House 4", true, 0, 10),
						new TrainingHouse("House 5", true, 0, 10)
						));
		
		List<TrainingHouse> houseListLevel5	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("House 1", true, 0, 10),
						new TrainingHouse("House 2", true, 0, 10),
						new TrainingHouse("House 3", true, 0, 10),
						new TrainingHouse("House 4", true, 0, 10),
						new TrainingHouse("House 5", true, 0, 10)
						));
		
		List<TrainingHouse> houseListLevel6	= 
				new ArrayList<TrainingHouse>(Arrays.asList(
						new TrainingHouse("House 1", true, 0, 10),
						new TrainingHouse("House 2", true, 0, 10),
						new TrainingHouse("House 3", true, 0, 10),
						new TrainingHouse("House 4", true, 0, 10),
						new TrainingHouse("House 5", true, 0, 10)
						));		
		
		
		TrainingLevel level1 = new TrainingLevel("Level 1", false, 50, 117,
				houseListLevel1);
		
		TrainingLevel level2 = new TrainingLevel("Fall", false, 50, 50,
				houseListLevel2);
		
		TrainingLevel level3 = new TrainingLevel("Level 3", false, 3, 50,
				houseListLevel3);
		
		TrainingLevel level4 = new TrainingLevel("Level 4", true, 0, 50,
				houseListLevel4);
		
		TrainingLevel level5 = new TrainingLevel("Level 5", true, 0, 50,
				houseListLevel5);
		
		TrainingLevel level6 = new TrainingLevel("Level 6", true, 0, 50,
				houseListLevel6);
		
		
		_levelList.add(level1);
		_levelList.add(level2);
		_levelList.add(level3);
		_levelList.add(level4);
		_levelList.add(level5);
		_levelList.add(level6);			
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
	//-------------------------------------------------------------------------
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
	public String getName()
	{
		return _name;
	}
	//-------------------------------------------------------------------------
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
	public List<TrainingLevel> getLevels()
	{
		return _levelList;	
	}
	//-------------------------------------------------------------------------
	/**
	 * Function returns a TrainingLevel object specified by the position in 
	 * the list of levels.
	 * 
	 * @param position - The position in the list of levels.
	 * @return An object of type TrainingLevel or null if object in specified 
	 * position does not exist.
	 */
	public TrainingLevel getLevelNo(int position)
	{   
		try{
			TrainingLevel level = _levelList.get(position);
			
			return level;
		}
		catch(IndexOutOfBoundsException exception)
		{
			return null;
		}
	}
}
