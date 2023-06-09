package com.example.mealapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    public static final String SHOPPING_TABLE = "SHOPPING_TABLE";
    public static final String COL_CART_ITEMS = "CART_ITEMS";
    public static final String COL_CART_AMOUNTS = "CART_AMOUNTS";

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

        //generate the calendar table
        createTable = "CREATE TABLE " + CALENDAR_TABLE +
                "(" +
                COL_CALENDAR_DATE + " TEXT PRIMARY KEY, " +
                COL_CALENDAR_BREAKFAST + " TEXT, " +
                COL_CALENDAR_LUNCH + " TEXT, " +
                COL_CALENDAR_DINNER + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);

        //generate the shopping cart table
        createTable = "CREATE TABLE " + SHOPPING_TABLE +
                "(" +
                COL_CART_ITEMS + " TEXT PRIMARY KEY, " +
                COL_CART_AMOUNTS + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);

        //now we can input some default values for the users on the start of the app
        ContentValues cv = new ContentValues();
        cv.put(COL_RECIPE_NAME, toTitleCase("Classic Omelette"));
        cv.put(COL_RECIPE_DESC, ("The basic omelette we all know and love. All you need to do is start by whisking up your eggs with the water, salt, and pepper. Butter up a pan and cook until cooked. Serve with toast."));
        cv.put(COL_RECIPE_INGRED, ("2 eggs, 2 tbsp water, salt & pepper to taste, extra ingredients per preference"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.put(COL_RECIPE_NAME, toTitleCase("Pasta Aglio e Olio"));
        cv.put(COL_RECIPE_DESC, ("A classic Italian dish originating from Naples. This simple meal consists of linguine and garlic cooked in olive oil with lemon juice, parsley, and red pepper flakes."));
        cv.put(COL_RECIPE_INGRED, ("0.5 head garlic, 0.5 cup parsley, 0.5 cup olive oil, 1 teaspoon red pepper flake, 0.5 pound linguine, 0.5 lemon, salt & pepper to taste"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.put(COL_RECIPE_NAME, toTitleCase("Mac n Cheese"));
        cv.put(COL_RECIPE_DESC, ("A quick recipe for an easy, creamy stovetop macaroni and cheese with a toasted breadcrumb topping."));
        cv.put(COL_RECIPE_INGRED, ("1 cup milk, 1.5 cups water, 0.5 cup panko breadcrumbs, 3.5 tablespoons butter, 8 ounces macaroni, 2 ounces ramano cheese, 4 ounces american cheese, 0.5 teaspoon dijon mustard, 4 ounces cheddar cheese, cayenne pepper to taste"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.put(COL_RECIPE_NAME, toTitleCase("Chicken Curry"));
        cv.put(COL_RECIPE_DESC, ("Delicious chicken curry recipe. Tender pieces of chicken and vegetables in a savory curry sauce."));
        cv.put(COL_RECIPE_INGRED, ("1.5 pounds chicken thighs, 2 carrots, 2 onions, 3 potatoes, 2 cloves garlic, 0.5 apple, 4 cups chicken stock, 1 package japanese curry roux, salt & pepper to taste"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.put(COL_RECIPE_NAME, toTitleCase("Garden Salad"));
        cv.put(COL_RECIPE_DESC, ("A fresh, simple salad. Your health is important!"));
        cv.put(COL_RECIPE_INGRED, ("10 cups lettuce, 1 tomato, 1 carrot, 1 cucumber, 1 cup tofu, 5 tablespoons italian dressing"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.put(COL_RECIPE_NAME, toTitleCase("Orange Joe"));
        cv.put(COL_RECIPE_DESC, ("If it tastes bad, that's normal."));
        cv.put(COL_RECIPE_INGRED, ("1 cup orange juice, 1 cup coffee"));
        sqLiteDatabase.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);
        cv.clear();
    }

    //this is likely not needed, but helps with future compatibility.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void onTruncate(String table_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ table_name +";");
        //sqLiteDatabase.execSQL("delete from sqlite_sequence where name='" + table_name + "';");
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
        output.add("-");
        output.sort(null);
        return output;
    }

    public List<RecipeModel> getAllRecipesListView() {
        //define the output list
        List<RecipeModel> output = new ArrayList<>();

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
                output.add(newRecipe);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public List<RecipeModel> searchAllRecipesListView(String name) {
        //declare output string
        List<RecipeModel> output = new ArrayList<>();

        //declare the query
        String outputQuery = ("SELECT * FROM " + RECIPE_TABLE + " WHERE " + COL_RECIPE_NAME + " LIKE \'%" + name + "%\';");
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
                output.add(newRecipe);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    //add a method to get the string and convert it to a title case
    public static String toTitleCase(String string) {
        //define a string builder
        StringBuilder sb = new StringBuilder(string.length());
        //be able to recognize the next word as a title
        boolean nextTitleCase = true;

        //run a foreach loop for each character
        for (char c : string.toCharArray()) {
            //check each letter recursively to breakdown words between spaces
            if (Character.isSpaceChar(c))
                nextTitleCase = true;
            else if (nextTitleCase) {
                //make a recursive look to check the next character
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            //add that character to the builder
            sb.append(c);
        }
        //finally return the finished builder when done
        return sb.toString();
    }
    //method to add a recipe to list of recipes
    public boolean addRecipeToList(RecipeModel newRecipe) {
        //declare starter variables to access the database (DB)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //push variables to new recipe
        cv.put(COL_RECIPE_NAME, toTitleCase(newRecipe.getName()));
        cv.put(COL_RECIPE_DESC, newRecipe.getDescription());
        cv.put(COL_RECIPE_INGRED, RecipeModel.ingredientsToCSV(newRecipe.getIngredients()));

        long insert = db.insert(RECIPE_TABLE, COL_RECIPE_NAME, cv);

        //check for successful insert value
        return insert != -1;
    }

    public boolean deleteRecipe(RecipeModel recipeModel) {
        //find and remove recipe, return true
        // if no found in db, return false
        //setup db
        SQLiteDatabase db = this.getWritableDatabase();
        //execute
        int result = db.delete(RECIPE_TABLE, (COL_RECIPE_NAME +" = \'" + recipeModel.getName() + "\';"), null);

        //cleanup
        db.close();
        //return result;
        return result != -1;
    }

    public List<String> getMealsForDay(String date) {
        List<String> output = new ArrayList<String>();

        //declare the query
        String outputQuery = ("SELECT * FROM " + CALENDAR_TABLE + " WHERE " + COL_CALENDAR_DATE + " = \'" + date + "\';");
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
            //use a do while to ensure you capture the first element!!
            //capture the data elements one at a time
            //Long d = cs.getLong(0);
            String breakfast = cs.getString(1);
            String lunch = cs.getString(2);
            String dinner = cs.getString(3);

            output.add(0, breakfast);
            output.add(1, lunch);
            output.add(2, dinner);
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public long saveMealForDay(String date, String breakfast, String lunch, String dinner) {
        long output;
        //declare starters
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //push variables to new recipe
        cv.put(COL_CALENDAR_DATE, date);
        if (!(breakfast.equals("-")))
            cv.put(COL_CALENDAR_BREAKFAST, breakfast);
        if (!(lunch.equals("-")))
            cv.put(COL_CALENDAR_LUNCH, lunch);
        if (!(dinner.equals("-")))
            cv.put(COL_CALENDAR_DINNER, dinner);

        //
        if (checkExists(date))
            output = db.update(CALENDAR_TABLE, cv, (COL_CALENDAR_DATE + " = '" + date +"'"), null);
        else
            output = db.insert(CALENDAR_TABLE, COL_CALENDAR_DATE, cv);

        cv.clear();
        db.close();
        //check for successful insert value
        return output;
    }

    public boolean checkExists(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT * FROM " + CALENDAR_TABLE + " WHERE " + COL_CALENDAR_DATE + " = '" + date + "';");

        //execute the query with a db object
        Cursor cs = db.rawQuery(query, null);

        if (cs.moveToFirst())
            return true;
        else
            return false;
    }
    public boolean deleteMealForDay(String date) {
        //setup db
        SQLiteDatabase db = this.getWritableDatabase();
        //execute
        int result = db.delete(CALENDAR_TABLE, (COL_CALENDAR_DATE + " = '" + date + "';"), null);

        //result = cs.moveToFirst();

        //cs.close();
        db.close();
        //return result;
        return result != -1;
    }

    public List<CartModel> getCartListView() {
        //define the output list
        List<CartModel> output = new ArrayList<>();

        //declare the query
        String outputQuery = "SELECT * FROM " + SHOPPING_TABLE;
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
            //use a do while to ensure you capture the first element!!
            do {
                //capture the data elements one at a time
                String itemName = cs.getString(0);
                String itemAmount = cs.getString(1);

                CartModel cartModel = new CartModel(itemName, itemAmount);
                output.add(cartModel);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public List<CartModel> searchCartListView(String name) {
        //declare output string
        List<CartModel> output = new ArrayList<>();

        //declare the query
        String outputQuery = ("SELECT * FROM " + SHOPPING_TABLE + " WHERE " + COL_CART_ITEMS + " LIKE \'%" + name + "%\';");
        //execute the query with a db object
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery(outputQuery, null);

        if (cs.moveToFirst()) {
            // data is valid, iter through the rest
            //use a do while to ensure you capture the first element!!
            do {
                //capture the data elements one at a time
                String itemName = cs.getString(0);
                String itemAmount = cs.getString(1);

                CartModel cartModel = new CartModel(itemName, itemAmount);
                output.add(cartModel);
            } while(cs.moveToNext());
        }

        //make sure to close out the resources and return the data
        cs.close();
        db.close();
        return output;
    }

    public boolean addItemToCart(CartModel cartModel) {
        //declare starter variables to access the database (DB)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //push variables to new recipe
        cv.put(COL_CART_ITEMS, toTitleCase(cartModel.getName()));
        cv.put(COL_CART_AMOUNTS, cartModel.getAmount());

        long insert = db.insert(SHOPPING_TABLE, COL_CART_ITEMS, cv);

        //check for successful insert value
        return insert != -1;
    }

    public boolean deleteCartItem(CartModel cartModel) {
        //find and remove recipe, return true
        // if no found in db, return false
        //setup db
        SQLiteDatabase db = this.getWritableDatabase();
        //execute
        int result = db.delete(SHOPPING_TABLE, (COL_CART_ITEMS +" = \'" + cartModel.getName() + "\';"), null);

        //cleanup
        db.close();
        //return result;
        return result != -1;
    }
}
