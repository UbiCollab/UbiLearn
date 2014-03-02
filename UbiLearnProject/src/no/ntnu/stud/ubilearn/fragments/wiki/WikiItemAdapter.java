package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WikiItemAdapter extends ArrayAdapter<WikiItem>{
	Context context; 
    int resource;    
    ArrayList<WikiItem> list;

	public WikiItemAdapter(Context context, ArrayList<WikiItem> list) {
		super(context, R.layout.wiki_list_item, list);
		
		this.context = context;
		this.resource = R.layout.wiki_list_item;
		this.list = list;
		
	} 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		rowView = null;
		rowView = inflater.inflate(R.layout.wiki_list_item, parent, false);
		TextView txtView = (TextView) rowView.findViewById(R.id.item_text);
		ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
		txtView.setText(list.get(position).getName());
		imgView.setImageResource(list.get(position).getIcon());
		

		return rowView;
	}
}
