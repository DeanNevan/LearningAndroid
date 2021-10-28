package com.example.learningandroid.booklist;

import android.os.Bundle;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.adapters.BookListAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.databinding.ActivityBookListMainBinding;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    private List<Book> books = new ArrayList<>();
    private BookListAdapter bookListAdapter;
    private RecyclerView booksRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        //生成书本数据
        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

        bookListAdapter = new BookListAdapter(this, books);
        booksRecyclerView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        booksRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        booksRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        booksRecyclerView.setAdapter(bookListAdapter);
        booksRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public List<Book> getListBooks(){
        return books;
    }

}