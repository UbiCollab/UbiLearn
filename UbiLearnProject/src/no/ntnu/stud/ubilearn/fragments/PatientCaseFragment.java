package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PatientCaseFragment extends Fragment{
	
	private String _name, _age, _gender, _pasientInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Bundle extra = getArguments();
		_name = extra.getString("name", "Test");
		_age = extra.getString("age", "Test");
		_gender = extra.getString("gender", "Test");
		_pasientInfo = extra.getString("info", "Test");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.training_case, container, false);
		
		TextView name = (TextView) rootView.findViewById(R.id.case_PatientName);
		name.setText(_name);
		TextView age = (TextView) rootView.findViewById(R.id.case_patientAge);
		age.setText(_age);
		TextView gender = (TextView) rootView.findViewById(R.id.case_patientGender);
		gender.setText(_gender);
		TextView pasientInfo = (TextView) rootView.findViewById(R.id.case_patientInfoField);
		pasientInfo.setText(_pasientInfo);
		
		Button next = (Button)rootView.findViewById(R.id.training_case_next);
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new QuizFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("quiz").commit();
			}
		});
		
		Button back = (Button)rootView.findViewById(R.id.training_case_back);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});
		
		return rootView;
	}
	
}