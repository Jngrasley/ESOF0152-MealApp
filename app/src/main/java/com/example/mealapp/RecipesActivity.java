package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class RecipesActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    ListView listOfRecipes;
    ImageButton searchImageButton;
    Button addRecipeButton;
    Button viewRecipeButton;
    ArrayAdapter recipesAdapter;
    DBHelper dbHelper;

    public void updateRecipeListView(DBHelper db) {
        recipesAdapter = new ArrayAdapter<String>(RecipesActivity.this, android.R.layout.simple_list_item_1, db.getAllRecipesListView());
        listOfRecipes.setAdapter(recipesAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //define UI objects
            //nav menu
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Recipes);
            //listview
        listOfRecipes = findViewById(R.id.list_of_recipes);
        searchImageButton = findViewById(R.id.search_image_button);
        addRecipeButton = findViewById(R.id.new_recipe_button);
        viewRecipeButton = findViewById(R.id.view_recipe_button);
            //functionality of listview
        dbHelper = new DBHelper(RecipesActivity.this);
        updateRecipeListView(dbHelper);

        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(RecipesActivity.this);
                dbHelper.onTruncate("RECIPE_TABLE");
                dbHelper.close();
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecipeEntryActivity.class));
                overridePendingTransition(0,0);
            }
        });

        viewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(RecipesActivity.this);
                updateRecipeListView(dbHelper);
                dbHelper.close();
            }
        });

        listOfRecipes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecipeModel recipe = (RecipeModel) listOfRecipes.getItemAtPosition(i);
                Toast.makeText(RecipesActivity.this, i + ", " + recipe.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //control the activity view based on navigation listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //send to page based on item selected
                switch (item.getItemId())
                {
                    case R.id.Meals: //also known as main activity
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Recipes:
                        return true;
                    case R.id.Cart:
                        startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}