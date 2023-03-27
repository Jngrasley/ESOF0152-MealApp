package com.example.mealapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String RECIPE_TABLE = "RECIPE_TABLE";
    public static final String COL_RECIPE_NAME = "RECIPE_NAME";
    public static final String COL_RECIPE_DESC = "RECIPE_DESC";
    public static final String COL_RECIPE_INGRED = "RECIPE_INGRED";

    public DBHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    //this is called on the first initialization of the DB. Needed for recipe and date storage.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //generate the first recipe table
        String createTable = "CREATE TABLE " + RECIPE_TABLE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT UNIQUE, " +
                COL_RECIPE_DESC + " TEXT, " +
                COL_RECIPE_INGRED + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);
    }

    //this is likely not needed, but helps with future compatibility.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    //method to add a recipe to list of recipes
    public boolean addRecipeToList(RecipeModel newRecipe) {
        //declare starter variables to access the database (DB)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //push variables to new recipe
        cv.put(COL_RECIPE_NAME, newRecipe.getName());
        cv.put(COL_RECIPE_DESC, newRecipe.getDescription());
        cv.put(COL_RECIPE_INGRED, newRecipe.ingredientsToCSV());

        long insert = db.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);

        //check for successful insert value
        if (insert == -1)
            return false;
        else
            return true;
    }
}
