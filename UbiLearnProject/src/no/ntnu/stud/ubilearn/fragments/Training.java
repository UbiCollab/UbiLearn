package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.R.drawable;
import no.ntnu.stud.ubilearn.R.id;
import no.ntnu.stud.ubilearn.R.layout;
import android.os.Bundle;
import android.app.Dialog;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;

public class Training extends Fragment {
	private RelativeLayout rl;
	private ScrollView sv;
	private View root;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		root = inflater.inflate(R.layout.fragment_training, null);
		sv = (ScrollView) root.findViewById(R.id.training_scroll);
		rl = (RelativeLayout) root.findViewById(R.id.training_rel);
		
		sv.setScrollX(sv.getMaxScrollAmount());
		return root;
	}
	public void houseClick(View v){
		final View house = v;
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.training_popup);
		
		Button cancel = (Button) dialog.findViewById(R.id.training_popup_cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					dialog.dismiss();
			}
		});
		
		Button enter = (Button) dialog.findViewById(R.id.training_popup_enter);
		
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View vi) {
				Bundle data = new Bundle();
				String name = "Test";
				String age = "1337";
				String gender = "Female";
				String info = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.";
				
				if(house.getContentDescription().toString().length() > 0 ){
					int c = Integer.parseInt(house.getContentDescription().toString());
					switch (c) {
					case 1:
						name = "Tore";
						age = "52";
						gender = "Male";
						break;
					case 2:
						name = "Trine";
						age = "32";
						gender = "Female";
						break;
					case 3:
						name = "Tim";
						age = "62";
						gender = "Male";
						break;
					case 4:
						name = "Magda";
						age = "47";
						gender = "Female";
						break;
					case 5:
						name = "Ola";
						age = "72";
						gender = "Male";
						break;
					case 6:
						name = "Eirik";
						age = "22";
						gender = "Male";
						break;
					case 7:
						name = "Ingeborg";
						age = "22";
						gender = "Female";
						break;
					case 8:
						name = "Henrik";
						age = "52";
						gender = "Male";
						break;
					case 9:
						name = "Eva";
						age = "92";
						gender = "Female";
						break;
					case 10:
						name = "Even";
						age = "42";
						gender = "Male";
						break;
					case 11:
						name = "Tone";
						age = "78";
						gender = "Female";
						break;
					case 12:
						name = "Glenn";
						age = "74";
						gender = "Male";
						break;

					default:
						break;
					}
				}
				
				data.putString("name", name);
				data.putString("age", age);
				data.putString("gender", gender);
				data.putString("info", info);
				Fragment patient = new PatientCaseFragment();
				patient.setArguments(data);
				
				getFragmentManager().beginTransaction().replace(R.id.content_frame, patient).commit();
				dialog.dismiss();
			}
		});
		
		dialog.setTitle("Tore sitt hus");
		dialog.show();
	}
	

}
