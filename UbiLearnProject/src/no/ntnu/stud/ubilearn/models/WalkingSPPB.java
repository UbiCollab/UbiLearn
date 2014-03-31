package no.ntnu.stud.ubilearn.models;

public class WalkingSPPB extends SPPB{
	private double time;
	private boolean noAid;
	private boolean crutches;
	private boolean rollater;
	private String other;

	@Override
	public int getScore() {
		if(time>8.7){
			return 1;
		}else if(time>6.21 && time<8.7){
			return 2;
		}
		else if(time>4.82 && time<6.2){
			return 3;
		}
		else if(time < 4.82){
			return 4;
		}
		return 0;
	}

}
