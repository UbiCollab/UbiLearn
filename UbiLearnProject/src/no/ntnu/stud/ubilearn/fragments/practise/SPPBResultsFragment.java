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
import android.widget.TextView;
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
		patientId = getArguments().getInt("id");
		dao = new PractiseDAO(getActivity());
		dao.open();
		HashMap<String,SPPB> tests = dao.getBestResults(patientId);
		dao.close();
		walking = (WalkingSPPB) tests.get("Walking");
		
		TextView testName = (TextView) view.findViewById(R.id.testName);
		TextView testDate = (TextView) view.findViewById(R.id.testDate);
		TextView time = (TextView) view.findViewById(R.id.time1);
		TextView score = (TextView) view.findViewById(R.id.score);
		if (walking != null){
			testName.setText(walking.getName());
			testDate.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(walking.getCreatedAt()));
			time.setText("Time: " + walking.getTime());
			score.setText("Score: " + walking.getScore());
		}
		else{
			testName.setText("Gangtest");
			testDate.setText("Ingen gangtester funnet");
			time.setText("Time: ");
			score.setText("Score: ");
		}
		
	
		return view;
	}
}
