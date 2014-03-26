package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class Category extends WikiItem{
	private ArrayList<WikiItem> sub;
	private int id;
	private String objectId;
	private Date createdAt;
	private String title;
	
	

	public Category(String name, ArrayList<WikiItem> sub){
		this.name = name;
		this.sub = sub;
	}
	
	
	
	public Category(int id, String objectId, Date createdAt, String title) {
		super();
		this.id = id;
		this.objectId = objectId;
		this.createdAt = createdAt;
		this.title = title;
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

	public String getTitle() {
		return title;
	}
	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}
	
}
