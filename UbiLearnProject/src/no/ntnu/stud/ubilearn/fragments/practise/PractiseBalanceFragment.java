package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.Date;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.db.PractiseDAO;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.Patient;
import android.annotation.SuppressLint;
import android.app.Dialog;
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

@SuppressLint("ValidFragment")
public class PractiseBalanceFragment extends Fragment {
	
	private int state = 0;
	private int timeLeft = 0;
	private int pairedScore = 0;
	private int semiTandemScore = 0;
	private int tandemScore = 0;
	private Patient patient;
	private BalanceSPPB result;
	
	/**
	 * constructor
	 * @param object of the type Patient
	 */
	public PractiseBalanceFragment(Patient patient)
	{
		this.patient = patient;
		result = new BalanceSPPB("Balansetest", this.patient.getId() , new Date(), pairedScore, semiTandemScore, tandemScore);	
	}
	
	public OnClickListener start, stop;
	
	/**
	 * creates a new view, defines the onClickListeners and the functionality of the test
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_practise_balance, container, false);
		
		final Button startStopButton = (Button) view.findViewById(R.id.startStopButton);
		final Button next = (Button) view.findViewById(R.id.next_button);
		
		final TextView titleField = (TextView) view.findViewById(R.id.titleField);
		final TextView score1 = (TextView) view.findViewById(R.id.score1);
		final TextView score2 = (TextView) view.findViewById(R.id.score2);
		final TextView score3 = (TextView) view.findViewById(R.id.score3);
		final TextView score4 = (TextView) view.findViewById(R.id.score4);
		final ImageView	imageField = (ImageView) view.findViewById(R.id.imageField);
		final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		final ImageView info = (ImageView)view.findViewById(R.id.balance_info);
		
		
		next.setEnabled(false);
		
		info.setClickable(true);
		info.setEnabled(true);
		
		progressBar.setMax(10);
		progressBar.setProgress(10);
		
		/**
		 * counts down 10 seconds. when finished: compute score and go to next part of test
		 */
		final CountDownTimer timer = new CountDownTimer(10000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				progressBar.setProgress((int) (millisUntilFinished / 1000));
				timeLeft = (int) millisUntilFinished / 1000;
			}
			
			public void onFinish()
			{
				startStopButton.setEnabled(false);
				progressBar.setProgress(0);
				startStopButton.setOnClickListener(start);
				startStopButton.setText("Start");
				
				state = state + 1;
				switch(state) {
					case 1:
						titleField.setText("SEMI-TANDEM");
						imageField.setImageResource(R.drawable.semi2);
						score1.setText("1 poeng");
						startStopButton.setEnabled(true);
						result.setPairedScore(1);
						break;
					case 2:
						titleField.setText("TANDEM");
						imageField.setImageResource(R.drawable.tandem2);
						score2.setText("1 poeng");
						startStopButton.setEnabled(true);
						result.setSemiTandemScore(1);
						break;
					case 3:
						titleField.setText("TEST FULLFØRT");
						imageField.setImageResource(R.drawable.balancecheck);
						score3.setText("2 poeng");
						startStopButton.setEnabled(false);
						next.setEnabled(true);
						result.setTandemScore(2);
						break;
				}
				
				score4.setText(result.getScore() + " poeng");
			}
		};
		
		/**
		 * onClick for the info button: open the info dialog window
		 */
		info.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				final Dialog dialog = new Dialog(getActivity());
				dialog.show();
				dialog.setContentView(R.layout.info_dialog_balance);
				dialog.setTitle("Balansetest");
				Button infoOk = (Button)dialog.findViewById(R.id.info_ok);
				infoOk.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						dialog.dismiss();
					}
				});
			}
		});
		
		/**
		 * onClick for the stop button: compute score and go to next part of test
		 */
		stop = new OnClickListener()
		{
			public void onClick(View vi)
			{
				startStopButton.setEnabled(false);
				startStopButton.setOnClickListener(start);
				int i = timeLeft;
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
					titleField.setText("TEST FULLFØRT");
					imageField.setImageResource(R.drawable.balancecheck);
					if(i>7)
					{
						score3.setText("0 poeng");
					}
					else
					{
						score3.setText("1 poeng");
						result.setTandemScore(1);
					}
					startStopButton.setEnabled(false);
					next.setEnabled(true);
					break;
				}
				
				score4.setText(result.getScore() + " poeng");
			}
		};
		
		/**
		 * onClick for the start button: start the timer
		 */
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
		
		/**
		 * onClick for the next button: go to the result fragment
		 */
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new SPPBBalanceResultFragment(result);
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Balance").commit();
			}
		});
		
		return view;
	}
}