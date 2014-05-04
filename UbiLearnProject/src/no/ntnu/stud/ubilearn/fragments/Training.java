package no.ntnu.stud.ubilearn.fragments;

import java.util.ArrayList;

import no.ntnu.stud.ubilearn.R;
import no.ntnu.stud.ubilearn.User;
import no.ntnu.stud.ubilearn.db.TrainingDAO;
import no.ntnu.stud.ubilearn.models.CasePatient;
import android.os.Bundle;
import android.app.Dialog;
import android.app.Fragment;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;




public class Training extends Fragment {
	private RelativeLayout rl;
	private ScrollView sv;
	private View root;
	private View root2;
	ImageView nextLevel;
	ImageView backLevel;
	TrainingDAO dao;
	boolean levelComplete = false;


	ArrayList<CasePatient> patientList;
	private int i;
	private int level = 1; //må endres til å bli hentet startlevelen fra databasen

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){

		//		TrainingDAO dao = new TrainingDAO(getActivity());
		//		dao.open();
		//		dao.printTables();
		//		Log.d("Training Fragment", "Number of quizzes: "+dao.getNofQuizzes(2));
		//		dao.close();

		patientList = User.getInstance().getPatientList();
		if(patientList == null){
			Toast.makeText(getActivity(), "lista er tom", Toast.LENGTH_SHORT).show();
		}

		root = inflater.inflate(R.layout.fragment_training, null);
		sv = (ScrollView) root.findViewById(R.id.training_scroll);
		rl = (RelativeLayout) root.findViewById(R.id.training_rel);

		backLevel = (ImageView)root.findViewById(R.id.back_level);
		nextLevel = (ImageView)root.findViewById(R.id.enter_level);

		levelController(root);

		return root;
	}

	public void houseClick(View v){
		final View house = v;
		Log.v("husnummer: "+house.getContentDescription(), "");
		setCarPositionY(house.getY() + house.getWidth()/2);
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.training_popup);
		if(house.getContentDescription().toString().length() > 0 ){
			i = Integer.parseInt(house.getContentDescription().toString())+((level - 1)*11);
		}

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

				Fragment patient = new PatientCaseFragment(patientList.get(i));

				Bundle data = new Bundle();

				getFragmentManager().beginTransaction().replace(R.id.content_frame, patient).addToBackStack("training").commit();
				dialog.dismiss();
			}
		});

		try{
			dialog.setTitle("Her bor " +patientList.get(i).getName());
			dialog.show();
		}
		catch(Exception e){
			Toast.makeText(getActivity(), "ingen hjemme", Toast.LENGTH_SHORT).show();
		}
	}


	private void setCarPositionY(float y){
		ImageView car = (ImageView) root.findViewById(R.id.traningCar);
		System.out.println("works?");
		if (car == null) {
			System.out.println("thats why");
		}
		car.setY(y);
	}
	public void levelController(View v){

		Toast.makeText(getActivity(), "Level"+ level+ " " + "Poeng"  + User.getInstance().getPoints(), Toast.LENGTH_SHORT).show();
		if(User.getInstance().getPoints()==5*level){
			Toast.makeText(getActivity(), "Congratulations, you are now allowed access to level "+(this.level+1), Toast.LENGTH_SHORT).show();

		}
		nextLevel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(User.getInstance().getQuizLevel()>=level+1){
					level++;
					setLevelImage(level);
					Toast.makeText(getActivity(), "Du er nå i level "+ level, Toast.LENGTH_SHORT).show();

				}
				else{
					Log.v("CurrentLevel: ",level + "");
					Log.v("User.getInstance level", User.getInstance().getQuizLevel() + "");
					Log.v("i er :", i + "");
					
					Toast.makeText(getActivity(), "Du har ikke nok poeng til neste level", Toast.LENGTH_SHORT).show();
				}
			}
		});
		backLevel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					level--;
					setLevelImage(level);
					Toast.makeText(getActivity(), "Du er nå i level "+ level, Toast.LENGTH_SHORT).show();

			}
		});

	}

	public void setLevelImage(int level){
		switch (level) {
		case 1:
			backLevel.setImageResource(R.drawable.tillevel1);
			nextLevel.setImageResource(R.drawable.tillevel2);

			break;
		case 2:
			backLevel.setImageResource(R.drawable.level1);
			nextLevel.setImageResource(R.drawable.tillevel3);
			break;
		case 3:
			backLevel.setImageResource(R.drawable.level2);
			nextLevel.setImageResource(R.drawable.tillevel4);
			break;
		case 4:
			backLevel.setImageResource(R.drawable.level3);
			nextLevel.setImageResource(R.drawable.tillevel5);
			break;

		default:
			break;
		}
	}

}
