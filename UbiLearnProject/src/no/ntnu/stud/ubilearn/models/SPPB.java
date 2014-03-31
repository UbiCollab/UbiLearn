package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Date;

public abstract class SPPB {
	protected String objectId;
	protected String name;
	protected int score;
	protected Patient patient;
	protected Date createdAt;
	
	public abstract int getScore();
	
	
}
