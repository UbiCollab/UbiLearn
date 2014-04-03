package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class PractiseFragment extends Fragment
{
	private ArrayList<Patient> patientList;
	private ListView listView;
	PractiseDAO dao;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		patientList = generatePatients();
		View view =  inflater.inflate(R.layout.fragment_practise, container, false);

		Button enterExercises = (Button) view.findViewById(R.id.exercises_button);

		enterExercises.setOnClickListener(new OnClickListener()
		{
			public void onClick(View vi)
			{
				Fragment fragment = new PractiseExercisesFragment();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("practise").commit();
			}
		});


		listView = (ListView) view.findViewById(R.id.patientsList);
		populateList();

		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				Fragment fragment = new PractisePatientsFragment(patientList.get(arg2));
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("patient").commit();
				
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
	public ArrayList<Patient> generatePatients(){
		//TODO hent pasienter fra backend
		ArrayList<Patient> tempPatients = new ArrayList<Patient>();
		
		Patient p1 = new Patient("1", "Kristian", "23", "vondt i ryggen", "snuser mye", new Date());
		dao.insertPatient(p1);
		Patient p2 = new Patient("2", "Ingeborg", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p3 = new Patient("3", "Kyrre", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p4 = new Patient("4", "Espen", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p5 = new Patient("5", "Kua", "23", "vondt i ryggen", "snuser mye", new Date());
		Patient p6 = new Patient("6", "Grisen", "23", "vondt i ryggen", "snuser mye", new Date());
		
		//(String objectId, String name, String age, String problems, String comment, Date createdAt)
		
		tempPatients.add(p1);
		tempPatients.add(p2);
		tempPatients.add(p3);
		tempPatients.add(p4);
		tempPatients.add(p5);
		tempPatients.add(p6);
		
		return tempPatients;
		
	}


}

