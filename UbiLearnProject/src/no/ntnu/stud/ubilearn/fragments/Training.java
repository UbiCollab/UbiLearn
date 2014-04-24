package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;


import no.ntnu.stud.ubilearn.models.CasePatient;
import android.os.Bundle;

import android.app.Dialog;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;




public class Training extends Fragment {
	private RelativeLayout rl;
	private ScrollView sv;
	private View root;
	private View root2;


	ArrayList<CasePatient> patientList;
	int i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		patientList = User.getInstance().getPatientList();
		if(patientList == null){
			Toast.makeText(getActivity(), "lista er tom", Toast.LENGTH_SHORT).show();
		}
		//Log.v("TrainingPatient", ""+patientList.size());



		root = inflater.inflate(R.layout.fragment_training, null);
		sv = (ScrollView) root.findViewById(R.id.training_scroll);
		rl = (RelativeLayout) root.findViewById(R.id.training_rel);

		if(User.getInstance().getPoints()>=10){
			Toast.makeText(getActivity(), "Congratulations, you are now in level 2", Toast.LENGTH_SHORT).show();
			root = inflater.inflate(R.layout.fragment_training_level2, null);
			sv = (ScrollView) root.findViewById(R.id.training_scroll);
			rl = (RelativeLayout) root.findViewById(R.id.training_rel);
			return root;
		}




		//	//	MainAcitivit.generatePatients();

		return root;
	}



	public void houseClick(View v){
		final View house = v;
		Log.v("husnummer: "+house.getContentDescription(), "");
		setCarPositionY(house.getY() + house.getWidth()/2);
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.training_popup);
		if(house.getContentDescription().toString().length() > 0 ){
			i = Integer.parseInt(house.getContentDescription().toString());
		}

		Button cancel = (Button) dialog.findViewById(R.id.training_popup_cancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button enter = (Button) dialog.findViewById(R.id.training_popup_enter);

		enter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View vi) {

				Fragment patient = new PatientCaseFragment(patientList.get(i));

				Bundle data = new Bundle();

				getFragmentManager().beginTransaction().replace(R.id.content_frame, patient).addToBackStack("training").commit();
				dialog.dismiss();
			}
		});

		try{
			dialog.setTitle("Her bor " +patientList.get(i).getName());
			dialog.show();
		}
		catch(Exception e){
			Toast.makeText(getActivity(), "ingen hjemme", Toast.LENGTH_SHORT).show();
		}
	}


	private void setCarPositionY(float y){
		ImageView car = (ImageView) root.findViewById(R.id.traningCar);
		System.out.println("works?");
		if (car == null) {
			System.out.println("thats why");
		}
		car.setY(y);
	}

}
