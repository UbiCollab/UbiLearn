package no.ntnu.stud.ubilearn.db;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.models.Article;
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

		long rowId = database.insert(DatabaseHandler.TABLE_QUIZ,null,values);
		return rowId;
	}
	public void insertQuizs(List<Quiz> quizs){	
		for (Quiz quiz : quizs) {
			insertQuiz(quiz);
		}	
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

	public Quiz getQuiz(String id){
		String query = "SELECT  * FROM " + DatabaseHandler.TABLE_QUIZ + " WHERE "
	            + DatabaseHandler.KEY_OBJECT_ID + " = " + id;
		Log.i(LOG, query);
		
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

	private ArrayList<String> parseAnswersToArray(String answers) {

		String[] answersArray = answers.split("|");
		ArrayList<String> answersList = new ArrayList<String>();
		for (int i = 0; i < answersArray.length; i++) {
			answersList.add(answersArray[i].trim());
		}
		
		return answersList;
	}
	
	

}
