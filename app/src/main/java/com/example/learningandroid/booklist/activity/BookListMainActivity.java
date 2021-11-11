package com.example.learningandroid.booklist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.pojo.Book;
import com.example.learningandroid.booklist.adapters.BookListAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    private List<Book> books = new ArrayList<>();
    private BookListAdapter bookListAdapter;
    private RecyclerView booksRecyclerView;

    int selectedBookPosition = -1;

    ActivityResultLauncher<Intent> editBookActivityLauncher;
    ActivityResultLauncher<Intent> addBookActivityLauncher;

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

        editBookActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    int bookID = result.getData().getIntExtra("book_id", -1);
                    String bookTitle = result.getData().getStringExtra("book_title");
                    getListBooks().get(bookID).setTitle(bookTitle);
                    booksRecyclerView.setAdapter(bookListAdapter);
                }
                else if (result.getResultCode() == RESULT_CANCELED){
                    //nothing
                }
            }
        });

        addBookActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    int bookID = result.getData().getIntExtra("book_id", -1);
                    String bookTitle = result.getData().getStringExtra("book_title");
                    getListBooks().add(bookID, new Book(bookTitle, R.drawable.book_no_name));
                    booksRecyclerView.setAdapter(bookListAdapter);
                }
                else if (result.getResultCode() == RESULT_CANCELED){
                    //nothing
                }
            }
        });

        //registerForContextMenu(booksRecyclerView);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void createMenu(Menu menu)
    {
        int groupID = 0;
        int order = 0;
        int[] itemID = {1, 2, 3};

        for(int i=0; i < itemID.length; i++)
        {
            switch(itemID[i])
            {
                case 1:
                    menu.add(groupID, itemID[i], order, "删除");
                    break;
                case 2:
                    menu.add(groupID, itemID[i], order, "编辑");
                    break;
                case 3:
                    menu.add(groupID, itemID[i], order, "新建");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Log.d("BookList", String.format("%s:%d", "删除", selectedBookPosition));
                books.remove(bookListAdapter.getContextMenuPosition());
                booksRecyclerView.setAdapter(bookListAdapter);
                break;
            case 2:
                activateEditBook();
                break;
            case 3:
                activateAddBook();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void activateEditBook(){
        Intent intent = new Intent(this, EditBookActivity.class);
        int bookID = bookListAdapter.getContextMenuPosition();
        String bookTitle = getListBooks().get(bookID).getTitle();
        intent.putExtra("book_id", bookID);
        intent.putExtra("book_title", bookTitle);
        intent.putExtra("is_edit", true);
        editBookActivityLauncher.launch(intent);
    }

    private void activateAddBook(){
        Intent intent = new Intent(this, EditBookActivity.class);
        int bookID = bookListAdapter.getContextMenuPosition();
        intent.putExtra("book_id", bookID);
        intent.putExtra("book_title", "");
        intent.putExtra("is_edit", false);
        addBookActivityLauncher.launch(intent);
    }


    public List<Book> getListBooks(){
        return books;
    }

}