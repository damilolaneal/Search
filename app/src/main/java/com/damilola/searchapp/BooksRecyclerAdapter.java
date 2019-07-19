package com.damilola.searchapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.BooksViewHolder>  implements Filterable {

    private Context mCtx;

    private List<BookModel> bookList;
    private List<BookModel> mFilteredList;
    private RecyclerViewClickListener clickListener;


    public BooksRecyclerAdapter(Context mCtx, List<BookModel> bookList, RecyclerViewClickListener clickListener) {
        this.mCtx = mCtx;
        this.bookList = bookList;
        this.mFilteredList = bookList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BooksRecyclerAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.books_recycler_container, null);

        return new BooksRecyclerAdapter.BooksViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final BooksRecyclerAdapter.BooksViewHolder holder, int i) {
        final BookModel bookmodel = mFilteredList.get(i);
        holder.tv_container_title.setText(bookmodel.getTitle());
        holder.tv_container_authors.setText(bookmodel.getAuthors());
        holder.tv_container_authors.setTypeface(null, Typeface.ITALIC);
        holder.tv_container_year.setText(bookmodel.getYear());

        if ((bookmodel.getCategory()).equalsIgnoreCase("cat")){
            holder.activity_category_background.setBackgroundResource(R.drawable.cats);
        }else if ((bookmodel.getCategory()).equalsIgnoreCase("dog")){
            holder.activity_category_background.setBackgroundResource(R.drawable.dogs);
        }else if ((bookmodel.getCategory()).equalsIgnoreCase("leopard")){
            holder.activity_category_background.setBackgroundResource(R.drawable.leopards);
        }else if ((bookmodel.getCategory()).equalsIgnoreCase("tiger")){
            holder.activity_category_background.setBackgroundResource(R.drawable.tiger);
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mlistener;

        CardView card_container;
        ImageView activity_category_background;
        TextView tv_container_title;
        TextView tv_container_authors;
        TextView tv_container_year;
        LinearLayout container_layout;
        FloatingActionButton floatingActionButton;

        BooksViewHolder(@NonNull View view, RecyclerViewClickListener clickListener) {
            super(view);
            mlistener = clickListener;
//            view.setOnClickListener(this);

            card_container = itemView.findViewById(R.id.card_container);
            container_layout = itemView.findViewById(R.id.container_layout);
            activity_category_background = itemView.findViewById(R.id.activity_category_background);
            tv_container_title = itemView.findViewById(R.id.tv_container_title);
            tv_container_authors = itemView.findViewById(R.id.tv_container_authors);
            tv_container_year = itemView.findViewById(R.id.tv_container_year);
            floatingActionButton = itemView.findViewById(R.id.floatingActionButton);

            floatingActionButton.setOnClickListener(this);
            container_layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mlistener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter(){

        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){

                String charString = charSequence.toString().trim();

                if(charString.isEmpty()){

                    mFilteredList = bookList;
                }else{
                    List<BookModel> filteredList = new ArrayList<>();

                    Log.d("CHECK", "charList" + bookList.toString());

                    for(BookModel articles : bookList){

                        if(articles.getTitle().toLowerCase().contains(charString)){

                            filteredList.add(articles);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults){

                mFilteredList = (List<BookModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }
}
