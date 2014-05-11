package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HomeAdapter;
import no.ntnu.stud.ubilearn.models.TrainingLevel;
import no.ntnu.stud.ubilearn.parse.SyncContent;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * This class handles the fragment for the Home page.
 */
public class HomeFragment extends Fragment {
	
	private Fragment pointerHax = this;
	/*
	 * The score the user has achieved from each level will be added to this
	 * variable and then it will be used to calculate the status of the user.
	 */
	private int _userScore			= 0;
	
	/*
	 * The maximum score achievable for each level will be added to this 
	 * variable and later this variable will be used to calculate the status
	 * for the user.
	 */
	private int _maxScore			= 0;
	
	/*
	 * This variable will the number of achievements the user has achieved.
	 */
	private int _nAchievements 		= 0;
	
	/*
	 * This variable will hold the number of unlocked levels for the user.
	 */
	private int _nUnlockedLevels	= 0;
	
	/*
	 * This variable will hold the number of locked levels for the user.
	 */
	private int _nLockedLevels		= 0;
	
	/*
	 * This variable will hold the name for the user.
	 */
	private String _userName 		= "";
	
	/*
	 * This list contains the name for the different levels.
	 */
	private List<String> _listName	= new ArrayList<String>();
	
	/*
	 * This list contains the scores for the different levels. The format of 
	 * the scores are XX/YY where XX = score achieved and YY = maximum possible
	 * score.
	 */
	private List<String> _listScore	= new ArrayList<String>();
	
	/*
	 * This list contains the status of the lock for the different levels.
	 * TRUE = level is locked
	 * FALSE = level is not locked
	 */
	private List<Boolean> _listLockStatus = new ArrayList<Boolean>();
	
	/*
	 * This variable hold an instance to the user.
	 */
	private User _user = null;
	
	/*
	 * This variable hold a list of different levels of TrainingLevel. Each
	 * TrainingLevel hold a list of instances of type TrainingHouse.
	 */
	private List<TrainingLevel> _levelList;
	
	
	//#########################################################################
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState)
	{
		// We need to reset some data in case the call of this function is the
		// result of clicking the "Back" button on the phone. This is to
		// prevent that data is added to previously added data.
		resetData();
		_user = User.getInstance();
		
		final View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
		
		_userName = _user.getName();
		_levelList = _user.getLevels(pointerHax.getActivity());

		/*
		 * We go through the list of levels and retrieve data that will be used
		 * to fill the level list in the "Home"-page as well as other
		 * variables.
		 */
		for(TrainingLevel level : _levelList)
		{
			_listName.add(level.getName());
			_listScore.add(level.getUserScore() + "/" + level.getMaxScore());
			
			_userScore += level.getUserScore();
			_maxScore  += level.getMaxScore();
			
			
			/*
			 * Based on whether the level is locked or not, we update the
			 * relevant variable.
			 */
			if(level.isLocked())
			{
				_listLockStatus.add(Boolean.valueOf(true));
				
				_nLockedLevels++; 
			}
			else
			{
				_listLockStatus.add(Boolean.valueOf(false));
				
				_nUnlockedLevels++;
			}
			
			
			/*
			 * The user will unlock an achievement if the user has achieved
			 * maximum score possible for that specific level.
			 */
			if(level.getUserScore() == level.getMaxScore())
			{
				_nAchievements++;
			}
		}		
		
		
		// The status is based on the amount of score achieved of the total
		// possible
		TextView userStatus = 
				(TextView)fragmentView.findViewById(R.id.homeStatus);
		
		
		if(_maxScore > 0)
		{
			int percentageAccomplished = (_userScore * 100) / _maxScore;
			
			
			if(percentageAccomplished < 51)
			{
				userStatus.setText(R.string.user_status_beginner);
			}
			else if(percentageAccomplished < 86)
			{
				userStatus.setText(R.string.user_status_experienced); 
			}
			else
			{
				userStatus.setText(R.string.user_status_expert);
			}
		}
		else
		{
			userStatus.setText(R.string.user_status_beginner);
		}		
			
		
			
		// We set the different TextViews in fragment_home.xml based on the
		// values read from file.
		TextView userName = 
				(TextView)fragmentView.findViewById(R.id.homeUserName); 
		userName.setText(_userName);				
		
		TextView userAchievement = 
				(TextView)fragmentView.findViewById(R.id.homeAchievement); 
		userAchievement.setText(Integer.toString(_nAchievements));
		
		/*
		 * We no longer need this because of the update to the TextView
		 * userUnlockedLevels below.
		TextView userUnlockedLevels =
				(TextView)fragmentView.findViewById(R.id.homeUnlockedLevels);
		userUnlockedLevels.setText(Integer.toString(_nUnlockedLevels));
		*/
		
		/*
		 * When we here set the TextView we set the text to also involve the
		 * total number of levels. So the output format will now be for the
		 * number of locked levels: 
		 * number of locked levels / total number of levels.
		 */
		TextView userLockedLevels =
				(TextView)fragmentView.findViewById(R.id.homeLockedLevels);
		userLockedLevels.setText(Integer.toString(_nLockedLevels) + "/" +
				Integer.toString(_nLockedLevels + _nUnlockedLevels));		

		// Now we want to fill the list in 'fragment_home.xml' with data 
		ListView _levelListView = 
				(ListView)fragmentView.findViewById(R.id.homeListLevel);
		
		_levelListView.setAdapter(new HomeAdapter(
				this.getActivity(),	_listName, _listScore, _listLockStatus));
		
		
		_levelListView.setClickable(true);
		_levelListView.setOnItemClickListener(
				new AdapterView.OnItemClickListener()
		{
			public void onItemClick(
					AdapterView<?> parent, View view,
					int position, long id)
			{
//				String levelName = (String)_listName.get(position);
				
				HomeCasesFragment fragment = 
						HomeCasesFragment.newInstance(position);//position + 1);
				getFragmentManager().beginTransaction().replace(
						R.id.content_frame, fragment).addToBackStack(
								null).commit();
								
			/*	getFragmentManager().beginTransaction().replace(
						R.id.content_frame, fragment, "level").addToBackStack(
								null).commit();
			*/	
			}
		});
		
		
		// We need to handle button clicks from the fragment_home.xml file. If
		// the user clicks 'Opplæring' or 'Praksis' we should replace this
		// fragment with the chosen one.
		Button trainingButton = 
				(Button)fragmentView.findViewById(R.id.homeButtonTraining);
		
				
		trainingButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// Here call the getActivity method from class MainActivity to
				// handle the change from this fragment to the Training 
				// fragment 
				((MainActivity)getActivity()).selectItem(3);
			}
		} );
		
		
		Button practiseButton = 
				(Button)fragmentView.findViewById(R.id.homeButtonPractise);
		
		
		practiseButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// Here call the getActivity method from class MainActivity to
				// handle the change from this fragment to the Practice 
				// fragment 
				((MainActivity)getActivity()).selectItem(4);
			}
		} );
		
		
		// Load the "Achievements" page if the user push this specific button.
		Button achievementsButton =
				(Button)fragmentView.findViewById(R.id.homeButtonAchievements);
		
		achievementsButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				HomeAchievementsFragment fragment = new HomeAchievementsFragment();
				
				getFragmentManager().beginTransaction().replace(
						R.id.content_frame, fragment).addToBackStack(
								null).commit();
			}
		});
		return fragmentView;
		//return inflater.inflate(R.layout.fragment_home, container, false);
	}
	
	//-------------------------------------------------------------------------
	/*
	 * This function resets variables that is part of the class. This is to
	 * make sure that when the user enters other pages and then returns to this
	 * page, data will not add to already existing data.
	 */
	
	private void doStuff(View fragmentView){
		
	}
	private void resetData()
	{
		_userScore			= 0;
		_maxScore			= 0;
		_nAchievements		= 0;
		_nUnlockedLevels	= 0;
		_nLockedLevels		= 0;
		
		_listName.clear();
		_listScore.clear();
	}
}
