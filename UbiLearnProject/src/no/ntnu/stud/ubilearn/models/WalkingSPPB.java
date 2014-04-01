package no.ntnu.stud.ubilearn.models;

import java.util.Date;

public class WalkingSPPB extends SPPB{
	private double time;
	private boolean noAid;
	private boolean crutches;
	private boolean rollater;
	private String other;

	
	
	
	public WalkingSPPB(String objectId, String name, String patientId, Date createdAt, double time, boolean noAid, boolean crutches, boolean rollater, String other) {
		super(objectId, name, patientId, createdAt);
		this.time = time;
		this.noAid = noAid;
		this.crutches = crutches;
		this.rollater = rollater;
		this.other = other;
	}
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
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public boolean isNoAid() {
		return noAid;
	}
	public void setNoAid(boolean noAid) {
		this.noAid = noAid;
	}
	public boolean isCrutches() {
		return crutches;
	}
	public void setCrutches(boolean crutches) {
		this.crutches = crutches;
	}
	public boolean isRollater() {
		return rollater;
	}
	public void setRollater(boolean rollater) {
		this.rollater = rollater;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}
