package com.example.learningandroid.booklist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.pojo.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {
    private List<Book> bookList;
    private Context context;
    private LayoutInflater inflater;

    public BookListAdapter(Context context, List<Book> datas){
        this.context = context;
        this.bookList = datas;
        inflater = LayoutInflater. from(context);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_book_view, parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Book book = bookList.get(position);
        Log.d("test", String.valueOf(position));

        holder.textViewTitle.setText(book.getTitle());
        holder.imageViewCover.setImageResource(book.getCoverResourceID());

//        if(position%2==0){
//            //holder.tv.setBackgroundColor(Color.BLUE);
//            holder.v.setBackgroundColor(Color.GRAY);
//
//        }
//        holder.tv.setText(book.getName());
//        holder.msg.setText(book.getZt());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView imageViewCover;
        public MyViewHolder(View view) {
            super(view);
            textViewTitle = (TextView) view.findViewById(R.id.text_view_book_title);
            imageViewCover = (ImageView) view.findViewById(R.id.image_view_book_cover);
        }
    }
}
