package com.thegriffen.projectsoaringgriffen;
 
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thegriffen.projectsoaringgriffen.utils.CustomAdapter;
import com.thegriffen.projectsoaringgriffen.utils.DatabaseHandler;
import com.thegriffen.projectsoaringgriffen.utils.Entry;
import com.thegriffen.projectsoaringgriffen.utils.TimeCalculator;
 
public class ListViewActivity extends ActionBarActivity {
    ListView list;
    List<String> dateArray;
    List<Integer> imageArray;
    List<Integer> idArray;
    List<String> timeArray;
    DatabaseHandler db;
    CustomAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        final Context context = this;
 
        dateArray = new ArrayList<String>();
        imageArray = new ArrayList<Integer>();
        timeArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();
        db = new DatabaseHandler(this);

        adapter = new CustomAdapter(ListViewActivity.this, dateArray, imageArray, timeArray);  
        getListData();
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 	        	Intent intent = new Intent(context, ViewEntryActivity.class);
 	        	intent.putExtra("entryId", idArray.get(position));
 	        	startActivity(intent); 
	        }
        });

		setTitle("My Log");
     }
    
    private void getListData() {
        List<Entry> entries = db.getAllEntries(); 
        dateArray.clear();
        imageArray.clear();
        timeArray.clear();
        idArray.clear();
        for (Entry entry : entries) {
        	idArray.add(entry.getID());
        	dateArray.add(entry.getDate());
        	imageArray.add(entry.getImage());
        	timeArray.add(new TimeCalculator(entry.getStartTime(), entry.getEndTime()).calculate());
        }
        adapter.notifyDataSetChanged();   	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.new_item:
				newEntry();
				return true;				 
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}
	
	private void newEntry() {
		Intent intent = new Intent(this, NewEntryActivity.class);
		startActivity(intent);
	}
 
}