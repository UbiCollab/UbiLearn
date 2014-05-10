package no.ntnu.stud.ubilearn.fragments.practise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.datatype.Duration;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.Patient;
import no.ntnu.stud.ubilearn.models.WalkingSPPB;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("ValidFragment")
public class PracticeSPPBWalkingFragment extends Fragment implements OnCheckedChangeListener{
	private Timer t;
	private int TimeCounter = 0;

	boolean isTime = false;
	String minSec;
	int testCounter = 1;
	ImageView resultAccept1;
	ImageView resultAccept2;
	TextView startTest;
	private Button next;
	

	//UI-elements
	private RadioButton noAidButton;
	private RadioButton crutchesButton;
	private RadioButton rollatorButton;
	private RadioButton otherButton;
	private List<RadioButton> radBtns;
	private EditText otherEdit;
	private TextView time;
	private TextView result1;
	private TextView result2;
	private ImageView circle;
	//Model:
	private Patient patient;
	private WalkingSPPB test1;
	private WalkingSPPB test2;
	
	public PracticeSPPBWalkingFragment(Patient patient){
		this.patient = patient;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_practice_walking, container, false);
		next = (Button)rootView.findViewById(R.id.next_button);
		time = (TextView)rootView.findViewById(R.id.gangtest_tid);
		result1 = (TextView)rootView.findViewById(R.id.gangtest_result1);
		result2 = (TextView)rootView.findViewById(R.id.gangtest_result2);
		resultAccept1 = (ImageView)rootView.findViewById(R.id.result1_accept);
		resultAccept2 = (ImageView)rootView.findViewById(R.id.result2_accept);
		startTest = (TextView)rootView.findViewById(R.id.start_test);
		
		radBtns = new ArrayList<RadioButton>();
		noAidButton = (RadioButton)rootView.findViewById(R.id.uten_hjelpemidler);
		noAidButton.setOnCheckedChangeListener(this);
		radBtns.add(noAidButton);
		crutchesButton = (RadioButton)rootView.findViewById(R.id.krykker);
		crutchesButton.setOnCheckedChangeListener(this);
		radBtns.add(crutchesButton);
		rollatorButton = (RadioButton)rootView.findViewById(R.id.rollator);
		rollatorButton.setOnCheckedChangeListener(this);
		radBtns.add(rollatorButton);
		otherButton = (RadioButton)rootView.findViewById(R.id.annet);
		otherButton.setOnCheckedChangeListener(this);
		radBtns.add(otherButton);
		otherEdit=(EditText)rootView.findViewById(R.id.annet_edit);
		resetAids();

		startTest.setText("Start test "+testCounter);
		t = new Timer();

		circle = (ImageView) rootView.findViewById(R.id.circle);

		final TextView start = (TextView) rootView.findViewById(R.id.start);


		ImageView info = (ImageView)rootView.findViewById(R.id.gangtest_info);
		info.setClickable(true);
		info.setEnabled(true);

		next.setEnabled(false);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment fragment = new SPPBWalkingResultFragment(test1, test2);
				getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("walking").commit();

			}
		});
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


		circle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);	
				anim.setDuration(10000);

				startTest.setText("Start test "+testCounter);
				if(isTime == false){
					circle.startAnimation(anim);
					start.setText("Stopp");
					testCounter++;

					t = new Timer();
					t.scheduleAtFixedRate(new TimerTask() {

						@Override
						public void run() {
						
							getActivity().runOnUiThread(new Runnable() {
								public void run() {
									minSec = ((int)TimeCounter/10) + ":" + TimeCounter%10;
									time.setText(minSec); // you can set it to a textView to show it to the user to see the time passing while he is writing.
									TimeCounter++;
								}
							});
						}
					}, 100, 100);

					isTime = true;
				}else if(isTime){
					circle.clearAnimation();
					results();
					start.setText("Start");
					try {
						t.cancel();

					} catch (Exception e) {
						e.printStackTrace();
					}

					circle.clearAnimation();
					isTime = false;
				}
			}
		});

		return rootView;
	}
	public void accept(ImageView acceptImage){
		if(TimeCounter/10>10){
			acceptImage.setVisibility(0x00000004);
		}
		else{
			acceptImage.setVisibility(0x00000000);
		}}
	public void results(){
		if(testCounter==2){
			result1.setText("1. "+minSec);
			
			try{
				String name = "Gangtest";
				int patientID = patient.getId();
				double parsedTime = Double.parseDouble(minSec.replace(":", "."));
				Date date = new Date();
				
			test1 = new WalkingSPPB(name,patientID , date, parsedTime, noAidButton.isChecked(), crutchesButton.isChecked(), rollatorButton.isChecked(), getOtherText());
			resetAids();
			accept(resultAccept1);
			TimeCounter = 0;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		if(testCounter==3){
			result2.setText("2. "+minSec);
			accept(resultAccept2);
			startTest.setText("ferdig");
			
			//time = Double.parseDouble(minSec.replace(":", "."));
			
			test2 = new WalkingSPPB("Gangtest", patient.getId(), new Date(),Double.parseDouble(minSec.replace(":", ".")), noAidButton.isChecked(), crutchesButton.isChecked(), rollatorButton.isChecked(), getOtherText());		
		}
		if(testCounter>2){
			circle.setEnabled(false);
			next.setEnabled(true);
		}
			
	}
	public String aid(){
		
		if(noAidButton.isChecked()){
			return "uten";
		}
		else if(crutchesButton.isChecked()){
			return "krykke/stokk(er)";
		}
		else if(rollatorButton.isChecked()){
			return "rollator";
		}
		else if(otherButton.isChecked()){
			return otherEdit.getText().toString();
			
		}
		return null;
	}
	private String getOtherText(){
		if(otherButton.isChecked()){
			return otherEdit.getText().toString();
		}
		else return "";
	}
	private void resetAids(){
		for (RadioButton radBtn : radBtns) {
			radBtn.setChecked(false);
		}
		noAidButton.setChecked(true);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (RadioButton radBtn : radBtns) {
			radBtn.setChecked(false);
		}
		buttonView.setChecked(isChecked);
	}
}



