package com.example.learningandroid.booklist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningandroid.R;

public class EditBookActivity extends AppCompatActivity {
    EditText editTextBookTitle;
    Button buttonConfirm;
    Button buttonCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextBookTitle = findViewById(R.id.editTextBookTitle);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonCancel = findViewById(R.id.buttonCancel);

        int bookID = getIntent().getIntExtra("book_id", -1);
        String bookTitle = getIntent().getStringExtra("book_title");

        editTextBookTitle.setText(bookTitle);

        buttonConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(EditBookActivity.this, BookListMainActivity.class);
            intent
                    .putExtra("book_title", editTextBookTitle.getText().toString())
                    .putExtra("book_id", bookID);
            setResult(RESULT_OK, intent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(EditBookActivity.this, BookListMainActivity.class);
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        Log.d("test", "aopishfoiaxhcoiahoisdhja");
        super.onDestroy();
    }
}
