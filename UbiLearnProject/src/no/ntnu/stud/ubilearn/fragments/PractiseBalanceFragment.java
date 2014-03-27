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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PractiseBalanceFragment extends Fragment {
	
	int state = 0;
	int score = 0;
	
	public int getScore() {
		return score;
	}

	public void changeScore(int i) {
		score = score + i;
	}
	
	public OnClickListener start, stop;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_balance, container, false);
		
		final Button startStopButton = (Button) view.findViewById(R.id.startStopButton);
		
		final TextView counterField = (TextView) view.findViewById(R.id.counterField);
		
		final TextView scoreField = (TextView) view.findViewById(R.id.scoreField);
		
		final TextView titleField = (TextView) view.findViewById(R.id.titleField);
		
		final ImageView	imageField = (ImageView) view.findViewById(R.id.imageField);
		
		final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		
		progressBar.setMax(10);
		progressBar.setProgress(10);
		
		final CountDownTimer timer = new CountDownTimer(10000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				//counterField.setText(" " + millisUntilFinished / 1000);
				progressBar.setProgress((int) (millisUntilFinished / 1000));
			}
			
			public void onFinish()
			{
				startStopButton.setEnabled(false);
				startStopButton.setOnClickListener(start);
				startStopButton.setText("Start");
				counterField.setText(" ");
				changeScore(1);
				
				state = state + 1;
				switch(state) {
					case 1:
						titleField.setText("SEMI-TANDEM");
						imageField.setImageResource(R.drawable.semi2);
						startStopButton.setEnabled(true);
						break;
					case 2:
						titleField.setText("TANDEM");
						imageField.setImageResource(R.drawable.tandem2);
						startStopButton.setEnabled(true);
						break;
					case 3:
						titleField.setText("FERDIG");
						changeScore(1);
						startStopButton.setEnabled(false);
						break;
				}
				
				scoreField.setText("Poeng: " + getScore());
			}
		};
		
		stop = new OnClickListener()
		{
			public void onClick(View vi)
			{
				startStopButton.setEnabled(false);
				startStopButton.setOnClickListener(start);
				timer.cancel();
				startStopButton.setText("Start");
				counterField.setText(" ");
				
				state = state + 1;
				switch(state) {
				case 1:
					titleField.setText("SEMI-TANDEM");
					imageField.setImageResource(R.drawable.semi2);
					startStopButton.setEnabled(true);
					break;
				case 2:
					titleField.setText("TANDEM");
					imageField.setImageResource(R.drawable.tandem2);
					startStopButton.setEnabled(true);
					break;
				case 3:
					titleField.setText("FERDIG");
					changeScore(1);
					startStopButton.setEnabled(false);
					break;
				}
				
				scoreField.setText("Poeng: " + getScore());
			}
		};
		
		start = new OnClickListener()
		{
			public void onClick(View vi)
			{
				startStopButton.setEnabled(false);
				startStopButton.setOnClickListener(stop);
				timer.start();
				startStopButton.setText("Stopp");
				startStopButton.setEnabled(true);	
			}
		};
		
		titleField.setText("SAMLEDE FØTTER");
		startStopButton.setOnClickListener(start);
		
		return view;
	}
}