package com.damilola.searchapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main2ActivityListFragment extends Fragment {

    EditText search;
    RecyclerView recyclerView;
    private List<BookModel> bookList = new ArrayList<>();
    ArrayList<Map<String,String>> journalList = new ArrayList<>();
    DBAssetHelper dBAssetHelper;
    RecyclerViewClickListener listener;
    BooksRecyclerAdapter booksRecyclerAdapter;

    public Main2ActivityListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_list, container, false);

        search = fragmentLayout.findViewById(R.id.search);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dBAssetHelper = new DBAssetHelper(getActivity());

        listener = (view, position) -> {

            switch(view.getId()){

                case R.id.floatingActionButton:
                    Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
                    break;

                case R.id.container_layout:
                    Toast.makeText(getContext(), "Position1 " + position, Toast.LENGTH_SHORT).show();
                    break;
            }

        };

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (booksRecyclerAdapter != null){
                    booksRecyclerAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        loadRecyclerView();
        // Inflate the layout for this fragment
        return fragmentLayout;
    }

    //This method is used to load recycler view from database
    public void loadRecyclerView(){
        journalList = new ArrayList<>();
        journalList = dBAssetHelper.getBookArray();

        Log.d("HELLO_J",journalList+"");
        recyclerController(journalList);
    }

    //This method populates the recycler from the database
    private void recyclerController( ArrayList<Map<String,String>> wordList){
        bookList.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject;
        for(int i = 0; i<jsonArray.length();i++){
            try {
                jsonObject = jsonArray.getJSONObject(i);
                bookList.add(new BookModel(
                        jsonObject.getString("title"),
                        jsonObject.getString("authors"),
                        jsonObject.getString("year"),
                        jsonObject.getString("category")
                ));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        booksRecyclerAdapter = new BooksRecyclerAdapter(getActivity(), bookList, listener);
        booksRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(booksRecyclerAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
}


/*switch(v.getId()){

        case R.id.Button_MyCards: *//** Start a new Activity MyCards.java *//*
        Intent intent = new Intent(this, MyCards.class);
        this.startActivity(intent);
        break;

        case R.id.Button_Exit: *//** AlerDialog when click on Exit *//*
        MyAlertDialog();
        break;
        }*/


