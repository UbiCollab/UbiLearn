package no.ntnu.stud.ubilearn.fragments;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.R.layout;
import no.ntnu.stud.ubilearn.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class WikiFragment extends Fragment {

	private RelativeLayout rl;
	private View root;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		root = inflater.inflate(R.layout.fragment_wiki_fragment, null);
		//rl = (RelativeLayout) root.findViewById(R.id.training_rel);
		
		
		return root;
	}

}
