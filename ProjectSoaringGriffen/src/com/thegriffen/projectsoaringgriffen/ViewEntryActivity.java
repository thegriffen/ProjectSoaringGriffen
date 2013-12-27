package com.thegriffen.projectsoaringgriffen;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.thegriffen.projectsoaringgriffen.utils.DatabaseHandler;
import com.thegriffen.projectsoaringgriffen.utils.Entry;
import com.thegriffen.projectsoaringgriffen.utils.ResizeBitmap;

public class ViewEntryActivity extends ActionBarActivity {
	DatabaseHandler db = new DatabaseHandler(this);
	int entryId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_entry);
		Intent intent = getIntent();
		entryId = intent.getIntExtra("entryId", 0);
		Entry entry = db.getEntry(entryId);
		TextView tView = (TextView)findViewById(R.id.text_test);
		ImageView iView = (ImageView)findViewById(R.id.view_image);
		tView.setText("Date: " + entry.getDate() + "\nStart Time: " + entry.getStartTime() + "\nEnd Time: " + entry.getEndTime());
		File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/Senior Project Images");    
		myDir.mkdirs();
		File file = new File(myDir, "Image-"+ entry.getImage() +".jpg");
		iView.setImageBitmap(ResizeBitmap.resizeAndDecode(file));
		setTitle("Edit Entry");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_edit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.delete_item:
				db.deleteEntry(entryId);
				Intent intent = new Intent(this, ListViewActivity.class);
				startActivity(intent);
				return true;				 
		}
		return super.onOptionsItemSelected(item);
	}

}
