package no.ntnu.stud.ubilearn.fragments.wiki;

import no.ntnu.stud.ubilearn.R;

public class Article extends WikiItem{
	
	String text;

	public Article(String name, String text) {
		this.name = name;
		this.text = text;
	}

	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_launcher;
	}
	
}
