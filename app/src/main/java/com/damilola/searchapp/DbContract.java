package com.damilola.searchapp;


//This is the helper class for the DbHelper class the most important database queries are defined here

import android.provider.BaseColumns;

public class DbContract implements BaseColumns {

    public static final String DATABASE_NAME = "damilola.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "members";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULL_NAME = "full_name";


    public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

    //This function creates the members.

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " " +
            "("+ COLUMN_EMAIL + " TEXT primary key, " +
            COLUMN_USERNAME + " TEXT unique, " +
            COLUMN_PASSWORD + " TEXT null, " +
            COLUMN_FULL_NAME +" TEXT null );";
}