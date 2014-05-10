package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.ArrayList;
import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.Exercise;
import no.ntnu.stud.ubilearn.models.Patient;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class PractiseFragment extends Fragment {
	private ArrayList<Patient> patientList;
	private ListView listView;
	PractiseDAO dao;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		dao = new PractiseDAO(getActivity());
		dao.open();
		dao.printTables();
		patientList = dao.getPatients();
		dao.close();
		
		//------------GENERATES PATIENTS IF DB IS EMPTY---------------------
		if(patientList.isEmpty()){
			generatePatients();
			dao.open();
			patientList = dao.getPatients();
			dao.printTables();
			dao.close();
		}
		//---------------------------------------------------------------------
		
		View view =  inflater.inflate(R.layout.fragment_practise, container, false);

		Button enterExercises = (Button) view.findViewById(R.id.exercises_button);

		enterExercises.setOnClickListener(new OnClickListener()
		{
			public void onClick(View vi)
			{
				Fragment fragment = new PractiseExercisesFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
//				Fragment fragment = new ExcerciseFragment();
//				((ExcerciseFragment)fragment).setExercise(new Exercise("dsfs", "Lorem Ipsum", getResources().getString(R.string.fillText)));
//				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
			}
		});


		listView = (ListView) view.findViewById(R.id.patientsList);
		populateList();

		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				Fragment fragment = new PractisePatientsFragment(patientList.get(arg2));
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "patient").addToBackStack("patient").commit();
				
			}
		});


		Button addPatient = (Button) view.findViewById(R.id.addPatientBtn);
		addPatient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddPatientFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("add patient").commit();

			}
		});
		
		return view;
	}
	
	public void populateList(){
		ArrayList<String> nameList = new ArrayList<String>();
		for (Patient p : patientList) {
			nameList.add(p.getName());
		}
		ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nameList);
		listView.setAdapter(adaptor);
	}
	public void generatePatients(){
		
		Log.i("PractiseFragment", "Generating patients");
		
		ArrayList<Patient> tempPatients = new ArrayList<Patient>();
		
		Patient p1 = new Patient("Kristian", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p2 = new Patient("Ingeborg", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p3 = new Patient("Kyrre", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p4 = new Patient("Espen", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p5 = new Patient("Jørgen", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p6 = new Patient("Arne", "23", "vondt i ryggen", "snuser mye", new Date());
		
		tempPatients.add(p1);
		tempPatients.add(p2);
		tempPatients.add(p3);
		tempPatients.add(p4);
		tempPatients.add(p5);
		tempPatients.add(p6);
		
		dao.open();
		dao.insertPatients(tempPatients);
		dao.close();		
	}


}

