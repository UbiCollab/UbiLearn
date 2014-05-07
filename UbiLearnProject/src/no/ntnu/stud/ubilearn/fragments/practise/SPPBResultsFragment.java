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
		balance = (BalanceSPPB) tests.get("Balance");
		standUp = (StandUpSPPB) tests.get("StandUp");
		
		fillWalkingFields(view);
		fillBalanceFields(view);
		fillStandUpFields(view);
		
		
//		//fills in Walking test fields
//		TextView testName = (TextView) view.findViewById(R.id.testName1);
//		TextView testDate = (TextView) view.findViewById(R.id.testDate1);
//		TextView time = (TextView) view.findViewById(R.id.time1);
//		TextView score = (TextView) view.findViewById(R.id.total1);
//		if (walking != null){
//			testName.setText(walking.getName());
//			testDate.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(walking.getCreatedAt()));
//			time.setText("Time: " + walking.getTime());
//			score.setText("Score: " + walking.getScore());
//		}
//		else{
//			testName.setText("Gangtest");
//			testDate.setText("Ingen gangtester funnet");
//			time.setText("Time: ");
//			score.setText("Score: ");
//		}
		
	
		return view;
	}
	/**
	 * Fills inn all the fields in the Walking test part of the screen
	 * @param view the View that this fragment uses.
	 */
	private void fillWalkingFields(View view){
		TextView testName = (TextView) view.findViewById(R.id.testName1);
		TextView testDate = (TextView) view.findViewById(R.id.testDate1);
		TextView time = (TextView) view.findViewById(R.id.time1);
		TextView total = (TextView) view.findViewById(R.id.total1);
		if (walking != null){
			testName.setText(walking.getName());
			testDate.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(walking.getCreatedAt()));
			time.setText("Time: " + walking.getTime());
			total.setText("Score: " + walking.getScore());
		}
		else{
			testName.setText("Gangtest");
			testDate.setText("Ingen gangtester funnet");
			time.setText("Time: ");
			total.setText("Score: 0");
		}
	}
	/**
	 * Fills inn all the fields in the Balance test part of the screen
	 * @param view the View that this fragment uses.
	 */
	private void fillBalanceFields(View view){
		TextView testName = (TextView) view.findViewById(R.id.testName2);
		TextView testDate = (TextView) view.findViewById(R.id.testDate2);
		TextView score1 = (TextView) view.findViewById(R.id.score1);
		TextView score2 = (TextView) view.findViewById(R.id.score2);
		TextView score3 = (TextView) view.findViewById(R.id.score3);
		TextView total = (TextView) view.findViewById(R.id.total2);
		
		if (balance != null){
			testName.setText(balance.getName());
			testDate.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(balance.getCreatedAt()));
			score1.setText(balance.getPairedScore());
			score2.setText(balance.getSemiTandemScore());
			score2.setText(balance.getTandemScore());
			total.setText(balance.getScore());
		}
		else{
			testName.setText("Balansetest");
			testDate.setText("Ingen balansetester funnet");
			score1.setText("-");
			score2.setText("-");
			score2.setText("-");			
			total.setText("0");
		}
	}
	/**
	 * Fills inn all the fields in the StandUp test part of the screen
	 * @param view the View that this fragment uses.
	 */
	private void fillStandUpFields(View view){
		TextView testName = (TextView) view.findViewById(R.id.testName3);
		TextView testDate = (TextView) view.findViewById(R.id.testDate3);
		TextView time = (TextView) view.findViewById(R.id.time3);
		TextView total = (TextView) view.findViewById(R.id.total3);
		if (standUp != null){
			testName.setText(standUp.getName());
			testDate.setText("Dato: " + new SimpleDateFormat( "dd.MM.yyyy", Locale.getDefault()).format(standUp.getCreatedAt()));
			time.setText("Time: " + standUp.getTime());
			total.setText("Score: " + standUp.getScore());
		}
		else{
			testName.setText("Opp-å-stå-test");
			testDate.setText("Ingen Opp-å-stå-tester funnet");
			time.setText("Time: ");
			total.setText("Score: 0");
		}
	}
}
