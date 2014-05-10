package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.Patient;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class PractisePatientsFragment extends Fragment 
{
	
	//UI
	private TextView patientName;
	private EditText patientNameEdit;
	private TextView patientAge;
	private EditText patientAgeEdit;
	private TextView patientProblems;
	private EditText patientProblemsEdit;
	private TextView patientOther;
	private EditText patientOtherEdit;
	private TextView SPPBresult;
	private ImageView editInfo;
	private Button saveBtn;
	private Button deleteBtn;
	private Button rsltBtn;
	Patient patient;
	
	
	public PractisePatientsFragment(Patient patient){
		this.patient = patient;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View rootView = inflater.inflate(R.layout.fragment_practise_patient, container, false);

		patientName = (TextView)rootView.findViewById(R.id.practise_patient_name);
		
		patientNameEdit = (EditText)rootView.findViewById(R.id.practise_patient_name_edit);

		patientAge = (TextView)rootView.findViewById(R.id.practice_patient_age);
		patientAge.setText("Alder: ");
		
		patientAgeEdit = (EditText)rootView.findViewById(R.id.practise_patient_age_edit);
		

		patientProblems = (TextView)rootView.findViewById(R.id.practice_patient_problems);
		patientProblems.setText("Problemområder: ");
		patientProblemsEdit = (EditText)rootView.findViewById(R.id.practice_patient_problems_edit);

		patientOther = (TextView)rootView.findViewById(R.id.practice_patient_other);
		patientOther.setText("Kommentar: ");
		patientOtherEdit = (EditText)rootView.findViewById(R.id.practice_patient_other_edit);
		saveBtn = (Button)rootView.findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				patient.setName(patientNameEdit.getText().toString());
				patient.setAge(patientAgeEdit.getText().toString());
				patient.setProblems(patientProblemsEdit.getText().toString());
				patient.setComment(patientOtherEdit.getText().toString());
				
				PractiseDAO dao = new PractiseDAO(getActivity());
				dao.open();
				dao.insertPatient(patient);
				dao.close();
				setEnabled(false);
			}
		});
		
		deleteBtn = (Button)rootView.findViewById(R.id.deleteBtn);
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				        	PractiseDAO dao = new PractiseDAO(getActivity());
							dao.open();
							dao.deletePatientData(patient);
							dao.close();
							getFragmentManager().popBackStack();
							getFragmentManager().beginTransaction().replace(R.id.content_frame, new PractiseFragment()).commit();
//							getFragmentManager().beginTransaction().replace(R.id.content_frame, getFragmentManager().findFragmentByTag("Practise")).commit();
				            break;
				        case DialogInterface.BUTTON_NEGATIVE:
				            break;
				        }
				    }
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Are you sure you want to delete patient " + patient.getName() +"?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
				
			}
		});

		setEnabled(false);
		
		editInfo = (ImageView)rootView.findViewById(R.id.edit_info_button);
		
		Button enterSPPB = (Button)rootView.findViewById(R.id.practice_SPPB_button);
		
		enterSPPB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new PracticeSPPBFragment(patient);

				Bundle data = new Bundle();

				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("training").commit();
				
			}
		});
		
		editInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setEnabled(true);
				
			}
		});
		
		rsltBtn = (Button)rootView.findViewById(R.id.resultBtn);
		rsltBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new SPPBResultsFragment();
				Bundle dataArg = new Bundle();
				dataArg.putInt("id", patient.getId());
				fragment.setArguments(dataArg);
				
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("results").commit();
				
			}
		});
		
		return rootView;

	}
	private void setEnabled(boolean b){
		int v;
//		if(b){
//			v = 0x00000000;
//		}
//		else{
//			v = 0x00000004;	
//			}
	//	patientNameEdit.setVisibility(v);
		patientNameEdit.setEnabled(b);
		patientNameEdit.setText(patient.getName());
//		patientAgeEdit.setVisibility(v);
		patientAgeEdit.setEnabled(b);
		patientAgeEdit.setText(patient.getAge());
//		patientProblemsEdit.setVisibility(v);
		patientProblemsEdit.setEnabled(b);
		patientProblemsEdit.setText(patient.getProblems());
//		patientOtherEdit.setVisibility(v);
		patientOtherEdit.setEnabled(b);
		patientOtherEdit.setText(patient.getComment());
		if(b){
			saveBtn.setVisibility(Button.VISIBLE);
			deleteBtn.setVisibility(Button.VISIBLE);
		}
		else{
			saveBtn.setVisibility(Button.INVISIBLE);
			deleteBtn.setVisibility(Button.INVISIBLE);
		}
		}
	}

