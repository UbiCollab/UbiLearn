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
	
	public OnClickListener start, stop;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_balance, container, false);
		
		final Button startStopButton = (Button) view.findViewById(R.id.startStopButton);
		
		final TextView titleField = (TextView) view.findViewById(R.id.titleField);
		final TextView score1 = (TextView) view.findViewById(R.id.score1);
		final TextView score2 = (TextView) view.findViewById(R.id.score2);
		final TextView score3 = (TextView) view.findViewById(R.id.score3);
		final TextView score4 = (TextView) view.findViewById(R.id.score4);
		final ImageView	imageField = (ImageView) view.findViewById(R.id.imageField);
		final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		
		progressBar.setMax(10);
		progressBar.setProgress(10);
		
		final CountDownTimer timer = new CountDownTimer(10000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				progressBar.setProgress((int) (millisUntilFinished / 1000));
			}
			
			public void onFinish()
			{
				startStopButton.setEnabled(false);
				progressBar.setProgress(0);
				startStopButton.setOnClickListener(start);
				startStopButton.setText("Start");
				score++;
				
				state = state + 1;
				switch(state) {
					case 1:
						titleField.setText("SEMI-TANDEM");
						imageField.setImageResource(R.drawable.semi);
						score1.setText("1 poeng");
						startStopButton.setEnabled(true);
						break;
					case 2:
						titleField.setText("TANDEM");
						imageField.setImageResource(R.drawable.tandem2);
						score2.setText("1 poeng");
						startStopButton.setEnabled(true);
						break;
					case 3:
						titleField.setText("TEST FULLFØRT");
						imageField.setImageResource(R.drawable.balancecheck);
						score3.setText("2 poeng");
						score++;
						startStopButton.setEnabled(false);
						break;
				}
				
				score4.setText(getScore() + " poeng");
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
				
				state = state + 1;
				switch(state) {
				case 1:
					titleField.setText("SEMI-TANDEM");
					imageField.setImageResource(R.drawable.semi2);
					score1.setText("0 poeng");
					startStopButton.setEnabled(true);
					break;
				case 2:
					titleField.setText("TANDEM");
					imageField.setImageResource(R.drawable.tandem2);
					score2.setText("0 poeng");
					startStopButton.setEnabled(true);
					break;
				case 3:
					titleField.setText("TEST FULLF�RT");
					imageField.setImageResource(R.drawable.balancecheck);
					score3.setText("0 poeng");
					score++;
					startStopButton.setEnabled(false);
					break;
				}
				
				score4.setText(getScore() + " poeng");
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
		
		startStopButton.setOnClickListener(start);
		
		return view;
	}
}