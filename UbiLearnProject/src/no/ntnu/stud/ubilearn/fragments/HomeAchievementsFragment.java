package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeAchievementsFragment extends Fragment
{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View fragmentView = inflater.inflate(
				R.layout.fragment_home_achievements, container, false);
				
		
		// We need the ability to return to the previous "Home" page
		Button returnButton =
				(Button)fragmentView.findViewById(R.id.homeAchievementsButtonBack);
				
		returnButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				((MainActivity)getActivity()).selectItem(1);
				//getFragmentManager().popBackStack();
			}
		});
		
		
		return fragmentView;	
	}
}
