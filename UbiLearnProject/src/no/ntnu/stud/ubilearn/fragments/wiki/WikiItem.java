package no.ntnu.stud.ubilearn.fragments.wiki;

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

//	@Override
//	public String toString() {
//		return name;
//	}
}
