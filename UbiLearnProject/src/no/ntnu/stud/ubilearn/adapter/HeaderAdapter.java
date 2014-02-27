package no.ntnu.stud.ubilearn.adapter;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.*;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HeaderAdapter extends ArrayAdapter<Model>{

	
	private final Context context;
	private final ArrayList<Model> modelsArrayList;

	public HeaderAdapter(Context context, ArrayList<Model> modelsArrayList){
		super(context, R.layout.drawer_text_item, modelsArrayList);
		 
        this.context = context;
        this.modelsArrayList = modelsArrayList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		// creates inflater
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//get rowView from inflater
		View rowView = null;
		if(!modelsArrayList.get(position).isGroupHeader()){
			rowView = inflater.inflate(R.layout.drawer_text_item, parent,false);
			//get icon and text
			ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
			TextView txtView = (TextView) rowView.findViewById(R.id.item_text);
			//set the text for textView
            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            txtView.setText(modelsArrayList.get(position).getTitle());
        }
        else{
                rowView = inflater.inflate(R.layout.drawer_header_item, parent, false);
                TextView titleView = (TextView) rowView.findViewById(R.id.header);
                titleView.setText(modelsArrayList.get(position).getTitle());
        }
		return rowView;
	}
}
