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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class WikiFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private String[] categories;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		categories= getResources().getStringArray(R.array.menu_options);
		
		root = inflater.inflate(R.layout.fragment_wiki_fragment, null);
		categoryListView = (ListView) root.findViewById(R.id.wikiListView);
		categoryListView.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.drawer_list_item, categories));
		
		return root;
	}
}
