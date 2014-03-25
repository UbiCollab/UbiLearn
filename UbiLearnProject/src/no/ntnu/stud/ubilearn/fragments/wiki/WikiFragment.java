package no.ntnu.stud.ubilearn.fragments.wiki;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.parse.SyncContent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class WikiFragment extends Fragment {
	private View root;
	private ListView categoryListView;
	private ArrayList<WikiItem> listItems;
	private Fragment pointerHax = this;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		generateTestData();
		root = inflater.inflate(R.layout.fragment_wiki, null);
		categoryListView = (ListView) root.findViewById(R.id.wikiListView);
		categoryListView.setAdapter(new WikiItemAdapter(this.getActivity(), listItems));
		
		categoryListView.setClickable(true);
		categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
			
				if (listItems.get(position) instanceof Catagory) {
					Catagory cat = (Catagory) listItems.get(position);
					if (cat.hasSubs()) {
						WikiFragment handbook = new WikiFragment();
						handbook.setListItems(cat.getSub());
						getFragmentManager().beginTransaction().replace(R.id.content_frame, handbook).addToBackStack(null).commit();
					}
				}else{
					Fragment article = new ArticleFragment();
					getFragmentManager().beginTransaction().replace(R.id.content_frame, article).addToBackStack(null).commit();
				}	
			}	
		});
		return root;
	}
	
	
	public void setListItems(ArrayList<WikiItem> listItems) {
		this.listItems = listItems;
	}


	public void generateTestData(){
		if (listItems == null) {
			ArrayList<WikiItem> sub = new ArrayList<WikiItem>();
//			sub.add(new Catagory("Sub-category 1", null));
//			sub.add(new Catagory("Sub-category 2", null));
//			sub.add(new Catagory("Sub-category 3", null));
//			sub.add(new Catagory("Sub-category 4", null));
			sub.add(new Article("Article 1", getResources().getString(
					R.string.gibberish)));
			sub.add(new Article("Article 2", getResources().getString(
					R.string.gibberish)));
			sub.add(new Article("Article 3",
					getResources().getString(R.string.gibberish)));
			listItems = new ArrayList<WikiItem>();
			listItems.add(new Catagory("Category 1", sub));
			listItems.add(new Catagory("Category 2", sub));
			listItems.add(new Catagory("Category 3", sub));
			listItems.add(new Catagory("Category 4", sub));
			listItems.add(new Catagory("Category 5", sub));
			listItems.add(new Catagory("Category 6", sub));
			listItems.add(new Catagory("Category 7", sub));
			listItems.add(new Catagory("Category 8", sub));
			listItems.add(new Catagory("Category 9", sub));
			listItems.add(new Catagory("Category 10", sub));
		}
	}
}
