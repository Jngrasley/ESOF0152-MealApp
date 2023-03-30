package com.example.mealapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String RECIPE_TABLE = "RECIPE_TABLE";
    public static final String COL_RECIPE_NAME = "RECIPE_NAME";
    public static final String COL_RECIPE_DESC = "RECIPE_DESC";
    public static final String COL_RECIPE_INGRED = "RECIPE_INGRED";
    public static final String CALENDAR_TABLE = "CALENDAR_TABLE";
    public static final String COL_CALENDAR_DATE = "DATE";
    public static final String COL_CALENDAR_BREAKFAST = "BREAKFAST";
    public static final String COL_CALENDAR_LUNCH = "LUNCH";
    public static final String COL_CALENDAR_DINNER = "DINNER";


    public DBHelper(@Nullable Context context) {
        super(context, "recipe_db", null, 1);
    }

    //this is called on the first initialization of the DB. Needed for recipe and date storage.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //generate the recipe table
        String createTable = "CREATE TABLE " + RECIPE_TABLE +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT UNIQUE, " +
                COL_RECIPE_DESC + " TEXT, " +
                COL_RECIPE_INGRED + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);

        createTable = "CREATE TABLE " + CALENDAR_TABLE +
                "(" +
                COL_CALENDAR_DATE + "DATE PRIMARY KEY, " +
                COL_CALENDAR_BREAKFAST + " TEXT UNIQUE, " +
                COL_CALENDAR_LUNCH + " TEXT, " +
                COL_CALENDAR_DINNER + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);

    }

    //this is likely not needed, but helps with future compatibility.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void onTruncate(String table_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ table_name +";");
        sqLiteDatabase.execSQL("delete from sqlite_sequence where name='" + table_name + "';");
        sqLiteDatabase.close();
    }

    public List<String> getAllRecipeNames()  {
        //define the output list
        List<String> output = new ArrayList<>();

        //declare the query
        String outputQuery = "SELECT " + COL_RECIPE_NAME + " FROM " + RECIPE_TABLE;
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
            //use a do while to ensure you capture the first element!!
            do {
                //capture the data elements one at a time
                String recipeName = cs.getString(0);

                output.add(recipeName);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public List<String> getAllRecipesListView() {
        //define the output list
        List<String> output = new ArrayList<>();

        //declare the query
        String outputQuery = "SELECT * FROM " + RECIPE_TABLE;
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
                //use a do while to ensure you capture the first element!!
            do {
                //capture the data elements one at a time
                int recipeID = cs.getInt(0);
                String recipeName = cs.getString(1);
                String recipeDesc = cs.getString(2);
                String recipeIngredients = cs.getString(3);

                RecipeModel newRecipe = new RecipeModel(recipeID, recipeName, recipeDesc, RecipeModel.CSVToIngredients(recipeIngredients));
                output.add(newRecipe.toListViewString());
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    //method to add a recipe to list of recipes
    public boolean addRecipeToList(RecipeModel newRecipe) {
        //declare starter variables to access the database (DB)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //push variables to new recipe
        cv.put(COL_RECIPE_NAME, newRecipe.getName());
        cv.put(COL_RECIPE_DESC, newRecipe.getDescription());
        cv.put(COL_RECIPE_INGRED, RecipeModel.ingredientsToCSV(newRecipe.getIngredients()));

        long insert = db.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);

        //check for successful insert value
        return insert != -1;
    }

    public List<String> getMealsForDay(String date) {
        //define the output list
        List<String> output = new ArrayList<>();

        //declare the query
        String outputQuery = "SELECT * FROM " + RECIPE_TABLE + "WHERE " + COL_CALENDAR_DATE +" = " + date + ";";
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
            //use a do while to ensure you capture the first element!!
            do {
                //capture the data elements one at a time
                //Long d = cs.getLong(0);
                String breakfast = cs.getString(1);
                String lunch = cs.getString(2);
                String dinner = cs.getString(3);

                output.add(breakfast);
                output.add(lunch);
                output.add(dinner);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public void saveMealForDay(String date) {

    }
}
