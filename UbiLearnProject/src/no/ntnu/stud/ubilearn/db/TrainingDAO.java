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

public class TrainingDAO extends DAO{

	public TrainingDAO(Context context) {
		super(context, "TrainingDAO");
	}
	
	public long insertQuiz(Quiz quiz){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, quiz.getObjectId());
		values.put(DatabaseHandler.KEY_QUESTION, quiz.getQuestion());
		values.put(DatabaseHandler.KEY_ANSWERS, parseAnswersToString(quiz.getAnswers()));
		values.put(DatabaseHandler.KEY_CORRECT, quiz.getCorrect());
		values.put(DatabaseHandler.KEY_OWNER_ID, quiz.getOwnerId());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(quiz.getCreatedAt()));

		log(values.toString());
		
		long rowId;
		if(!exists(DatabaseHandler.TABLE_QUIZ, quiz.getObjectId()))
			rowId = database.insert(DatabaseHandler.TABLE_QUIZ,null,values);
		else
			rowId = database.update(DatabaseHandler.TABLE_QUIZ,values,null,null);
		return rowId;
	}
	public void insertQuizzes(List<Quiz> quizzes){	
		for (Quiz quiz : quizzes) {
			insertQuiz(quiz);
		}	
	}
	

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
	
	private Quiz getQuiz(Cursor result){
		String objectId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OBJECT_ID));
		String question = result.getString(result.getColumnIndex(DatabaseHandler.KEY_QUESTION));
		ArrayList<String> answers = parseAnswersToArray(result.getString(result.getColumnIndex(DatabaseHandler.KEY_ANSWERS)));
		String correct = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CORRECT));
		String ownerId = result.getString(result.getColumnIndex(DatabaseHandler.KEY_OWNER_ID));
		String createdAt = result.getString(result.getColumnIndex(DatabaseHandler.KEY_CREATED_AT));
		
		
		
		return new Quiz(question, answers, correct, objectId, ownerId, stringToDate(createdAt));
		
	}
	
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
	
	private String parseAnswersToString(ArrayList<String> answers) {
		
		StringBuilder output = new StringBuilder();
		
		output.append(answers.get(0));
		
		for (int i = 1; i < answers.size(); i++) {
			output.append(" | ");
			output.append(answers.get(i));
		}
		return output.toString();
	}

	private ArrayList<String> parseAnswersToArray(String answers) {
		
		String[] answersArray = answers.split("\\|");
		ArrayList<String> answersList = new ArrayList<String>();
		for (int i = 0; i < answersArray.length; i++) {
			answersList.add(answersArray[i].trim());
		}
		
		return answersList;
	}
	
	public long insertCasePatient(CasePatient patient){
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_OBJECT_ID, patient.getObjectId());
		values.put(DatabaseHandler.KEY_NAME, patient.getName());
		values.put(DatabaseHandler.KEY_AGE, patient.getAge());
		values.put(DatabaseHandler.KEY_GENDER, patient.getGender());
		values.put(DatabaseHandler.KEY_INFO, patient.getInfo());
		values.put(DatabaseHandler.KEY_LEVEL, patient.getLevel());
		values.put(DatabaseHandler.KEY_CREATED_AT, dateToString(patient.getCreatedAt()));
		
		log(values.toString());
		
		long rowId;
		if(!exists(DatabaseHandler.TABLE_CASE_PATIENT, patient.getObjectId()))
			rowId = database.insert(DatabaseHandler.TABLE_CASE_PATIENT,null,values);
		else
			rowId = database.update(DatabaseHandler.TABLE_CASE_PATIENT,values,null,null);
		return rowId;
	}
	public void insertCasePatients(List<CasePatient> patients){	
		for (CasePatient patient : patients) {
			insertCasePatient(patient);
		}	
	}
	

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



}
