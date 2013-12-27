package com.thegriffen.projectsoaringgriffen.utils;


public class Entry {
    int id;
    String date;
    String startTime;
    String endTime;
    int image;
    
    public Entry() {
    }
    
    public Entry(int id, String date, int image, String startTime, String endTime) {
        this.id = id;
        this.date = date;
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Entry(String date, int image, String startTime, String endTime) {
        this.date = date;
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Entry(String date, String startTime, String endTime) {
    	this.date = date;
    	this.startTime = startTime;
    	this.endTime = endTime;
    }
    
    public Entry(int id) {
    	this.id = id;
    }
        
    public int getID() {
        return this.id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public int getImage() {
        return this.image;
    }
    
    public void setImage(int image) {
        this.image = image;
    }
    
    public String getStartTime() {
    	return this.startTime;
    }
    
    public void setStartTime(String startTime) {
    	this.startTime = startTime;
    }
    
    public String getEndTime() {
    	return this.endTime;
    }
    
    public void setEndTime(String endTime) {
    	this.endTime = endTime;
    }
}
