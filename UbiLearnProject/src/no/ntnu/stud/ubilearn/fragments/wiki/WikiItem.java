package no.ntnu.stud.ubilearn.fragments.wiki;

public abstract class WikiItem {
	protected String name;
	protected int icon;
	
	public WikiItem() {
		setIcon();
	}
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	
	protected abstract void setIcon();

	public int getIcon() {
		return icon;
	}

	@Override
	public String toString() {
		return name;
	}
}
