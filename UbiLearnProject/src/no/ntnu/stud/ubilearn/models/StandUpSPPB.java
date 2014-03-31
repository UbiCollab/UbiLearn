package no.ntnu.stud.ubilearn.models;

public class StandUpSPPB extends SPPB{
	private double time;
	@Override
	public int getScore() {
			if(time>60){
				return 0;
			}else if(time >= 16.7){
				return 1;
			}else if(time >13.7&& time < 16.69){
				return 2;
			}else if(time > 11.20 && time < 13.69){
				return 3;
			}else if(time <= 11.19){
				return 4;
			}
		return 0;
	}
}
