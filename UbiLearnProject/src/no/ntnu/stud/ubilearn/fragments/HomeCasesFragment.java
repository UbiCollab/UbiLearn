package no.ntnu.stud.ubilearn.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
//	String _unlockedCases 	= "";
//	String _lockedCases		= "";
	String _totalScore		= "";
//	String _achievements 	= "";
//	String _unlockedLevels	= "";
//	String _lockedLevels	= "";
	String _caseData		= "";
	
	List<String> _listName	= new ArrayList<String>();
	List<String> _listMedal	= new ArrayList<String>();
	List<String> _listScore	= new ArrayList<String>();
	
	BufferedReader _bufferedReader		= null;
	
	TrainingLevel _level = null;
	
	
	//#########################################################################
	public HomeCasesFragment()
	{
		
	}
	
	//-------------------------------------------------------------------------
/*	public HomeCasesFragment(String levelName, TrainingLevel level)
	{
		_levelName	= levelName;
		_level 		= level;
	}	
*/	//-------------------------------------------------------------------------
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
					
/*		String level = "#LEVEL_" + String.valueOf(_levelNo);
		
		System.out.println("Level name:" + _levelName);
		System.out.println("Level #:" + _levelNo);
		System.out.println("Level: " + level);
		
		
		// Here we read the data from the textfile.
		try
		{
			//TODO: Used only for testing. The way we store scores and so on,
			// has not yet been decided.
			InputStream inputStream = 
					getResources().openRawResource(R.raw.levels_data);
			InputStreamReader inputStreamReader =
					new InputStreamReader(inputStream);
			_bufferedReader =
					new BufferedReader(inputStreamReader);
			
			
			_caseData = _bufferedReader.readLine();
			
			System.out.println("CaseData: " + _caseData);
			
			
			//TODO: Error handling needs to be implemented
			while(!_caseData.equals(level) && _caseData != null)
			{
				_caseData = _bufferedReader.readLine();
			}
			
						
			_unlockedCases 	= _bufferedReader.readLine();
			_lockedCases	= _bufferedReader.readLine();
			_totalScore		= _bufferedReader.readLine();
			_achievements	= _bufferedReader.readLine(); 
						
			// Read a line to see if we have data for a case
			_caseData = _bufferedReader.readLine();
			
			
			// TODO: Unfinished. Add data dynamically...
			// This is for testing purposes.
			while(!_caseData.startsWith("#") && _caseData != null)
			{
				// A line should consist of two numbers. The first to indicate
				// what the user has achieved and the second the maximum score
				// for the specific case.
				String[] splitStr 	= _caseData.split("[#&/]");
								
				//Here we add the data to the lists that we will use to fill
				// the ListView in fragment_home.xml
				_listName.add(splitStr[0]);
				_listMedal.add(splitStr[1]);
				_listScore.add(splitStr[2] + "/" + splitStr[3]);
								
				_caseData			= _bufferedReader.readLine();
			}
		}
		catch(IOException exception)
		{
			// TODO: Replace with a dialog box and handle the error better.
			System.out.println("Problems reading text file!");
		}
*/		/**
		 * We must ensure that the file is closed after used.
		 */
/*		finally
		{
			try
			{
				if(_bufferedReader != null)
					{
					_bufferedReader.close();
					}
			}
			catch(IOException ioException)
			{
				System.out.println("Problems closing the file");
			}
		}
*/		
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
	
		// Now we want to fill the list in 'fragment_home.xml' with data loaded
		// from the textfile.
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
