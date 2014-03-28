package no.ntnu.stud.ubilearn.models;

public abstract class WikiItem {
//	protected String name;
	protected int icon;
	
	public WikiItem() {
		setIcon();
	}
	

	public abstract String getName();



	public abstract void setName(String name);

	
	protected abstract void setIcon();

	public int getIcon() {
		return icon;
	}
	public abstract String printContent();

//	@Override
//	public String toString() {
//		return name;
//	}
}
