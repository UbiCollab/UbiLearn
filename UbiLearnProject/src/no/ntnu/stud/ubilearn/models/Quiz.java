package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;

import no.ntnu.stud.ubilearn.User;
import android.util.Log;

/**
 * a model class for the quiz
 * @author ingeborgoftedal
 *
 */
public class Quiz{
	private String question;
	private ArrayList<String> answers; //answers[3] er riktig svar
	private String correct;
	private String objectId;
	/**
	 * the the same as the id of a patient
	 */
	private String ownerId;
	private Date createdAt;
	
	/**
	 * constructor for quiz
	 * @param qstn the quiz question
	 * @param ans1 wrong answer
	 * @param ans2 wrong answer
	 * @param ans3 wrong answer
	 * @param correct correct answer
	 */
	public Quiz(String qstn, String ans1, String ans2, String ans3, String correct){
		this.question = qstn;
		this.answers = new ArrayList<String>();
		this.answers.add(ans1);
		this.answers.add(ans2);
		this.answers.add(ans3);
		this.answers.add(correct);
		this.correct = correct;//riktig svar
	}
	
	/**
	 * constructor for quiz
	 * @param qstn the quiz question
	 * @param ans1 wrong answer
	 * @param ans2 wrong answer
	 * @param ans3 wrong answer
	 * @param correct correct answer
	 * @param ownerId the patient who own this quiz
	 */
	public Quiz(String qstn, String ans1, String ans2, String ans3, String correct, String ownerId){
		this.question = qstn;
		this.answers = new ArrayList<String>();
		this.answers.add(ans1);
		this.answers.add(ans2);
		this.answers.add(ans3);
		this.answers.add(correct);
		this.correct = correct;//riktig svar
		this.ownerId = ownerId;
		createdAt = new Date();
		objectId = ownerId+correct+(qstn.length()*Math.random());
	}
	
	/**
	 * constructor for quiz
	 * @param qstn the quiz question
	 * @param ans1 wrong answer
	 * @param ans2 wrong answer
	 * @param ans3 wrong answer
	 * @param correct correct answer
	 * @param ownerId the patient who own this quiz
	 * @param createdAt when the quiz is created
	 */
	public Quiz(String question, ArrayList<String> answers, String correct, String objectId, String ownerId, Date createdAt) {
		super();
		this.question = question;
		this.answers = answers;
		this.correct = correct;
		this.objectId = objectId;
		this.ownerId = ownerId;
		this.createdAt = createdAt;
	}
	
	/**
	 * method for returning the answers in a random order
	 * @return answers in random order
	 */
	public String[] getAlternatives(){
		
		ArrayList<String> shuff = answers;
		Collections.shuffle(shuff);
		
		String[] result = new String[shuff.size()];
		result = shuff.toArray(result);
		
		return result;
	}

	/**
	 * returns the question
	 * @return the question
	 */
	public String getQuestion(){
		return this.question;
	}
	
	/**
	 * 
	 * @return the answers in order
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * 
	 * @return the correct answer
	 */
	public String getCorrect() {
		return correct;
	}

	/**
	 * 
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * 
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}
	
	/**
	 * 
	 * @return when the quiz was created
	 */
	public Date getCreatedAt(){
		return createdAt;
	}

	
	/**
	 * method for checking if the correct answer is chosen
	 * @param ans the answer chosen
	 * @return true or false, depending on the answer
	 */
	public boolean checkAnswer(String ans){
		Log.v("ans", ans);
		if(this.correct.equals(ans)){

			return true;
		}
		return false;
	}
	
	/**
	 * toString() method
	 */
	@Override
	public String toString() {
		return "Quiz [question=" + question + ", answers=" + answers
				+ ", correct=" + correct + ", objectId=" + objectId
				+ ", ownerId=" + ownerId + ", createdAt=" + createdAt + "]";
	}
	
}
