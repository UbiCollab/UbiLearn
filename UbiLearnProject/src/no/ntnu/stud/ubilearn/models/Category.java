package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.fragments.handbook.CategoryFragment;

/**
 *Simple model containing data used by {@link CategoryFragment}.
 *This model contains a list of children. All children must extend
 *{@link ListItem}.
 *
 */
public class Category extends ListItem{
	private ArrayList<ListItem> subItems;
	private String objectId;
	private Date createdAt;
	private String name;
	
	

	public Category(String name, ArrayList<ListItem> sub){
		this.name = name;
		this.subItems = sub;
	}
	
	
	
	public Category(String objectId, String name, Date createdAt, String parentId) {
		super();
		this.objectId = objectId;
		this.createdAt = createdAt;
		this.parentId = parentId;
		subItems = new ArrayList<ListItem>();
		this.name = name;

	}



	public ArrayList<ListItem> getSub(){
		return subItems;
	}
	
	public boolean hasSubs(){
		if (subItems != null && !subItems.isEmpty()) {
			return true;
		}
		return false;
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

	public void addSubItem(ListItem item) {
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
