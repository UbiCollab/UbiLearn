package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.Patient;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class PractisePatientsFragment extends Fragment 
{
	
	
	
	Patient patient;
	public PractisePatientsFragment(Patient patient){
		this.patient = patient;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View rootView = inflater.inflate(R.layout.fragment_practise_patient, container, false);
		
		
		Button enterSPPB = (Button)rootView.findViewById(R.id.practice_SPPB_button);
		
		enterSPPB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment fragment = new PracticeSPPBFragment(patient);

				Bundle data = new Bundle();

				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("training").commit();
				
			}
		});
		
		
		
		return rootView;

	}
}
