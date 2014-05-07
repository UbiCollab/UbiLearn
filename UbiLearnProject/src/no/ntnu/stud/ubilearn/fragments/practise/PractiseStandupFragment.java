package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class PractiseStandupFragment extends Fragment
{
	private Patient patient;
	private StandUpSPPB result;
	private double time = 0;
	
	public PractiseStandupFragment(Patient patient)
	{
		this.patient = patient;
		result = new StandUpSPPB("StandUp", this.patient.getId() , time, new Date());	
	}
	
	public OnClickListener start, rep;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view =  inflater.inflate(R.layout.fragment_practise_standup, container, false);
		
		
		
		return view;
	}

}
