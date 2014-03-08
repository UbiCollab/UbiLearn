package no.ntnu.stud.ubilearn.adapter;

import java.util.List;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeAdapter extends ArrayAdapter<String>
{
	private final Context 		_context;
	private final List<String>	_caseMedalValues;
	private final List<String>	_caseNameValues;
	private final List<String>	_caseScoreValues;
	
	
	//-------------------------------------------------------------------------
	public HomeAdapter(Context context, 
			List<String> caseMedalValues,
			List<String> caseNameValues,
			List<String> caseScoreValues)
	{
		super(context, R.layout.home_list_single_case, caseNameValues);
		
		_context			= context;
		_caseMedalValues	= caseMedalValues;
		_caseNameValues		= caseNameValues;
		_caseScoreValues	= caseScoreValues;
	}
	//-------------------------------------------------------------------------
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater	= (LayoutInflater)_context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(
				R.layout.home_list_single_case, parent, false);
		
		ImageView medalView	=
				(ImageView)rowView.findViewById(R.id.caseMedalImage);
		TextView nameView	=
				(TextView)rowView.findViewById(R.id.caseName);
		TextView scoreView	=
				(TextView)rowView.findViewById(R.id.caseScore);
		
		
		// Based on the type of medal we will load the specific medal image.
		// If "None" is specified we will not load a image. 
		// TODO: Later we could load an image indicating no medal.
		if(_caseMedalValues.get(position).equals("Bronze"))
		{
			medalView.setImageResource(R.drawable.medal_bronze_icon);
		}
		else if(_caseMedalValues.get(position).equals("Silver"))
		{
			medalView.setImageResource(R.drawable.medal_silver_icon);
		}
		else if(_caseMedalValues.get(position).equals("Gold"))
		{
			medalView.setImageResource(R.drawable.medal_gold_icon);
		}
		
		
		nameView.setText(_caseNameValues.get(position));
		scoreView.setText(_caseScoreValues.get(position));
		
		
		return rowView;
	}
}
