package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.Patient;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPatientFragment extends Fragment {
	
	
	private EditText patientNameEdit;
	private EditText patientAgeEdit;
	private EditText patientProblemsEdit;
	private EditText patientOtherEdit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View rootView = inflater.inflate(R.layout.fragment_practise_patient, container, false);		
		patientNameEdit = (EditText)rootView.findViewById(R.id.practise_patient_name_edit);		
		patientAgeEdit = (EditText)rootView.findViewById(R.id.practise_patient_age_edit);
		patientProblemsEdit = (EditText)rootView.findViewById(R.id.practice_patient_problems_edit);
		patientOtherEdit = (EditText)rootView.findViewById(R.id.practice_patient_other_edit);
		
		Button saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
		saveBtn.setVisibility(Button.VISIBLE);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
							
				String name = patientNameEdit.getText().toString();
				String age = patientAgeEdit.getText().toString();
				String problems = patientProblemsEdit.getText().toString();
				String comment = patientOtherEdit.getText().toString();
				
				Patient patient = new Patient(name, age, problems, comment, new Date());
				PractiseDAO dao = new PractiseDAO(getActivity());
				dao.open();
				dao.insertPatient(patient);
				dao.close();
				
				PractiseFragment fragment = new PractiseFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
			}
		});
		
		removeExcessComponents(rootView);
		
		return rootView;

	}

	private void removeExcessComponents(View view) {
		LinearLayout l = (LinearLayout) view.findViewById(R.id.layout1);
		l.setVisibility(LinearLayout.GONE);
		Button b1 = (Button) view.findViewById(R.id.register_button);
		b1.setVisibility(Button.GONE);
		Button b2 = (Button) view.findViewById(R.id.practice_SPPB_button);
		b2.setVisibility(Button.GONE);
	}
}
