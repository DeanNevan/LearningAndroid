package com.example.learningandroid.booklist.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.activity.EditBookActivity;
import com.example.learningandroid.booklist.adapters.BookListAdapter;
import com.example.learningandroid.booklist.pojo.Book;
import com.example.learningandroid.booklist.pojo.BooksWrapper;
import com.example.learningandroid.booklist.util.LocalDataWR;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentBookList extends Fragment {

    private List<Book> books = new ArrayList<>();
    private BookListAdapter bookListAdapter;
    private RecyclerView booksRecyclerView;

    int selectedBookPosition = -1;

    ActivityResultLauncher<Intent> editBookActivityLauncher;
    ActivityResultLauncher<Intent> addBookActivityLauncher;

    private String booksLocalSaveFileName = "books.db";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container);

        bookListAdapter = new BookListAdapter(getContext(), books);
        booksRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        booksRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        booksRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        booksRecyclerView.setAdapter(bookListAdapter);
        booksRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //读取书本数据
        try {
            loadBooksFromLocal();
            updateBooksListAndSaveToLocal();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        if (books.size() == 0){
            //生成书本数据
            books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
            books.add(new Book("创新工程实践", R.drawable.book_no_name));
            books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
            updateBooksListAndSaveToLocal();
        }

        editBookActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    int bookID = result.getData().getIntExtra("book_id", -1);
                    String bookTitle = result.getData().getStringExtra("book_title");
                    getListBooks().get(bookID).setTitle(bookTitle);
                    updateBooksListAndSaveToLocal();
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
                    updateBooksListAndSaveToLocal();
                }
                else if (result.getResultCode() == RESULT_CANCELED){
                    //nothing
                }
            }
        });

        return view;
    }

    public void saveToLocal(String fileName, byte[] data) {
        try{
            FileOutputStream fout = getActivity().openFileOutput(fileName, MODE_PRIVATE);
            fout.write(data);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updateBooksList(){
        booksRecyclerView.setAdapter(bookListAdapter);
    }

    private void updateBooksListAndSaveToLocal(){
        updateBooksList();
        saveBooksToLocal();
    }

    private BooksWrapper.Books serializeBooksToProtobufBytes(){
        BooksWrapper.Books.Builder builder = BooksWrapper.Books.newBuilder();
        for (Book book : books){
            builder.addBooks(book.toProtobufBook());
        }
        return builder.build();
    }

    private void unserializeBooksFromProtobufBytes(BooksWrapper.Books booksProtobuf){
        books.clear();
        for (BooksWrapper.Book bookProtobuf : booksProtobuf.getBooksList()){
            Book book = new Book();
            book.fromProtobufBook(bookProtobuf);
            books.add(book);
        }
    }

    public void saveBooksToLocal() {
        BooksWrapper.Books booksProtobuf = serializeBooksToProtobufBytes();
        Log.d("存储bytes", Arrays.toString(booksProtobuf.toByteArray()));
        LocalDataWR.saveData(getActivity(), booksLocalSaveFileName, booksProtobuf.toByteArray());
    }

    public void loadBooksFromLocal() throws InvalidProtocolBufferException {
        byte[] booksData = LocalDataWR.loadData(getActivity(), booksLocalSaveFileName);
        if (booksData != null){
            BooksWrapper.Books booksProtobuf = BooksWrapper.Books.parseFrom(booksData);
            unserializeBooksFromProtobufBytes(booksProtobuf);
        }
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
                updateBooksListAndSaveToLocal();
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
        Intent intent = new Intent(getActivity(), EditBookActivity.class);
        int bookID = bookListAdapter.getContextMenuPosition();
        String bookTitle = getListBooks().get(bookID).getTitle();
        intent.putExtra("book_id", bookID);
        intent.putExtra("book_title", bookTitle);
        intent.putExtra("is_edit", true);
        editBookActivityLauncher.launch(intent);
    }

    private void activateAddBook(){
        Intent intent = new Intent(getActivity(), EditBookActivity.class);
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
