package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PractisePatientsFragment extends Fragment 
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_practise_patient, container, false);
	}
}
