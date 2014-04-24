package no.ntnu.stud.ubilearn.fragments.wiki;

import no.ntnu.stud.ubilearn.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class ArticleFragment extends Fragment {
	
	View root;
	private TextView articleTitleView; 
	private TextView articleTextView;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Bundle b = getArguments();
		
		root = inflater.inflate(R.layout.fragment_article, null);
		articleTitleView = (TextView) root.findViewById(R.id.articleTitle);
		articleTextView = (TextView) root.findViewById(R.id.articleText);

		return root;
	}

	public void setTitle(String title){
		articleTextView.setText(title);
	}
}
