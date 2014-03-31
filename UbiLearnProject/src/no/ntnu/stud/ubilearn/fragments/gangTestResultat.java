package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.WalkingSPPB;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class gangTestResultat extends Fragment{

	private double[] results;
	private TextView poeng1;
	private TextView poeng2;
	private Spinner fail1;
	private Spinner fail2;
	private WalkingSPPB test;
	private PractiseDAO dao;

	public gangTestResultat(double[] results) {
		this.results=results;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.gangtest_resultat, container, false);
		fail1 = (Spinner)view.findViewById(R.id.fail_chooser);
		poeng1= (TextView)view.findViewById(R.id.poeng1);
		fail2 = (Spinner)view.findViewById(R.id.fail_chooser2);
		poeng2= (TextView)view.findViewById(R.id.poeng2);
		
		dao = new PractiseDAO(getActivity());

		fail1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("Item picked", arg2+"");

				if(arg2!=0){
					poeng1.setText("Poengsum: 0");
				}else{
					poeng1.setText("Poengsum: "+setPoeng(results[0]));
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
					poeng2.setText("Poengsum: "+setPoeng(results[1]));
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}
	private int setPoeng(double tid){
		if(tid>8.7){
			return 1;
		}else if(tid>6.21 && tid<8.7){
			return 2;
		}
		else if(tid>4.82 && tid<6.2){
			return 3;
		}
		else if(tid < 4.82){
			return 4;
		}
		return 0;
	}
}
