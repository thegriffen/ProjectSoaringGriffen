package com.thegriffen.projectsoaringgriffen;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thegriffen.projectsoaringgriffen.utils.DatabaseHandler;
import com.thegriffen.projectsoaringgriffen.utils.Entry;
import com.thegriffen.projectsoaringgriffen.utils.TimeCalculator;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideActionBar();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);	
		TextView mHours = (TextView)findViewById(R.id.hours);
		mHours.setText(getHours());	
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void hideActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		    ActionBar mActionBar = getActionBar();
		    mActionBar.hide();
		}
	}
	
	private String getHours() {
		DatabaseHandler db = new DatabaseHandler(this);
		List<Entry> entries = new ArrayList<Entry>();
		entries = db.getAllEntries();
		int totalHours = 0;
		int totalMinutes = 0;
		for(Entry entry : entries) {
			totalHours += new TimeCalculator(entry.getStartTime(), entry.getEndTime()).getHours();
			totalMinutes += new TimeCalculator(entry.getStartTime(), entry.getEndTime()).getMinutes();
		}
		totalHours += totalMinutes/60;
		return Integer.toString(totalHours);
	}
	
	public void gotoList(View view) {
		Intent intent = new Intent(this, ListViewActivity.class);
		startActivity(intent);
	}

}
