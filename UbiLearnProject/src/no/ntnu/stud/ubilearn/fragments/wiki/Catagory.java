package no.ntnu.stud.ubilearn.fragments.wiki;

public class Catagory extends WikiItem{
	WikiItem[] sub;
	
	public Catagory(String name, WikiItem[] sub){
		this.name = name;
		this.sub = sub;
	}
	
	public WikiItem[] getSub(){
		return sub;
	}
	
	public boolean hasSub(){
		if (sub != null && sub.length > 0) {
			return true;
		}
		return false;
	}
}
