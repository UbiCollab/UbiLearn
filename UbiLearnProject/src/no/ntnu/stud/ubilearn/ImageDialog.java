package no.ntnu.stud.ubilearn;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class ImageDialog extends Activity {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dialog_layout);


        Bundle data = this.getIntent().getBundleExtra("image");
        mDialog = (ImageView)findViewById(R.id.image);
        mDialog.setImageResource(data.getInt("image"));
//        mDialog.setImageBitmap(BitmapFactory.decodeByteArray(data.getByteArray("image"),0,data.getByteArray("image").length));
        mDialog.setClickable(true);


        //finish the activity (dismiss the image dialog) if the user clicks 
        //anywhere on the image
        mDialog.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
        });

    }
}