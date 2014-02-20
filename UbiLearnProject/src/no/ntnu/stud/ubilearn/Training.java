package no.ntnu.stud.ubilearn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Training extends Fragment {
	private RelativeLayout sv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		View root = inflater.inflate(R.layout.fragment_training, null);
		
		sv = (RelativeLayout) root.findViewById(R.id.training_scroll);
		
		ImageView img = new ImageView(getActivity());
		img.setImageResource(R.drawable.hus1);
		ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 600);
		img.setLayoutParams(layoutParams);
		sv.addView(img, 0, 0);
		
		
		return root;
	}

}
