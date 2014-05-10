package no.ntnu.stud.ubilearn.models;

public class CasePatientStatus {

	int highScore;
	boolean isComplete;
	String objectId;
	
	public CasePatientStatus(int highScore, boolean isComplete) {
		this.highScore = highScore;
		this.isComplete = isComplete;
		objectId = null;
	}
	
	public CasePatientStatus(int highScore, boolean isComplete, String objectId) {
		this.highScore = highScore;
		this.isComplete = isComplete;
		this.objectId = objectId;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		if(this.highScore>highScore){
			return;
		}
		else{
		this.highScore = highScore;
		}
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public String getObjectId() {
		return objectId;
	}
	

}
