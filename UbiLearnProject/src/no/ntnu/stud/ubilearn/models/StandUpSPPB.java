package no.ntnu.stud.ubilearn.models;

import java.util.Date;


public class StandUpSPPB extends SPPB{
	private double time;

	public StandUpSPPB(String objectId, String name, String patientId, double time, Date createdAt) {
		super(objectId, name, patientId, createdAt);
		this.time = time;
	}
	public StandUpSPPB(String name, int patientId, double time, Date createdAt) {
		super(name, patientId, createdAt);
		this.time = time;
	}
	public StandUpSPPB(int id, String name, int patientId, double time, Date createdAt) {
		super(id,name, patientId, createdAt);
		this.time = time;
	}
	
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

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "StandUpSPPB [time=" + time + ", objectId=" + objectId
				+ ", name=" + name + ", patient=" + patient + ", patientId="
				+ patientId + ", createdAt=" + createdAt + "]";
	}
	
	
}
