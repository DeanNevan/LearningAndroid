package com.example.learningandroid.booklist;

import android.graphics.drawable.Drawable;

import com.example.learningandroid.R;

public class Book {

    private int coverResourceID;
    private String title;

    public Book(String title, int coverResourceID){
        this.title = title;
        this.coverResourceID = coverResourceID;
    }

    public int getCoverResourceID(){
        return coverResourceID;
    }

    public String getTitle() {
        return title;
    }


}
