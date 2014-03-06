package no.ntnu.stud.ubilearn.fragments.wiki;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.R.layout;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ArticleFragment extends Fragment {
	
	View root;
	View articleTitleView; 
	View articleTextView;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Bundle b = getArguments();
		
		root = inflater.inflate(R.layout.fragment_article, null);
		articleTitleView = root.findViewById(R.id.articleTitle);
		articleTextView = root.findViewById(R.id.articleText);
		
		
		
		
		return root;
	}

}
