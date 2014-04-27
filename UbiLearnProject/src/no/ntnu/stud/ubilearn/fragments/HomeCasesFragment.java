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


public class HomeCasesFragment extends Fragment 
{
	int _levelNo			= -1;
	int _levelScore			= 0;
	int _levelMaxScore		= 0;
	int _nUnlockedHouses 	= 0;
	int _nLockedHouses	 	= 0;
	int _nAchievements		= 0;
	
	// Variables to store input from textfile.
	String _levelName 			= "";
//	String _status 			= "";	//TODO: Delete this when you are
									//certain it will not be used.
	String _totalScore		= "";
	String _caseData		= "";
	
	List<String> _listName	= new ArrayList<String>();
	List<String> _listMedal	= new ArrayList<String>();
	List<String> _listScore	= new ArrayList<String>();
		
	TrainingLevel _level = null;
	
	
	//#########################################################################
	public HomeCasesFragment()
	{
		
	}
	//-------------------------------------------------------------------------
	public static HomeCasesFragment newInstance(/*String levelName, */int levelNo)
	{
		HomeCasesFragment fragment = new HomeCasesFragment();
		
		Bundle bundle = new Bundle(1);
//		bundle.putString("levelName", levelName);
		bundle.putInt("levelNo", levelNo);
		fragment.setArguments(bundle);
		
		
		return fragment;
	}
	//-------------------------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState)
	{
		//_levelName 	= getArguments().getString("levelName");
		_levelNo	= getArguments().getInt("levelNo");
						
		View fragmentView = inflater.inflate(
				R.layout.fragment_home_cases, container, false);
					
		
		TrainingLevel level = User.getInstance().getLevelNo(_levelNo);
		
		_levelName = level.getName();
		
			
		// We go through a list of cases and intialize relevant data
		for(TrainingHouse house : level.getHouseList())
		{
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
			// the specified house/case.
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
		
		
		return fragmentView;
		//return inflater.inflate(R.layout.fragment_home, container, false);
	}
}
