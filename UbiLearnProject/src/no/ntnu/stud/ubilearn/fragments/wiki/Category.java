package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class Category extends WikiItem{
	private ArrayList<WikiItem> subItems;
	private long id;
	private String objectId;
	private Date createdAt;
	private String title;
	private long parentId;
	
	

	public Category(String name, ArrayList<WikiItem> sub){
		this.name = name;
		this.subItems = sub;
	}
	
	
	
	public Category(long id, String objectId, String title, Date createdAt, long parentId) {
		super();
		this.id = id;
		this.objectId = objectId;
		this.createdAt = createdAt;
		this.title = title;
		this.parentId = parentId;
		subItems = new ArrayList<WikiItem>();
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

	public String getTitle() {
		return title;
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
	
}
