package no.ntnu.stud.ubilearn.adapter;

import java.util.List;

import com.parse.R.color;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class defines an adapter which is adapted to the individual rows in
 * the Achievements page.
 */
public class HomeAchievementsAdapter extends ArrayAdapter<String>
{
	/**
	 * Variable contains a reference to an Activity that the fragment creating
	 * this adapter is associated with.
	 */
	private final Context		_context;
	
	/**
	 * List contains instances of Boolean that each indicate whether that
	 * particular element (level) has achieved an achievement or not.
	 */
	private final List<Boolean>	_achievementStatus;
	
	/**
	 * List contains text that will represent the titles for each element in
	 * the list of achievements.
	 */
	private final List<String>	_achievementTitles;
	
	/**
	 * List contains text that will represent the text for each element in the
	 * list of achievement.
	 */
	private final List<String> 	_achievementTexts;
	
	
	//#########################################################################
	/**
	 * The constructor initializes data for the adapter.
	 * 
	 * @param context - The Activity the fragment calling this adapter is 
	 * currently associated with.
	 * @param achievementStatus - A list that represent the achievement status
	 * for each level.
	 * @param achievementTitles - A list that represent the titles to be used
	 * for each level.
	 * @param achievementTexts - A list that represent the texts to be used for
	 * each level.
	 */
	public HomeAchievementsAdapter(Context context,
			List<Boolean> achievementStatus,
			List<String> achievementTitles,
			List<String> achievementTexts)
	{
		super(context, R.layout.home_list_single_achievement, 
				achievementTitles);
		
		_context			= context;
		_achievementStatus	= achievementStatus;
		_achievementTitles	= achievementTitles;
		_achievementTexts	= achievementTexts;
	}
	
	//-------------------------------------------------------------------------
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater	= (LayoutInflater)_context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(
				R.layout.home_list_single_achievement, parent, false);
		
		ImageView iconView	=
				(ImageView)rowView.findViewById(R.id.achievementImage);
		TextView titleView	=
				(TextView)rowView.findViewById(R.id.achievementTitle);
		TextView textView	=
				(TextView)rowView.findViewById(R.id.achievementText);
		
		// We get a handle to a LinearLayout so that we can set the background
		// color.
		LinearLayout layout =
				(LinearLayout)rowView.findViewById(R.id.achievementLinearLayout);
		
		
		// Handle the situation where the user has achieved an achievement.
		if(_achievementStatus.get(position) == true)
		{
			// The user has achieved an achievement and therefore load the
			// specific icon to indicate this. In this case an image of a
			// heart.
			iconView.setImageResource(R.drawable.achievement_true);
			
			// We set the background color to a sky blue color.
			layout.setBackgroundColor(0xFF87CEFA);
			//layout.setBackgroundColor(0xFFADFF2F);
		}
		// Handle the situation where the user has not achieved an achievement.
		else
		{
			// The user has not achieved an achievement and therefore we load
			// the icon that represent the "greyed out" image of a heart.
			iconView.setImageResource(R.drawable.achievement_false_97);
			
			// We set the title and text color to a greyish color to make it less
			// visible.
			titleView.setTextColor(0xFFb1a3a3);
			textView.setTextColor(0xFFb1a3a3);
			
			// The background color is also set to a grey color but a little
			// different than the text color, so to make the text visible.
			layout.setBackgroundColor(Color.LTGRAY);	
		}
		
		
		titleView.setText(_achievementTitles.get(position));
		textView.setText(_achievementTexts.get(position));
		
		
		return rowView;
	}
}
