package com.example.learningandroid.booklist.pojo;

import com.example.learningandroid.R;

public class Book {

    private int coverResourceID;
    private String title;

    public Book(String title, int coverResourceID){
        this.title = title;
        this.coverResourceID = coverResourceID;
    }

    public Book(){
        this.title = "";
        this.coverResourceID = R.drawable.book_no_name;
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

    public BooksWrapper.Book toProtobufBook(){
        BooksWrapper.Book.Builder builder = BooksWrapper.Book.newBuilder();
        builder
                .setTitle(title)
                .setCoverResourceId(coverResourceID);
        return builder.build();
    }

    public void fromProtobufBook(BooksWrapper.Book protobufBook){
        this.title = protobufBook.getTitle();
        this.coverResourceID = protobufBook.getCoverResourceId();
    }
}
