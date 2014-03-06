package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;

public class Catagory extends WikiItem{
	private ArrayList<WikiItem> sub;
	
	public Catagory(String name, ArrayList<WikiItem> sub){
		this.name = name;
		this.sub = sub;
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
	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
	}
	
}
