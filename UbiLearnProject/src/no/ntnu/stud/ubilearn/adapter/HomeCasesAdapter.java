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

/**
 * This class is part of the loading of data in the list that is constructed
 * when a user clicks on a specified level from the Home fragment. The new
 * fragment that is created contains the different houses/cases that is part of
 * the level selected and data for each case will be loaded into the list on this
 * page.
 */
public class HomeCasesAdapter extends ArrayAdapter<String>
{
	// This variable will hold the current Activity.
	private final Context 		_context;
	private final List<String>	_caseMedalValues;
	private final List<String>	_caseNameValues;
	private final List<String>	_caseScoreValues;
	
	
	//#########################################################################
	/**
	 * The constructor initializes data for the adapter.
	 * 
	 * @param context - The Activity this fragment is currently associated with.
	 * @param caseMedalValues - A list of strings that represent the medals for
	 * each case.
	 * @param caseNameValues - A list of strings that represent the name for 
	 * each case.
	 * @param caseScoreValues - A list of strings that represent the score for
	 * each case. The format is <scoreAchieved/maxScore>.
	 */
	public HomeCasesAdapter(Context context, 
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
	@Override
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
