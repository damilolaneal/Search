package com.damilola.searchapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//This class does all that is required for database manipulation in the project required for the database

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(DbContract.CREATE_TABLE_QUERY);
        }catch(SQLException ex){
            Log.d("SQLite not created", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbContract.DROP_QUERY);
        this.onCreate(db);
    }

    public String confirmDetails(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String ans = "";
        String query = "select password from members where username =\"" + username + "\" OR email =\"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                ans = cursor.getString(cursor.getColumnIndex("password"));
            }
            cursor.close();
        }
        db.close();
        return ans;
    }

    public String selectFullName(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String ans = "";
        String query = "select full_name from members where username =\"" + username + "\" OR email =\"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                ans = cursor.getString(cursor.getColumnIndex("full_name"));
            }
            cursor.close();
        }
        db.close();
        return ans;
    }

    public int check_email(String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        int count = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT count(email) FROM members WHERE email=\"" + email + "\"", null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e){
        }
        database.close();
        return count;
    }

    public int check_username(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        int count = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT count(username) FROM members WHERE username=\"" + username + "\"", null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e){
        }
        database.close();
        return count;
    }

    public void save_new_member(String email, String username, String password, String full_name){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("username", username);
        values.put("password", password);
        values.put("full_name", full_name);

        database.insert(DbContract.TABLE_NAME, null, values);
        database.close();
    }
}
