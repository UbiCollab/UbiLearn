package no.ntnu.stud.ubilearn.fragments;

import java.util.HashMap;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.CasePatient;
import no.ntnu.stud.ubilearn.models.CasePatientStatus;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * class for the patient case
 * @author ingeborgoftedal
 *
 */
@SuppressLint("ValidFragment")
public class PatientCaseFragment extends Fragment{
	
	private String _name, _age, _gender, _pasientInfo;
	private int _level;
	private CasePatient patient;
	private TextView statusText;
	
	//Empty constructor for validFragment
	public PatientCaseFragment() {
		
	}
	
	public PatientCaseFragment(CasePatient patient) {
		this.patient = patient;
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		_name = patient.getName();
		_age = patient.getAge();
		_gender = patient.getGender();
		_pasientInfo = patient.getInfo();
		_level = patient.getLevel();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.training_case, container, false);
		
		TextView name = (TextView) rootView.findViewById(R.id.case_PatientName);
		name.setText(_name);
		TextView age = (TextView) rootView.findViewById(R.id.case_patientAge);
		age.setText("Alder: " + _age);
		TextView gender = (TextView) rootView.findViewById(R.id.case_patientGender);
		gender.setText("Kjønn: " + _gender);
		TextView pasientInfo = (TextView) rootView.findViewById(R.id.case_patientInfoField);
		pasientInfo.setText(_pasientInfo);
		RatingBar level = (RatingBar)rootView.findViewById(R.id.training_ratingBar);

		TrainingDAO trainingDAO = new TrainingDAO(getActivity());
		trainingDAO.open();
		
		level.setEnabled(false);
		
		
		statusText = (TextView)rootView.findViewById(R.id.training_status);
		
		/**
		 * sjekker om casen er klart eller ikke og setter rating etter hvor mange spm som er klart
		 */
		if(User.getInstance().getHouseStatus(patient.getObjectId()).getHighScore() > 0){
		statusText.setText("Gratulerer, du har klart denne casen " + "\n" +User.getInstance().getHouseStatus(patient.getObjectId()).getHighScore() + "/" + (trainingDAO.getPatientQuizzes(patient)).size());
		if(User.getInstance().getHouseStatus(patient.getObjectId()).getHighScore() < trainingDAO.getPatientQuizzes(patient).size()){
			level.setRating(1);
		}
		else{
			level.setRating(2);
		}
		}
		else{
			statusText.setText("Du har ikke klart casen ennå " + "\n" +User.getInstance().getHouseStatus(patient.getObjectId()).getHighScore() + "/" + (trainingDAO.getPatientQuizzes(patient)).size());

		}
		trainingDAO.close();
		
		
		Button next = (Button)rootView.findViewById(R.id.training_case_next);
		
		next.setOnClickListener(new OnClickListener() {//neste knapp til Quiz fra pasientCase
			
			@Override
			public void onClick(View v) {

				TrainingDAO trainingDAO = new TrainingDAO(getActivity());
				trainingDAO.open();
				
				if(trainingDAO.getPatientQuizzes(patient) != null){
				Fragment fragment = new QuizFragment(patient);
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("quiz").commit();
				}else{
					Toast.makeText(getActivity(), "No quiz avaliable", Toast.LENGTH_SHORT).show();
				}
				
				trainingDAO.close();
				
			}
		});
		//tilbake knapp fra pasientcase
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
