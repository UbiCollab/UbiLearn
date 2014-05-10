package no.ntnu.stud.ubilearn.fragments.practise;

import no.ntnu.stud.ubilearn.ImageDialog;
import no.ntnu.stud.ubilearn.MainActivity;
import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.adapter.GalleryImageAdapter;
import no.ntnu.stud.ubilearn.models.Exercise;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExcerciseFragment extends Fragment {
	
	ImageView selectedImage;
	TextView description;
	TextView name;
	private Exercise exercise;
	
    private Integer[] mImageIds = {
    		R.drawable.exercise1,
    		R.drawable.exercise2,
    		R.drawable.exercise3,
    		R.drawable.exercise4
    };
	
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
			View view = inflater.inflate(R.layout.fragment_practise_excercise, container, false);
	       
	        Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
	        selectedImage = (ImageView) view.findViewById(R.id.imageView1);
	        gallery.setSpacing(1);
	        gallery.setAdapter(new GalleryImageAdapter(getActivity(),exercise.getImages()));

	         // clicklistener for Gallery
	        gallery.setOnItemClickListener(new OnItemClickListener() {
	        	
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            	
	                Toast.makeText(getActivity(), "Your selected position = " + position, Toast.LENGTH_SHORT).show();
	                // show the selected Image
	                Intent myIntent = new Intent(getActivity(), ImageDialog.class);
	                Bundle data = new Bundle();
//	                data.putInt("image", mImageIds[position]);
	                data.putByteArray("image", exercise.getImages().get(position));
	                myIntent.putExtra("image", data);
	                startActivity(myIntent);
	            }
	        });
	        
	        description = (TextView) view.findViewById(R.id.description);
//	        description.setText(R.string.fillText);
	        description.setText(exercise.getText());
	        
	        name = (TextView) view.findViewById(R.id.name);
//	        name.setText("Lorem Ipsum");
	        name.setText(exercise.getName());
	        
	        return view;
	    }
	 public void setExercise(Exercise exercise){
		 this.exercise = exercise;
	 }

}
