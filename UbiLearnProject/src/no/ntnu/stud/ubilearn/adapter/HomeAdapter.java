package no.ntnu.stud.ubilearn.adapter;

import java.util.List;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
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
	
	/*
	 * This list contains the lock status for the different levels.
	 * TRUE = level is locked
	 * FALSE = level is not locked 
	 */
	private final List<Boolean> _levelLockStatus;
	
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
			List<String> levelScoreValues,
			List<Boolean>levelLockStatus)
	{
		super(context, R.layout.home_list_single_level, levelNameValues);
		
		_context			= context;
		_levelNameValues	= levelNameValues;
		_levelScoreValues	= levelScoreValues;
		_levelLockStatus	= levelLockStatus;
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
				(TextView)rowView.findViewById(R.id.singleLevelName);
		nameView.setText(_levelNameValues.get(position));
		
		TextView scoreView =
				(TextView)rowView.findViewById(R.id.singleLevelScore);
		scoreView.setText(_levelScoreValues.get(position));
		
		TableRow tableRow =
				(TableRow)rowView.findViewById(R.id.singleLevelTableRow);
		
		/* We choose not to use this now.
		// If the level is locked we indicate this by a certain color on text
		// and background.
		if(_levelLockStatus.get(position) == true)
		{
			// We set the background color to indicate that the level is 
			// locked.
			// Here we set a grey background color
			tableRow.setBackgroundColor(Color.LTGRAY);
			
			// Here we set a red background color
			//tableRow.setBackgroundColor(0xFFFF0080);
			
			// We set the text color to a greyish color.
			nameView.setTextColor(0xFFb1a3a3);
			scoreView.setTextColor(0xFFb1a3a3);
		}
		// If the level is not locked we indicate this by a different color
		// on background.
		else
		{
			// Here we set the background to a greenish color.
			tableRow.setBackgroundColor(0xFF00FF7F);
		}
		*/
		
		return rowView;
	}
}
