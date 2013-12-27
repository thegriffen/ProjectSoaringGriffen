package com.thegriffen.projectsoaringgriffen.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_NAME = "logEntryDatabase";
 
    private static final String TABLE_ENTRIES = "logEntries";
 
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_TIME = "endTime";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_IMAGE + " INTEGER," + KEY_START_TIME + " TEXT," + KEY_END_TIME + " TEXT" + ")";
        db.execSQL(CREATE_ENTRIES_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
 
        onCreate(db);
    }
    
    
    
    
    
    
    public void addEntry(Entry entry) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_DATE, entry.getDate());
    	values.put(KEY_IMAGE, entry.getImage());
    	values.put(KEY_START_TIME, entry.getStartTime());
    	values.put(KEY_END_TIME, entry.getEndTime());
    	db.insert(TABLE_ENTRIES, null, values);
    	db.close();
    }
    
    public Entry getEntry(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_ENTRIES, new String[] { KEY_ID,  KEY_DATE,  KEY_IMAGE, KEY_START_TIME, KEY_END_TIME }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
    	if (cursor != null) {
    		cursor.moveToFirst();
    	}
    	
    	Entry entry = new Entry(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
    	cursor.close();
    	db.close();
    	return entry;
    }
    
    public List<Entry> getAllEntries() {
    	List<Entry> entryList = new ArrayList<Entry>();
    	
    	String selectQuery = "SELECT  * FROM " + TABLE_ENTRIES;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	
    	if (cursor.moveToFirst()) {
    		do {
    			Entry entry = new Entry();
    			entry.setID(cursor.getInt(0));
    			entry.setDate(cursor.getString(1));
    			entry.setImage(cursor.getInt(2));
    			entry.setStartTime(cursor.getString(3));
    			entry.setEndTime(cursor.getString(4));
    			entryList.add(entry);
    		} while (cursor.moveToNext());
    	}
    	cursor.close();
    	db.close();
    	return entryList;
    }
    
    public int getEntriesCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_ENTRIES;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(countQuery, null);
    	int count = cursor.getCount();
    	cursor.close();
    	db.close();
    	return count;
    }
    
    public int updateEntry(Entry entry) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_DATE, entry.getDate());
    	values.put(KEY_IMAGE, entry.getImage());
    	values.put(KEY_START_TIME, entry.getStartTime());
    	values.put(KEY_END_TIME, entry.getEndTime());
    	int i = db.update(TABLE_ENTRIES, values, KEY_ID + " = ?", new String[] { String.valueOf(entry.getID()) });
    	db.close();
    	return i;
    }
    
    public void deleteEntry(int id) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_ENTRIES, KEY_ID + " = ?", new String[] { String.valueOf(id) });
    	db.close();
    }
}
