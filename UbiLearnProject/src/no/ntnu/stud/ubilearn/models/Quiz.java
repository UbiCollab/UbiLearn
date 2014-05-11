package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import no.ntnu.stud.ubilearn.User;
import android.util.Log;

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
	
	public Quiz(String qstn, String ans1, String ans2, String ans3, String correct){
		this.question = qstn;
		this.answers = new ArrayList<String>();
		this.answers.add(ans1);
		this.answers.add(ans2);
		this.answers.add(ans3);
		this.answers.add(correct);
		this.correct = correct;//riktig svar
	}
	
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
	
	
	public Quiz(String question, ArrayList<String> answers, String correct, String objectId, String ownerId, Date createdAt) {
		super();
		this.question = question;
		this.answers = answers;
		this.correct = correct;
		this.objectId = objectId;
		this.ownerId = ownerId;
		this.createdAt = createdAt;
	}

	public String[] getAlternatives(){
		
		ArrayList<String> shuff = answers;
		Collections.shuffle(shuff);
		
		String[] result = new String[shuff.size()];
		result = shuff.toArray(result);
		
		return result;
	}

	
	public String getQuestion(){
		return this.question;
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}


	public String getCorrect() {
		return correct;
	}


	public String getObjectId() {
		return objectId;
	}


	public String getOwnerId() {
		return ownerId;
	}
	public Date getCreatedAt(){
		return createdAt;
	}


	public boolean checkAnswer(String ans){
		Log.v("ans", ans);
		if(this.correct.equals(ans)){
			//Log.v("ans på plass 3", answers.get(3));
		//	User.getInstance().addPoints();
		
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Quiz [question=" + question + ", answers=" + answers
				+ ", correct=" + correct + ", objectId=" + objectId
				+ ", ownerId=" + ownerId + ", createdAt=" + createdAt + "]";
	}
	
}
