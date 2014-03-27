package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class Category extends WikiItem{
	private ArrayList<WikiItem> subItems;
	private long id;
	private String objectId;
	private Date createdAt;
	private long parentId;
	private String name;
	
	

	public Category(String name, ArrayList<WikiItem> sub){
		this.name = name;
		this.subItems = sub;
	}
	
	
	
	public Category(long id, String objectId, String name, Date createdAt, long parentId) {
		super();
		this.id = id;
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

	public long getParentId(){
		return parentId;
	}

	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}


	//havent decided how to represent a top level category
	public boolean isTopLevel() {
		return id == parentId || id == -1;
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
	
	@Override
	public String toString(){
		return name;
	}
}
