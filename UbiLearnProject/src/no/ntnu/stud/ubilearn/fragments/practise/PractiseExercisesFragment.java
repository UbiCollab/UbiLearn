package no.ntnu.stud.ubilearn.fragments.practise;

import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PractiseExercisesFragment extends Fragment 
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_exercises, container, false);
		
		return view;
	}
}
