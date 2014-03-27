package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class Category extends WikiItem{
	private ArrayList<WikiItem> sub;
	private int id;
	private String objectId;
	private Date createdAt;
	private String name;
	
	

	public Category(String name, ArrayList<WikiItem> sub){
//		this.name = name;
		this.sub = sub;
	}
	
	
	
	public Category(int id, String objectId, Date createdAt, String name) {
		super();
		this.id = id;
		this.objectId = objectId;
		this.createdAt = createdAt;
		this.name = name;
		sub = new ArrayList<WikiItem>();
	}



	public ArrayList<WikiItem> getSub(){
		return sub;
	}
	
	public boolean hasSubs(){
		if (sub != null && !sub.isEmpty()) {
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}



	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;		
	}
	
	@Override
	public String toString(){
		return name;
	}
}
