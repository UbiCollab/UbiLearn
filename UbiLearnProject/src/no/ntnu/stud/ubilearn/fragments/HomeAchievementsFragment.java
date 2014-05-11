package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HomeAchievementsAdapter;
import no.ntnu.stud.ubilearn.models.TrainingLevel;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;


/**
 * This class handles the fragment containing the achievements.
 */
public class HomeAchievementsFragment extends Fragment
{
	/*
	 * This list hold the status of each level in relation to whether it is
	 * achieved or not. 
	 * TRUE = achievement achieved, 
	 * FALSE = achievement not achieved.
	 */
	List<Boolean> _achievementStatus	= new ArrayList<Boolean>();
	
	/*
	 * This list contains text that represents the title for each achievement.
	 */
	List<String> _achievementTitles		= new ArrayList<String>();
	
	/*
	 * This list contains a text that represent the description for each 
	 * achievement.
	 */
	List<String> _achievementTexts		= new ArrayList<String>();
		
	
	//#########################################################################
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// We reset some of the data.
		resetData();
		
		View fragmentView = inflater.inflate(
				R.layout.fragment_home_achievements, container, false);
		
		
		// We retrieve the list of levels through the user
		for(TrainingLevel level : User.getInstance().getLevels(this.getActivity()))
		{
			// If the user has a score that means he deserves an achievement
			if(level.getUserScore() == level.getMaxScore())
			{
				// Indicate an achievement for this level
				_achievementStatus.add(Boolean.valueOf(true));
			}
			else
			{
				_achievementStatus.add(Boolean.valueOf(false));
			}	
			
			
			_achievementTitles.add(level.getName() + " ekspert");
			
			_achievementTexts.add("Kjempebra jobb! Du har oppnådd " +
					level.getMaxScore() + " av " +
					level.getMaxScore() + " mulige poeng.");
		}
		
		
		// Now we want to fill the list in fragments_home_achievements.xml
		ListView achievementsListView = 
				(ListView)fragmentView.findViewById(R.id.homeAchievementsList);
		
		achievementsListView.setAdapter(new HomeAchievementsAdapter(
				this.getActivity(),
				_achievementStatus, _achievementTitles, _achievementTexts));
		
		achievementsListView.setClickable(false);
		
		/*
		 * We don't really need this button considering that all Android phones
		 * has a "Back" button on the phone itself.
		 * 
		// We need the ability to return to the previous "Home" page
		Button returnButton =
				(Button)fragmentView.findViewById(
						R.id.homeAchievementsButtonBack);
				
		returnButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				((MainActivity)getActivity()).selectItem(1);
				//getFragmentManager().popBackStack();
			}
		});
		*/
		
		return fragmentView;	
	}
	
	//-------------------------------------------------------------------------
	/*
	 * This code is used to make sure that some of the data this fragment
	 * contains, is reset for each call to it. This to prevent that data is
	 * is added to already existing data when the user clicks the "Back" button
	 * on the phone.
	 */
	public void resetData()
	{
		_achievementStatus.clear();
		_achievementTitles.clear();
		_achievementTexts.clear();
	}
}
