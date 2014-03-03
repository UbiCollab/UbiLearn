package no.ntnu.stud.ubilearn.patientcase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Quiz {
	private String qstn;
	private String[] answers; //answers[3] er riktig svar
	
	public Quiz(String qstn, String ans1, String ans2, String ans3, String ans4){
		this.qstn = qstn;
		this.answers = new String[4];
		this.answers[0] = ans1;
		this.answers[1] = ans2;
		this.answers[2] = ans3;
		this.answers[3] = ans4;
	}
	
	public String[] getAlternatives(){
//		Random random = new Random();
//		Set<String> uniqueAns = new HashSet<String>();
//		while(uniqueAns.size() < 4){
//			uniqueAns.add(this.answers[random.nextInt(3)]);
//		}
//		return (String[]) uniqueAns.toArray();
		return this.answers;
	}
	
	public String getQstn(){
		return this.qstn;
	}
	
	public boolean checkAnswer(String ans){
		if(ans.equals(this.answers[3])){
			return true;
		}
		return false;
	}
}
