package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.adapter.HomeAdapter;
import no.ntnu.stud.ubilearn.models.TrainingLevel;
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


/**
 * This class handles the fragment for the Home page.
 */
public class HomeFragment extends Fragment 
{
	private int _userScore			= 0;
	private int _maxScore			= 0;
	private int _nAchievements 		= 0;
	private int _nUnlockedLevels	= 0;
	private int _nLockedLevels		= 0;
	
	private String _userName 		= "";
	
	private List<String> _listName	= new ArrayList<String>();
	private List<String> _listScore	= new ArrayList<String>();	
	
	private ListView _levelListView	= null;
	
	private User _user = null;
	
	//TODO: Only for testing. Delete afterwards.
	private List<TrainingLevel> _levelList;
	
	
	//#########################################################################
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState)
	{
		View fragmentView = inflater.inflate(
				R.layout.fragment_home, container, false);
		
		// We retrieve the data that is relevant for the user
		_user = User.getInstance();
		
		
		if(_user == null)
		{
			//TODO: Output that an error occured and return to the login 
			// screen
		}
		
		_userName = _user.getName();						 
		_levelList = _user.getLevels();
		
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
			
			
			if(level.isLocked())
			{
				_nLockedLevels++; 
			}
			else
			{
				_nUnlockedLevels++;
			}
			
						
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
//				String levelName = (String)_listName.get(position);
				
				HomeCasesFragment fragment = 
						HomeCasesFragment.newInstance(position);//position + 1);
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
