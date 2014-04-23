package no.ntnu.stud.ubilearn.adapter;

import java.util.List;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class HomeAdapter extends ArrayAdapter<String>
{
	private final Context 		_context;
	private final List<String>	_levelNameValues;
	private final List<String>	_levelScoreValues;
	
	//#########################################################################
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
