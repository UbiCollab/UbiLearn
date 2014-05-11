package no.ntnu.stud.ubilearn.models;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import no.ntnu.stud.ubilearn.R;

public class Exercise extends ListItem{
	private String objectId;
	private String name;
	private String text;
	private ArrayList<ExerciseImage> images;
	private Date createdAt;
	
	
	public Exercise(String objectId, String name, String text,Date createdAt) {
		super();
		this.objectId = objectId;
		this.name = name;
		this.text = text;
		this.createdAt = createdAt;
	}
	public Date getCreatedAt(){
		return createdAt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getObjectId() {
		return objectId;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
		
	}
	@Override
	protected void setIcon() {
		this.icon = R.drawable.ic_navigation_next_item;
		
	}
	@Override
	public String printContent() {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<ExerciseImage> getImages() {
		return images;
	}
	public void setImages(ArrayList<ExerciseImage> images){
		this.images = images;
	}
	public ArrayList<byte[]> getImagesAsBytes() {
		ArrayList<byte[]> imagesAsBytes = new ArrayList<byte[]>();
		for (ExerciseImage image : images) {
			imagesAsBytes.add(image.getBytes());
		}
		return imagesAsBytes;
	}

	private byte[] pngToByteArray(String name){
		
			Bitmap bmp = BitmapFactory.decodeFile(name);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			
			return byteArray;
	}

}
