package no.ntnu.stud.ubilearn.fragments.practise;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.WalkingSPPB;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class SPPBWalkingResultFragment extends Fragment{

	private double[] results;
	private TextView poeng1;
	private TextView poeng2;
	private Spinner fail1;
	private Spinner fail2;
	private PractiseDAO dao;
	private Button finishBtn;
	
	private WalkingSPPB test1;
	private WalkingSPPB test2;

	public SPPBWalkingResultFragment(WalkingSPPB test1, WalkingSPPB test2) {
		this.test1 = test1;
		this.test2 = test2;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_walking_result, container, false);
		fail1 = (Spinner)view.findViewById(R.id.fail_chooser);
		poeng1= (TextView)view.findViewById(R.id.poeng1);
		fail2 = (Spinner)view.findViewById(R.id.fail_chooser2);
		poeng2= (TextView)view.findViewById(R.id.poeng2);
		finishBtn = (Button)view.findViewById(R.id.finishBtn);
		
		dao = new PractiseDAO(getActivity());

		fail1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("Item picked", arg2+"");

				if(arg2!=0){
					poeng1.setText("Poengsum: 0");
				}else{
					poeng1.setText("Poengsum: "+test1.getScore());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		fail2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("Item picked", arg2+"");

				if(arg2!=0){
					poeng2.setText("Poengsum: 0");
				}else{
				
					poeng2.setText("Poengsum: "+test2.getScore());
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		finishBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(fail1.getSelectedItemPosition() != 0)
					test1.failed(true);
				if(fail2.getSelectedItemPosition() != 0)
					test2.failed(true);
				dao.open();
				if(test1.compareTo(test2)>0)
					dao.insertSPPB(test1);
				else
					dao.insertSPPB(test2);
				Patient patient = dao.getPatient(test1.getPatientId());
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
