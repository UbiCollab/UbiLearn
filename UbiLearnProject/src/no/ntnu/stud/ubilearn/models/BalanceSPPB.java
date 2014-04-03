package no.ntnu.stud.ubilearn.models;

import java.util.Date;

public class BalanceSPPB extends SPPB{
	private int pairedScore;
	private int semiTandemScore;
	private int tandemScore;

	
	public BalanceSPPB(String objectId, String name, String patientId,Date createdAt, int pairedScore, int semiTandemScore, int tandemScore) {
		super(objectId, name, patientId, createdAt);
		this.pairedScore = pairedScore;
		this.semiTandemScore = semiTandemScore;
		this.tandemScore = tandemScore;
	}




	@Override
	public int getScore() {
		return pairedScore + semiTandemScore + tandemScore;
	}
	public int getPairedScore() {
		return pairedScore;
	}
	public void setPairedScore(int pairedScore) {
		this.pairedScore = pairedScore;
	}
	public int getSemiTandemScore() {
		return semiTandemScore;
	}
	public void setSemiTandemScore(int semiTandemScore) {
		this.semiTandemScore = semiTandemScore;
	}
	public int getTandemScore() {
		return tandemScore;
	}
	public void setTandemScore(int tandemScore) {
		this.tandemScore = tandemScore;
	}

}
