package no.ntnu.stud.ubilearn.models;

public class CasePatientStatus {

	int highScore;
	boolean isComplete;
	
	public CasePatientStatus(int highScore, boolean isComplete) {
		this.highScore = highScore;
		this.isComplete = isComplete;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

}
