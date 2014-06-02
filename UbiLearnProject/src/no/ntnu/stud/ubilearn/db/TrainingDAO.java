package no.ntnu.stud.ubilearn.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ntnu.stud.ubilearn.models.Article;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.Quiz;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
/**
 * This class acts as a interface between the database and the training fragment, by using training related models.
 *
 */
public class TrainingDAO extends DAO{

	public TrainingDAO(Context context) {
		super(context, "TrainingDAO");
	}
	/**
	 * inserts a quiz into the database
	 * @param quiz the quiz to be inserted
	 * @return the row of the newly inserted quiz
	 */
	public long insertQuiz(Quiz quiz){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, quiz.getObjectId());
		values.put(DatabaseHandler.KEY_QUESTION, quiz.getQuestion());
		values.put(DatabaseHandler.KEY_ANSWERS, parseAnswersToString(quiz.getAnswers()));
		values.put(DatabaseHandler.KEY_CORRECT, quiz.getCorrect());
		values.put(DatabaseHandler.KEY_OWNER_ID, quiz.getOwnerId());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(quiz.getCreatedAt()));
		
		long rowId;
		if(!exists(DatabaseHandler.TABLE_QUIZ, quiz.getObjectId()))
			rowId = database.insert(DatabaseHandler.TABLE_QUIZ,null,values);
		else{
			values.remove(DatabaseHandler.KEY_OBJECT_ID);
			rowId = database.update(DatabaseHandler.TABLE_QUIZ,values, DatabaseHandler.KEY_OBJECT_ID + "=?" , new String[]{quiz.getObjectId()});
		}
		return rowId;
	}
	public void insertQuizzes(List<Quiz> quizzes){	
		for (Quiz quiz : quizzes) {
			insertQuiz(quiz);
		}	
	}
	
	/**
	 * gets a quiz from the database
	 * @param id the id of the quiz to be retreived
	 * @return the requeste quiz
	 */
	public Quiz getQuiz(String id){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_QUIZ + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = '" + id + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			return getQuiz(result);
		else 
			return null;
	}
	/**
	 * uses the result of a query to initilize a quiz
	 * @param result the result of a select query
	 * @return a initlizied quiz object
	 */
	private Quiz getQuiz(Cursor result){
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String question = result.getString(result.getColumnIndex(DatabaseHandler.KEY_QUESTION));
		ArrayList<String> answers = parseAnswersToArray(result.getString(result.getColumnIndex(DatabaseHandler.KEY_ANSWERS)));
		String correct = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CORRECT));
		String ownerId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OWNER_ID));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		
		
		return new Quiz(question, answers, correct, objectId, ownerId, stringToDate(createdAt));
		
	}
	/**
	 * gets all quizzes related to this CasePatient using its id
	 * @param patient the CasePatient who has the quizzes
	 * @return a list of quizzes
	 */
	public ArrayList<Quiz> getPatientQuizzes(CasePatient patient) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_QUIZ + " WHERE "
				+ DatabaseHandler.KEY_OWNER_ID + " = '" + patient.getObjectId() + "'";
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				Quiz quiz = getQuiz(result);
								
				quizzes.add(quiz);
			}while(result.moveToNext());
		else
			return null;
		return quizzes;
	}
	/**
	 * returns the number of quizzes assigned to a CasePatient for a specific level
	 * @param level the level where the number of quizzes will be counted
	 */
	public int getNofQuizzes(int level){
		int nofQuizzes = 0;
		
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_CASE_PATIENT + " INNER JOIN " + DatabaseHandler.TABLE_QUIZ 
				+ " ON " + DatabaseHandler.TABLE_CASE_PATIENT + "." + DatabaseHandler.KEY_OBJECT_ID + " = " + DatabaseHandler.TABLE_QUIZ + "." + DatabaseHandler.KEY_OWNER_ID
				+ " WHERE " + DatabaseHandler.KEY_LEVEL + " = " + level;
		
		log(query);
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst()){		
			do{
				nofQuizzes++;
			}while(result.moveToNext());
		}else
			return 0;		
		
		return nofQuizzes;
	}
	
	/**
	 * Takes in an array of answers and parse to a string format that can be inserted to the database
	 * @param answers an array of the answers
	 * @return a string formatted for the database
	 */
	private String parseAnswersToString(ArrayList<String> answers) {
		
		StringBuilder output = new StringBuilder();
		
		output.append(answers.get(0));
		
		for (int i = 1; i < answers.size(); i++) {
			output.append(" | ");
			output.append(answers.get(i));
		}
		return output.toString();
	}
	/**
	 * takes in a string which were formatted for the database and parses it to seperate answers and puts them in an array
	 * @param answers the string containing all the answers
	 * @return a list of all the answers in the string
	 */
	private ArrayList<String> parseAnswersToArray(String answers) {
		
		String[] answersArray = answers.split("\\|");
		ArrayList<String> answersList = new ArrayList<String>();
		for (int i = 0; i < answersArray.length; i++) {
			answersList.add(answersArray[i].trim());
		}
		
		return answersList;
	}
	/**
	 * inserts a case patient into the database
	 * @param patientthe patient to be inserted
	 * @return the row id of the newly inserted case patient
	 */
	public long insertCasePatient(CasePatient patient){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, patient.getObjectId());
		values.put(DatabaseHandler.KEY_NAME, patient.getName());
		values.put(DatabaseHandler.KEY_AGE, patient.getAge());
		values.put(DatabaseHandler.KEY_GENDER, patient.getGender());
		values.put(DatabaseHandler.KEY_INFO, patient.getInfo());
		values.put(DatabaseHandler.KEY_LEVEL, patient.getLevel());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(patient.getCreatedAt()));
				
		long rowId;
		if(!exists(DatabaseHandler.TABLE_CASE_PATIENT, patient.getObjectId()))
			rowId = database.insert(DatabaseHandler.TABLE_CASE_PATIENT,null,values);
		else{
			values.remove(DatabaseHandler.KEY_OBJECT_ID);
			rowId = database.update(DatabaseHandler.TABLE_CASE_PATIENT,values, DatabaseHandler.KEY_OBJECT_ID + "=?" , new String[]{patient.getObjectId()});
		}
		return rowId;
	}
	/**
	 * inserts a list of case patients into the database
	 * @param patients the list of all case pateints to be inserted
	 */
	public void insertCasePatients(List<CasePatient> patients){	
		for (CasePatient patient : patients) {
			insertCasePatient(patient);
		}	
	}
	
	/**
	 * retrevies a case patient from the database that has this id
	 * @param id the id of the case patient
	 * @return a case patient object
	 */
	public CasePatient getCasePatient(String id){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_CASE_PATIENT + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = '" + id + "'";
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			return getCasePatient(result);
		else 
			return null;
	}
	/**
	 * uses the result of a query to initilize a CasePAtient abject
	 * @param result the result of a select query
	 * @return a initlized CasePatient
	 */
	private CasePatient getCasePatient(Cursor result){
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String name = result.getString(result.getColumnIndex(DatabaseHandler.KEY_NAME));
		String age = result.getString(result.getColumnIndex(DatabaseHandler.KEY_AGE));
		String gender = result.getString(result.getColumnIndex(DatabaseHandler.KEY_GENDER));
		String info = result.getString(result.getColumnIndex(DatabaseHandler.KEY_INFO));
		int level = result.getInt(result.getColumnIndex(DatabaseHandler.KEY_LEVEL));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		return new CasePatient(objectId, name, age, gender, info, level, stringToDate(createdAt));
		
	}
	/**
	 * gets all the CasePatients in the database
	 * @return
	 */
	public ArrayList<CasePatient> getAllCasePatients() {
		
		ArrayList<CasePatient> patients = new ArrayList<CasePatient>();
		String query = "SELECT * FROM " + DatabaseHandler.TABLE_CASE_PATIENT;
		log(query);
		
		Cursor result = database.rawQuery(query, null);
		if(result.moveToFirst())
			do{
				CasePatient patient = getCasePatient(result);
				patients.add(patient);
				
			}while(result.moveToNext());
		else
			return null;
		
		
		return patients;
	}
	/**
	 * prints all the tables and its content related to the training fragment.
	 */
	public void printTables(){
		printTable(DatabaseHandler.TABLE_CASE_PATIENT);
		printTable(DatabaseHandler.TABLE_QUIZ);
	}

}
