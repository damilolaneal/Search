package com.damilola.searchapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "journals.db";
    private static final int DATABASE_VERSION = 1;

    Context context;

    DBAssetHelper(Context context) {
        //DBHandler constructor.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2) {
        super.onUpgrade(database, i, i2);
    }

    //This method contains the query which is used to load the meeting list recycler
    public ArrayList<Map<String,String>> getBookArray(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT title, authors, year, category FROM journal ORDER BY year DESC, title ASC", null);
        cursor.moveToFirst();
        ArrayList<Map<String,String>> bookData = new ArrayList<>();

        Map<String, String> map;

        if (cursor.getCount() > 0){

            if (cursor.moveToFirst()){

                do {
                    //do stuff

                    map = new HashMap<>();

                    map.put("title", cursor.getString(cursor.getColumnIndex("title")));
                    map.put("authors", cursor.getString(cursor.getColumnIndex("authors")));
                    map.put("year", cursor.getString(cursor.getColumnIndex("year")));
                    map.put("category", cursor.getString(cursor.getColumnIndex("category")));

                    bookData.add( map);
                    cursor.moveToNext();

                }while (!cursor.isAfterLast());
            }

            cursor.close();
        }
        db.close();
        return bookData;
    }
}
