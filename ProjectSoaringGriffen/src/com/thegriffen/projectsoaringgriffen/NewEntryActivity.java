package com.thegriffen.projectsoaringgriffen;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.thegriffen.projectsoaringgriffen.utils.DatabaseHandler;
import com.thegriffen.projectsoaringgriffen.utils.Entry;
import com.thegriffen.projectsoaringgriffen.utils.ResizeBitmap;

public class NewEntryActivity extends ActionBarActivity {
	final Context context = this;
	Dialog picker;
	EditText startTimeSelect, endTimeSelect;
	ImageView iView;
	Button set;
	TimePicker timep;
	DatePicker datep;
	int startHour, startMinute, endHour, endMinute, month, day, year;
	int image;
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry);
		setTitle("New Entry");
				
		iView = (ImageView)findViewById(R.id.image);
		Intent intent = getIntent();
		image = intent.getIntExtra("com.thegriffen.projectsoaringgriffen.imgID", 0);
		if (image == 0) {
			iView.setImageResource(R.drawable.smileyface);
		}
		else {
			File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/Senior Project Images");    
			myDir.mkdirs();
			File file = new File(myDir, "Image-"+ image +".jpg");
			if (file.exists()) {
				try {
					iView.setImageBitmap(ResizeBitmap.resizeAndDecode(file));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				iView.setImageResource(R.drawable.smileyface);
			}
		}
		iView.setClickable(true);
		iView.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CustomCameraActivity.class);
				startActivity(intent);
			}
		});
		
		startTimeSelect = (EditText)findViewById(R.id.txtStartTime);
		
        startTimeSelect.setOnClickListener(new View.OnClickListener() {        	 
            @Override
            public void onClick(View view) {
                picker = new Dialog(NewEntryActivity.this);
                picker.setContentView(R.layout.picker_frag);
                picker.setTitle("Select Date and Time");
 
                datep = (DatePicker)picker.findViewById(R.id.datePicker);
                timep = (TimePicker)picker.findViewById(R.id.timePicker1);
                set = (Button)picker.findViewById(R.id.btnSet);
 
                set.setOnClickListener(new View.OnClickListener() { 
                    @Override
                    public void onClick(View view) {
                    	timep.clearFocus();
                    	datep.clearFocus();
                        month = datep.getMonth() + 1;
                        day = datep.getDayOfMonth();
                        year = datep.getYear();
                        startHour = timep.getCurrentHour();
                        startMinute = timep.getCurrentMinute();
                        
                        startTimeSelect.setText(dateFormat(month, day, year) + "  " + timeFormat(startHour, startMinute));
                        picker.dismiss();
                    }
                });
                picker.show(); 
            }
        }); 
        
		endTimeSelect = (EditText)findViewById(R.id.txtEndTime);
		
        endTimeSelect.setOnClickListener(new View.OnClickListener() {        	 
            @Override
            public void onClick(View view) {
                picker = new Dialog(NewEntryActivity.this);
                picker.setContentView(R.layout.picker_frag);
                picker.setTitle("Select Date and Time");
 
                datep = (DatePicker)picker.findViewById(R.id.datePicker);
                timep = (TimePicker)picker.findViewById(R.id.timePicker1);
                set = (Button)picker.findViewById(R.id.btnSet);
 
                set.setOnClickListener(new View.OnClickListener() { 
                    @Override
                    public void onClick(View view) {
                    	timep.clearFocus();
                    	datep.clearFocus();
                        month = datep.getMonth() + 1;
                        day = datep.getDayOfMonth();
                        year = datep.getYear();
                        endHour = timep.getCurrentHour();
                        endMinute = timep.getCurrentMinute();
                        
                        endTimeSelect.setText(dateFormat(month, day, year) + "  " + timeFormat(endHour, endMinute));
                        picker.dismiss();
                    }
                });
                picker.show(); 
            }
        }); 
        
        db = new DatabaseHandler(this);
        startTimeSelect.setFocusable(false);
        startTimeSelect.setClickable(true);
        endTimeSelect.setFocusable(false);
        endTimeSelect.setClickable(true);
	}
	
	private String timeFormat(int hr, int min) {
		String s;
		String minute;
		if (min < 10) {
			minute = "0" + Integer.toString(min);
		}
		else {
			minute = Integer.toString(min);
		}
		if (hr > 12) {
			hr -= 12;
			s = Integer.toString(hr) + ":" + minute + " PM";
		}
		else {
			s = Integer.toString(hr) + ":" + minute + " AM";
		}
		return s;
	}
	
	private String dateFormat(int month, int day, int year) {
		return Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_new_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.cancel_new_item:
				cancelConfirm();
				return true;
			case R.id.accept_new_item:
				saveEvent();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}		
	}
	
	private void cancelConfirm() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder
				.setTitle("Are you sure?")
				.setMessage("Clicking yes will discard your changes!")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(context, ListViewActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();							
					}
				});
		AlertDialog alertDialog = alertBuilder.create();
		alertDialog.show();
	}
	
	private void saveEvent() {
		if (startTimeSelect.getText().toString().equals("Click To Select") || endTimeSelect.getText().toString().equals("Click To Select")) {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder
					.setTitle("Incomplete!")
					.setMessage("You didn't input all the required information!")
					.setIcon(R.drawable.ic_action_warning)
					.setCancelable(false)
					.setNeutralButton("Ok", new DialogInterface.OnClickListener() {						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			AlertDialog alertDialog = alertBuilder.create();
			alertDialog.show();
		}
		else {
			db.addEntry(new Entry(dateFormat(month, day, year), image ,startHour + ":" + startMinute, endHour + ":" + endMinute));
			Intent intent = new Intent(context, ListViewActivity.class);
			startActivity(intent);
		}
	}

}
