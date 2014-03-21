package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class PractiseFragment extends Fragment
{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view =  inflater.inflate(R.layout.fragment_practise, container, false);
		
		Button enterExercises = (Button) view.findViewById(R.id.exercises_button);
		
		enterExercises.setOnClickListener(new OnClickListener()
		{
			public void onClick(View vi)
			{
				Fragment fragment = new PractiseExercisesFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
			}
		});
		
		ListView listView = (ListView) view.findViewById(R.id.patientsList);
		 
	    String[] patients = getResources().getStringArray(R.array.patients_list);
		
		return view;
	}	
}
