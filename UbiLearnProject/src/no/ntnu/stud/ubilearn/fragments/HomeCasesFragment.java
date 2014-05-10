package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HomeCasesAdapter;
import no.ntnu.stud.ubilearn.models.TrainingHouse;
import no.ntnu.stud.ubilearn.models.TrainingLevel;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * This class handles the fragment for each of the levels. The page contains
 * information about the level selected from the list in the Home page. 
 */
public class HomeCasesFragment extends Fragment 
{
	/*
	 * Indicate which element has been selected in the list of levels in the
	 * Home page.
	 */
	int _levelNo			= -1;
	
	/*
	 * This variable hold the the total score achieved for this level.
	 */
	int _levelScore			= 0;
	
	/*
	 * This variable hold the total score possible for this level.
	 */
	int _levelMaxScore		= 0;
	
	/*
	 * This variable hold the number of unlocked houses for this level.
	 */
	int _nUnlockedHouses 	= 0;
	
	/*
	 * This variable hold the number of locked houses for this level.
	 */
	int _nLockedHouses	 	= 0;
	
	/*
	 * This variable hold the number of locked houses for this level.
	 */
	int _nAchievements		= 0;
	
	/*
	 * Variable used in relation to the output of the name of the level.
	 */
	String _levelName 		= "";
	
	/*
	 * This list contains the names of the different houses.
	 */
	List<String> _listName	= new ArrayList<String>();
	
	/*
	 * This list contains medals the user has achieved for the different
	 * houses. 
	 */
	List<String> _listMedal	= new ArrayList<String>();
	
	/*
	 * This list contains the scores for the different houses.
	 */
	List<String> _listScore	= new ArrayList<String>();
	
	/*
	 * This variable holds an instance of a TrainingLevel that was selected in
	 * the Home page.
	 */
	TrainingLevel _level = null;
	
	
	//#########################################################################
	public HomeCasesFragment()
	{
		
	}
	//-------------------------------------------------------------------------
	public static HomeCasesFragment newInstance(int levelNo)
	{
		HomeCasesFragment fragment = new HomeCasesFragment();
		
		Bundle bundle = new Bundle(1);
		bundle.putInt("levelNo", levelNo);
		fragment.setArguments(bundle);
		
		
		return fragment;
	}
	//-------------------------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState)
	{
		// We reset some data.
		resetData();			
		
		_levelNo	= getArguments().getInt("levelNo");
						
		View fragmentView = inflater.inflate(
				R.layout.fragment_home_cases, container, false);
					
		TrainingLevel level = User.getInstance().getLevelNo(_levelNo);
		
		_levelName = level.getName();
		
			
		// We go through a list of cases and initializes relevant data.
		for(TrainingHouse house : level.getHouseList())
		{
			// Here we check whether each house is locked or not, and update
			// the relevant variable accordingly.
			if(house.isLocked() == true)
			{
				_nLockedHouses += 1;
			}
			else
			{
				_nUnlockedHouses += 1;
			}
			
			
			int houseUserScore = house.getUserScore();
			int houseMaxScore  = house.getMaxScore();
			
			_listName.add(house.getName());
			_listScore.add(houseUserScore + "/" + houseMaxScore);
				
			
			// We need to calculate the type of medal the user has retrieved in
			// the specified house/case. We calculate this a bit different
			// if the maximum score for a house is greater than 5 than if the 
			// score is 5 or less.
			if(houseMaxScore > 5)
			{
				int score = (houseUserScore * 100) / houseMaxScore;
				
				
				if(score >= 89)
				{
					_listMedal.add("Gold");
				}
				else if(score >= 77)
				{
					_listMedal.add("Silver");
				}
				else if(score >= 65)
				{
					_listMedal.add("Bronze");
				}
				else
				{
					_listMedal.add("None");
				}
			}
			else
			{
				if(houseUserScore == houseMaxScore)
				{
					_listMedal.add("Gold");
				}
				else if(houseUserScore == (houseMaxScore - 1))
				{
					_listMedal.add("Silver");
				}
				else if(houseUserScore == (houseMaxScore - 2))
				{
					_listMedal.add("Bronze");
				}
				else
				{
					_listMedal.add("None");
				}
			}
		}
		
		
		_levelScore 	= level.getUserScore();
		_levelMaxScore	= level.getMaxScore();
		
		
		// We add the number of achievements with 1 if the condition for this
		// is true. An achievement is achieved if the user has managed to get
		// the maximum score possible for a level.
		if(_levelScore == _levelMaxScore)
		{
			_nAchievements += 1;
		}
		
			
		// We set the different TextViews in fragment_home.xml based on the
		// values read from file.
		TextView levelName = 
				(TextView)fragmentView.findViewById(R.id.homeCasesLevelName); 
		levelName.setText(_levelName);
		
		TextView unlockedCases =
				(TextView)fragmentView.findViewById(R.id.homeCasesUnlockedCases); 
		unlockedCases.setText(Integer.toString(_nUnlockedHouses));
		
		TextView lockedCases =
				(TextView)fragmentView.findViewById(R.id.homeCasesLockedCases);
		lockedCases.setText(Integer.toString(_nLockedHouses));
		
		TextView totalScore =
				(TextView)fragmentView.findViewById(R.id.homeCasesTotalScore);
		totalScore.setText(Integer.toString(_levelScore) + "/" +
				Integer.toString(_levelMaxScore));
		
		TextView achievements =
				(TextView)fragmentView.findViewById(R.id.homeCasesAchievements);
		achievements.setText(Integer.toString(_nAchievements));
	
		// Now we want to fill the list in 'fragment_home_cases.xml' with data
		ListView caseListView = 
				(ListView)fragmentView.findViewById(R.id.homeCasesListCases);
		
		caseListView.setAdapter(new HomeCasesAdapter(
				this.getActivity(),
				_listMedal, _listName, _listScore));
		
		caseListView.setClickable(false);
		
		/*
		 * We do not really need the 'Back' button considering that Android
		 * phones has a 'Back' button.
		 * 
		// We need the ability to return to the previous "Home" page
		Button returnButton =
				(Button)fragmentView.findViewById(R.id.homeCasesButtonBack);
		
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
		//return inflater.inflate(R.layout.fragment_home, container, false);
	}
	
	//-------------------------------------------------------------------------
	/*
	 * This function resets variables that is part of the class. This is to
	 * make sure that when the user enters other pages and then returns to this
	 * page, data will not add to already existing data.
	 */
	public void resetData()
	{
		_levelNo			= -1;
		_levelScore			= 0;
		_levelMaxScore		= 0;
		_nUnlockedHouses 	= 0;
		_nLockedHouses	 	= 0;
		_nAchievements		= 0;
		
		_levelName 		= "";
		
		_listName.clear();
		_listMedal.clear();
		_listScore.clear();
	}
}
