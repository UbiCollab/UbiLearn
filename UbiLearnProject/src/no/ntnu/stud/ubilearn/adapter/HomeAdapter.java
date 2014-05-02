package no.ntnu.stud.ubilearn.adapter;

import java.util.List;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class defines an adapter which is adapted to the individual rows in
 * the Home page. (Each row represent a level in the Training part of the
 * application).
 */
public class HomeAdapter extends ArrayAdapter<String>
{
	/**
	 * Variable contains a reference to an Activity that the fragment creating
	 * this adapter is associated with.
	 */
	private final Context 		_context;
	
	/**
	 * This list contains the names of the different levels. 
	 */
	private final List<String>	_levelNameValues;
	
	/**
	 * This list contains the scores for the different levels.
	 */
	private final List<String>	_levelScoreValues;
	
	
	//#########################################################################
	/**
	 * This constructor initializes data for the adapter.  
	 * 
	 * @param context - The Activity the fragment calling this adapter is 
	 * currently associated with.
	 * @param levelNameValues - A list of names representing the name of the
	 * levels.
	 * @param levelScoreValues - A list of scores representing the scores of
	 * the levels
	 */
	public HomeAdapter(
			Context context,
			List<String> levelNameValues,
			List<String> levelScoreValues)
	{
		super(context, R.layout.home_list_single_level, levelNameValues);
		
		_context			= context;
		_levelNameValues	= levelNameValues;
		_levelScoreValues	= levelScoreValues;
	}

	//-------------------------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater	= (LayoutInflater)_context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(
				R.layout.home_list_single_level, parent, false);
		
		
		TextView nameView =
				(TextView)rowView.findViewById(R.id.levelName);
		nameView.setText(_levelNameValues.get(position));
		
		TextView scoreView =
				(TextView)rowView.findViewById(R.id.homeCasesLevelName);
		scoreView.setText(_levelScoreValues.get(position));
		
		
		return rowView;
	}
}
