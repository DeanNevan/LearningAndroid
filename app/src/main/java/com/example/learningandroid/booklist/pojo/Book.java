package com.example.learningandroid.booklist.pojo;

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

    public void setTitle(String title) {
        this.title = title;
    }
}
