package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.BalanceSPPB;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.StandUpSPPB;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class PractiseStandupFragment extends Fragment
{
	private Patient patient;
	private StandUpSPPB result;
	
	private Timer t;
	private int TimeCounter = 0;
	private double time = 0;
	
	boolean running = false;
	String minSec;
	int reps = 0;
	
	private EditText heightField;
	
	public PractiseStandupFragment(Patient patient)
	{
		this.patient = patient;
		result = new StandUpSPPB("Reise seg test", this.patient.getId() , time, new Date());	
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view =  inflater.inflate(R.layout.fragment_practise_standup, container, false);
		
		final ImageView mainButton = (ImageView) view.findViewById(R.id.circle);
		final TextView start = (TextView) view.findViewById(R.id.start);
		final TextView titleField = (TextView) view.findViewById(R.id.titleField);
		final TextView timeField = (TextView) view.findViewById(R.id.timeField);
		final TextView repsField = (TextView) view.findViewById(R.id.repsField);
		heightField = (EditText) view.findViewById(R.id.cmField);
		final Button next = (Button)view.findViewById(R.id.next_button);
		next.setEnabled(false);
		
		ImageView info = (ImageView)view.findViewById(R.id.standUp_info);
		info.setClickable(true);
		info.setEnabled(true);
		
		info.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				final Dialog dialog = new Dialog(getActivity());
				dialog.show();
				dialog.setContentView(R.layout.info_dialog_standup);
				dialog.setTitle("Reise seg test");
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
		
		mainButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);	
				anim.setDuration(10000);
				
				if(!running)
				{
					mainButton.startAnimation(anim);
					start.setText("Repetisjon");
					
					t = new Timer();
					t.scheduleAtFixedRate(new TimerTask() {

						@Override
						public void run() {
						
							getActivity().runOnUiThread(new Runnable() {
								public void run() {
									minSec = ((int)TimeCounter/10) + ":" + TimeCounter%10;
									timeField.setText(minSec); // you can set it to a textView to show it to the user to see the time passing while he is writing.
									TimeCounter++;
								}
							});
						}
					}, 100, 100);
					
					running = true;
				}
				else if(running)
				{
					reps++;
					repsField.setText(" " + reps);
					if(reps>=5)
					{
						mainButton.clearAnimation();
						time = Double.parseDouble(minSec.replace(":", "."));
						result.setTime(time);
						result.setSeatHeight(heightField.getText().toString());
						start.setText("Start");
						try 
						{
							t.cancel();
						} catch (Exception e) 
						{
							e.printStackTrace();
						}
						
						running = false;
						mainButton.setClickable(false);
						next.setEnabled(true);
					}
				}
			}
		});
		
		next.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) {
				Fragment fragment = new SPPBStandUpResultFragment(result);
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Standup").commit();
			}
		});
		
		return view;
	}
}