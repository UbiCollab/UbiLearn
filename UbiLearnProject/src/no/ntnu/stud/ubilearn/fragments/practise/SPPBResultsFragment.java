package no.ntnu.stud.ubilearn.fragments.practise;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.SPPB;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import no.ntnu.stud.ubilearn.models.WalkingSPPB;

public class SPPBResultsFragment extends Fragment {
	private int patientId;
	private PractiseDAO dao;
	private WalkingSPPB walking;
	private BalanceSPPB balance;
	private StandUpSPPB standUp;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_practise_sbbp_results, container, false);
		patientId = savedInstanceState.getInt("id");
		dao = new PractiseDAO(getActivity());
		dao.open();
		HashMap<String,SPPB> tests = dao.getBestResults(patientId);
		dao.close();
		walking = (WalkingSPPB) tests.get("WalkingTest");
		
		EditText testName = (EditText) view.findViewById(R.id.testName);
		testName.setText(walking.getName());
		EditText testDate = (EditText) view.findViewById(R.id.testDate);
		testName.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(walking.getCreatedAt()));
	
		return view;
	}
}
