package com.example.learningandroid.booklist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.activity.BookListMainActivity;
import com.example.learningandroid.booklist.pojo.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {
    private List<Book> bookList;
    private Context context;
    private LayoutInflater inflater;

    private int contextMenuPosition = -1;

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    public void setContextMenuPosition(int contextMenuPosition) {
        this.contextMenuPosition = contextMenuPosition;
    }

    public BookListAdapter(Context context, List<Book> datas){
        this.context = context;
        this.bookList = datas;
        inflater = LayoutInflater. from(context);

    }

    public Book getBookViaPosition(int pos){
        return bookList.get(pos);
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

        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setContextMenuPosition(holder.getLayoutPosition());
                return false;
            }
        });


//        if(position%2==0){
//            //holder.tv.setBackgroundColor(Color.BLUE);
//            holder.v.setBackgroundColor(Color.GRAY);
//
//        }
//        holder.tv.setText(book.getName());
//        holder.msg.setText(book.getZt());
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textViewTitle;
        ImageView imageViewCover;
        public MyViewHolder(View view) {
            super(view);
            textViewTitle = (TextView) view.findViewById(R.id.text_view_book_title);
            imageViewCover = (ImageView) view.findViewById(R.id.image_view_book_cover);
//            tv = (TextView) view.findViewById(R.id.txt_xs);
//            msg = (TextView) view.findViewById(R.id.txt_msg);
//            v = view;

            view.setLongClickable(true);
            view.setOnCreateContextMenuListener(this);
//            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                @Override
//                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                    Log.d("test", "生成上下文菜单");
//                }
//            });
//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Log.d("test", "长按了");
//                    return false;
//                }
//            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //注意传入的menuInfo为null
            Book book = bookList.get(getContextMenuPosition());
            Log.i("Adapter", "onCreateContextMenu: " + getContextMenuPosition());
            menu.setHeaderTitle(book.getTitle());
            ((BookListMainActivity) context).createMenu(menu);
        }

    }


}
