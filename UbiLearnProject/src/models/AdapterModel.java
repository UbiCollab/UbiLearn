package models;

public class AdapterModel {

	private int icon;
	private String title;
	
	private boolean isGroupHeader = false;
	
	public AdapterModel(String title){
		this(-1,title);
		isGroupHeader = true;
	}

	public AdapterModel(int icon, String title) {
		this.icon = icon;
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isGroupHeader() {
		return isGroupHeader;
	}

	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}
	
}
