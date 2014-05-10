package no.ntnu.stud.ubilearn.models;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;

public class ExerciseCategory  extends ListItem{

	private String name;
	private ArrayList<ListItem> subItems;
	private String objectId;
	
	
	
	public ExerciseCategory(String name, String objectId, String parentId) {
		super();
		this.name = name;
		this.subItems = new ArrayList<ListItem>();
		this.objectId = objectId;
		this.parentId = parentId;
	}

	public String getParentId(){
		return parentId;
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
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}

	
	public String getObjectId() {
		return objectId;
	}

	public ArrayList<ListItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(ArrayList<ListItem> subItems) {
		this.subItems = subItems;
	}

	@Override
	public String printContent() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasSubs() {
		if (!subItems.isEmpty()) {
			return true;
		}
		return false;
	}

}
