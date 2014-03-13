package no.ntnu.stud.ubilearn.patientcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import no.ntnu.stud.ubilearn.User;

import android.util.Log;

public class Quiz {
	private String qstn;
	private ArrayList<String> answers; //answers[3] er riktig svar
	private String correct;
	
	public Quiz(String qstn, String ans1, String ans2, String ans3, String correct){
		this.qstn = qstn;
		this.answers = new ArrayList<String>();
		this.answers.add(ans1);
		this.answers.add(ans2);
		this.answers.add(ans3);
		this.answers.add(correct);
		this.correct = correct;//riktig svar
	}
	
	public String[] getAlternatives(){
		
		ArrayList<String> shuff = answers;
		Collections.shuffle(shuff);
		
		String[] result = new String[shuff.size()];
		result = shuff.toArray(result);
		
		return result;
	}

	
	public String getQstn(){
		return this.qstn;
	}
	
	public boolean checkAnswer(String ans){
		Log.v("ans", ans);
		if(this.correct.equals(ans)){
			//Log.v("ans på plass 3", answers.get(3));
			User.getInstance().addPoints();
			System.out.println("antall poeng: "+ User.getInstance().getPoints());
			return true;
		}
		return false;
	}
	
}
