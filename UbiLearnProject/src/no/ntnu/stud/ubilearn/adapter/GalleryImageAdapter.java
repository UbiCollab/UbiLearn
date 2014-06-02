package no.ntnu.stud.ubilearn.adapter;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import no.ntnu.stud.ubilearn.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
/**
 * this is an adapter that handles the showing of imagethumbs in a listview. The litview will use this adaper to display the images.
 *
 */
public class GalleryImageAdapter extends BaseAdapter 
{
    private Context mContext;

    private ArrayList<byte[]> images;

    public GalleryImageAdapter(Context context, ArrayList<byte[]> images) 
    {
        mContext = context;
        this.images = images;
    }
    @Override
    public int getCount() {
    	return images.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
        ImageView i = new ImageView(mContext);
        i.setImageBitmap(BitmapFactory.decodeByteArray(images.get(index), 0, images.get(index).length));
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
        i.setPadding(10, 10, 10, 10);
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}
