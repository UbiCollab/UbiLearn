package no.ntnu.stud.ubilearn.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import no.ntnu.stud.ubilearn.R;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class Practice_SPPB_gangtestFragment extends Fragment{
	private Timer t;
	private int TimeCounter = 0;
	TextView time;
	boolean isTime = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_practice_gangtest, container, false);

		time = (TextView)rootView.findViewById(R.id.gangtest_tid);
		t = new Timer();

		final ImageView imageView = (ImageView) rootView.findViewById(R.id.gangtest_timeButton);
		final TextView start = (TextView) rootView.findViewById(R.id.start);


		ImageView info = (ImageView)rootView.findViewById(R.id.gangtest_info);
		info.setClickable(true);
		info.setEnabled(true);
		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("Trykket: ", "info knapp");
				final Dialog dialog = new Dialog(getActivity());
				dialog.show();
				dialog.setContentView(R.layout.info_dialog_gangtest);
				dialog.setTitle("Gangtest");
				Button infoOk = (Button)dialog.findViewById(R.id.info_ok);
				infoOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
			}
		});


		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

				if(isTime == false){
					start.setText("Stopp");
					anim.setDuration(10000);
					anim.setRepeatCount(Animation.INFINITE);
					imageView.setAnimation(anim);
					t = new Timer();
					t.scheduleAtFixedRate(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							getActivity().runOnUiThread(new Runnable() {
								public void run() {
									String minSec = ((int)TimeCounter/10) + ":" + TimeCounter%10;
									time.setText(minSec); // you can set it to a textView to show it to the user to see the time passing while he is writing.
									TimeCounter++;
								}
							});
						}
					}, 100, 100);
					isTime = true;
				}else if(isTime){
					start.setText("Start");

					try {
						t.cancel();

					} catch (Exception e) {
						e.printStackTrace();
					}

					imageView.clearAnimation();
					isTime = false;
				}
			}
		});


		return rootView;
	}

}
