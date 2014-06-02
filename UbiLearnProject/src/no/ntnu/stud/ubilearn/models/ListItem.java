package no.ntnu.stud.ubilearn.models;

import android.widget.ListView;

/**
 * An abstract model containing commend properties for models
 * that are represented in a {@link ListView}.
 *
 */
public abstract class ListItem {
//	protected String name;
	protected int icon;
	protected String parentId;
	
	public ListItem() {
		setIcon();
	}
	
	public abstract String getName();
	public abstract void setName(String name);

	
	protected abstract void setIcon();
	
	public boolean isTopLevel() {
		return parentId == null;
	}

	public int getIcon() {
		return icon;
	}
	public abstract String printContent();

//	@Override
//	public String toString() {
//		return name;
//	}
}
