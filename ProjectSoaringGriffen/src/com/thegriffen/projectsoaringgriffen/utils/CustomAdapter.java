package com.thegriffen.projectsoaringgriffen.utils;
 
import java.io.File;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thegriffen.projectsoaringgriffen.R;
 
public class CustomAdapter extends ArrayAdapter<String>{
 
	private final Activity context;
	private final List<String> date;
	private final List<Integer> image;
	private final List<String> time;
	public CustomAdapter(Activity context, List<String> date, List<Integer> image, List<String> time) {
		super(context, R.layout.list_row, date);
		this.context = context;
		this.date = date;
		this.image = image; 
		this.time = time;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_row, null, true);
		TextView txtTitle = (TextView)rowView.findViewById(R.id.date); 
		TextView txtTime = (TextView)rowView.findViewById(R.id.time);
		ImageView imageView = (ImageView)rowView.findViewById(R.id.img);
		txtTitle.setText(date.get(position)); 
		txtTime.setText(time.get(position));
		File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/Senior Project Images");    
		myDir.mkdirs();
		File file = new File(myDir, "Image-"+ image.get(position) +".jpg");
		if (file.exists()) {
			Bitmap bmp = ResizeBitmap.resizeAndDecode(file);
			imageView.setImageBitmap(bmp);
		}
		else {
			imageView.setImageResource(R.drawable.smileyface);
		}
		return rowView;
	}
	
}