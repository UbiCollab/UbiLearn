package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class Category extends WikiItem{
	private ArrayList<WikiItem> subItems;
	private long id;
	private String objectId;
	private Date createdAt;
	private String parentId;
	private String name;
	
	

	public Category(String name, ArrayList<WikiItem> sub){
		this.name = name;
		this.subItems = sub;
	}
	
	
	
	public Category(String objectId, String name, Date createdAt, String parentId) {
		super();
		this.objectId = objectId;
		this.createdAt = createdAt;
		this.parentId = parentId;
		subItems = new ArrayList<WikiItem>();
		this.name = name;

	}



	public ArrayList<WikiItem> getSub(){
		return subItems;
	}
	
	public boolean hasSubs(){
		if (subItems != null && !subItems.isEmpty()) {
			return true;
		}
		return false;
	}

	public long getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getParentId(){
		return parentId;
	}

	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}


	//havent decided how to represent a top level category
	public boolean isTopLevel() {
		return parentId == null;
	}



	public void addSubItem(WikiItem item) {
		subItems.add(item);
	}
	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;		
	}


	
	public String printContent() {
		return "Category [subItems=" + subItems + ", objectId=" + objectId
				+ ", createdAt=" + createdAt + ", parentId=" + parentId
				+ ", name=" + name + "]";
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}
