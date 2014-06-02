package no.ntnu.stud.ubilearn.fragments.handbook;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.models.Article;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * This class is used to represent a {@link Article} thru a view.
 *@see View
 *@see TextView
 *@see Article
 */
public class ArticleFragment extends Fragment {
	
	View root;
	private TextView articleTitleView; 
	private TextView articleTextView;
	private Article article;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Bundle b = getArguments();
		
		root = inflater.inflate(R.layout.fragment_article, null);
		articleTitleView = (TextView) root.findViewById(R.id.articleTitle);
		articleTextView = (TextView) root.findViewById(R.id.articleText);
		if (article != null) {
			articleTitleView.setText(article.getTitle());
			articleTextView.setText(article.getContent());
		}

		return root;
	}
	
	/**
	 * Sets the Article to be used. This article is used in onCreateView(),
	 * and should therefore be called before onCreateView().
	 * @param article
	 * @see Article
	 */
	public void setArticle(Article article){
		this.article = article;
	}
}
