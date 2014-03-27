package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PractiseBalanceFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_balance, container, false);
		
		Button startStopButton = (Button) view.findViewById(R.id.startStopButton);
		
		final TextView counterField = (TextView) view.findViewById(R.id.counterField);
		
		startStopButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View vi)
			{
				 new CountDownTimer(10000, 1000) {

				     public void onTick(long millisUntilFinished) {
				         counterField.setText(" " + millisUntilFinished / 1000);
				     }

				     public void onFinish() {
				         
				     }
				  }.start();

			}
		});
		
		return view;
	}
}