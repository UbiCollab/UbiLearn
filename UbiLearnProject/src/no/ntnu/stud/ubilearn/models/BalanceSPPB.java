package no.ntnu.stud.ubilearn.models;

public class BalanceSPPB extends SPPB{
	private int pairedScore;
	private int semiTandemScore;
	private int tandemScore;
	
	
	@Override
	public int getScore() {
		return pairedScore + semiTandemScore + tandemScore;
	}
	
}
