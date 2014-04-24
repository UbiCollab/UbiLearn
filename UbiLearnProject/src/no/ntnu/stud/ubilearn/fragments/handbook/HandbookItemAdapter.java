package no.ntnu.stud.ubilearn.fragments.handbook;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.WikiItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HandbookItemAdapter extends ArrayAdapter<WikiItem>{
	Context context; 
    int resource;    
    ArrayList<WikiItem> list;

	public HandbookItemAdapter(Context context, ArrayList<WikiItem> list) {
		super(context, R.layout.wiki_list_item, list);
		
		this.context = context;
		this.resource = R.layout.wiki_list_item;
		this.list = list;
		
	} 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View rowView = inflater.inflate(R.layout.wiki_list_item, parent, false);
		
		TextView textView = (TextView) rowView.findViewById(R.id.item_text);
		ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
		textView.setText(list.get(position).getName());
		imgView.setImageResource(list.get(position).getIcon());
		
//		rowView.setOnClickListener(new OnClickListener() {	
//			@Override
//			public void onClick(View v) {
//				v.setBackgroundColor(Color.BLACK);
//			}
//		});

		return rowView;
	}
}
