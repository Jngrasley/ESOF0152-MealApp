package com.example.mealapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String RECIPE_TABLE = "RECIPE_TABLE";

    public DBHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    //this is called on the first initialization of the DB. Needed for recipe and date storage.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + RECIPE_TABLE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RECIPE_NAME TEXT, " +
                "RECIPE_DESC TEXT, " +
                "RECIPE_INGRED TEXT" +
                ")";

        sqLiteDatabase.execSQL(createTable);
    }

    //this is likely not needed, but helps with future compatibility.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
