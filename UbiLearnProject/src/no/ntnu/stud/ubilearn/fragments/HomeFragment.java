package no.ntnu.stud.ubilearn.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.adapter.HomeAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class HomeFragment extends Fragment 
{
	private int _userScore		= 0;
	private int _userTotalScore	= 0;
	
	// Variables to store input from textfile.
	private String _name 			= "";
	private String _status 			= "";	//TODO: Delete this when you are
									//certain it will not be used.
	private String _achievements 	= "";
	private String _unlockedLevels	= "";
	private String _lockedLevels	= "";
	private String _caseData		= "";
	
	private List<String> _listName	= new ArrayList<String>();
	private List<String> _listScore	= new ArrayList<String>();
	
	private BufferedReader _bufferedReader	= null;
	
	private ListView _levelListView	= null;
		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState)
	{
		View fragmentView = inflater.inflate(
				R.layout.fragment_home, container, false);
					
		
		// Here we read the data from the textfile.
		try
		{
			//TODO: Used only for testing. The way we store scores and so on,
			// has not yet been decided.
			// TEST: Here we try to read from the userdata_expanded.txt file.
			InputStream inputStream = 
					getResources().openRawResource(R.raw.userdata_expanded);
			InputStreamReader inputStreamReader =
					new InputStreamReader(inputStream);
			_bufferedReader =
					new BufferedReader(inputStreamReader);
				
			
			_name 			= _bufferedReader.readLine();
			_status			= _bufferedReader.readLine();
			_achievements	= _bufferedReader.readLine(); 
			_unlockedLevels = _bufferedReader.readLine();
			_lockedLevels	= _bufferedReader.readLine();
								
			
			// Here we check to see if we have stored data for the different
			// levels. If so we add these to the list in fragment_home.xm
			// Read a line to see if we have data for a case
			_caseData = _bufferedReader.readLine();
			
			
			// TODO: Unfinished. Add data dynamically...
			// This is for testing purposes.
			int counter	= 0;
			
			
			while(_caseData != null)
			{
				// We add the counter for every level 
				counter++;
				
				String[] splitStr 	= _caseData.split("[#/]");
				
				//Here we add the data to the lists that we will use to fill
				// the ListView in fragment_home.xml
				_listName.add(splitStr[0]);
				_listScore.add(splitStr[1] + "/" + splitStr[2]);
				
				
				// We sum the scores the user has achieved. This will help us
				// decide which 'Status' the user has
				_userScore 		+= Integer.parseInt(splitStr[1]);
				_userTotalScore	+= Integer.parseInt(splitStr[2]);		
								
				_caseData			= _bufferedReader.readLine();
			}
		}
		catch(IOException exception)
		{
			// TODO: Replace with a dialog box and handle the error better.
			System.out.println("Problems reading text file!");
		}
		/**
		 * We must ensure that the file is closed after used.
		 */
		finally
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
		
			
		// We set the different TextViews in fragment_home.xml based on the
		// values read from file.
		TextView userName = 
				(TextView)fragmentView.findViewById(R.id.homeUserName); 
		userName.setText(_name);
		
		// The status is based on the amount of score achieved of the total
		// possible. NB! This could have been calculated when data from a 
		// case is saved.
		TextView userStatus = 
				(TextView)fragmentView.findViewById(R.id.homeStatus); 

		
		if(_userTotalScore > 0)
		{
			int percentageAccomplished = (_userScore * 100) / _userTotalScore;
			
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
			userStatus.setText(R.string.user_status_expert);
		}
				
		
		TextView userAchievement = 
				(TextView)fragmentView.findViewById(R.id.homeAchievement); 
		userAchievement.setText(_achievements);
		
		TextView userUnlockedLevels =
				(TextView)fragmentView.findViewById(R.id.homeUnlockedLevels);
		userUnlockedLevels.setText(_unlockedLevels);
		
		TextView userLockedLevels =
				(TextView)fragmentView.findViewById(R.id.homeLockedLevels);
		userLockedLevels.setText(_lockedLevels);
		

		// Now we want to fill the list in 'fragment_home.xml' with data loaded
		// from the textfile.
		_levelListView = 
				(ListView)fragmentView.findViewById(R.id.homeListLevel);
		
		_levelListView.setAdapter(new HomeAdapter(
				this.getActivity(),	_listName, _listScore));
		
		
		_levelListView.setClickable(true);
		_levelListView.setOnItemClickListener(
				new AdapterView.OnItemClickListener()
		{
			public void onItemClick(
					AdapterView<?> parent, View view,
					int position, long id)
			{
				String levelName = (String)_listName.get(position);
				
				HomeCasesFragment fragment = HomeCasesFragment.newInstance(
						levelName, position + 1);
				getFragmentManager().beginTransaction().replace(
						R.id.content_frame, fragment).addToBackStack(
								null).commit();
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
}
