package no.ntnu.stud.ubilearn.fragments.practise;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("ValidFragment")
public class SPPBStandUpResultFragment extends Fragment
{
	private StandUpSPPB test;
	private TextView poeng;
	private Spinner fail;
	private PractiseDAO dao;
	private Button finishBtn;
	
	public SPPBStandUpResultFragment(StandUpSPPB test) 
	{
		this.test = test;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_standup_result, container, false); 
		
		poeng= (TextView)view.findViewById(R.id.poeng1);
		finishBtn = (Button)view.findViewById(R.id.finishBtn);
		fail = (Spinner)view.findViewById(R.id.fail_chooser);
		
		dao = new PractiseDAO(getActivity());

		fail.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("Item picked", arg2+"");

				if(arg2!=0){
					poeng.setText("Poengsum: 0");
				}else{
					poeng.setText("Poengsum: "+test.getScore());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		/**
		 * onClick for the finish button: save results to dao
		 */
		finishBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				if(fail.getSelectedItemPosition() != 0)
					test.failed(true);
				dao.open();
				dao.insertSPPB(test);
				Patient patient = dao.getPatient(test.getPatientId());
				dao.close();
				
//				getFragmentManager().popBackStack(getFragmentManager().findFragmentByTag("patient").getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
				getFragmentManager().popBackStack();
				getFragmentManager().popBackStack();
				getFragmentManager().popBackStack();
				getFragmentManager().beginTransaction().replace(R.id.content_frame, getFragmentManager().findFragmentByTag("patient")).commit();
			}
		});
		
		return view;
	}
}