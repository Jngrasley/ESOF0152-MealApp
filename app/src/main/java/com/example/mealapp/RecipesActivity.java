package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;



public class RecipesActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    ListView listOfRecipes;
    Button addRecipeButton;
    Button viewRecipeButton;
    ArrayAdapter recipesAdapter;
    DBHelper dbHelper;



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
        addRecipeButton = findViewById(R.id.new_recipe_button);
        viewRecipeButton = findViewById(R.id.view_recipe_button);
            //functionality of listview
        dbHelper = new DBHelper(RecipesActivity.this);
        updateRecipeListView(dbHelper);

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
                DBHelper dbHelper1 = new DBHelper(RecipesActivity.this);
                updateRecipeListView(dbHelper1);
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

    public void updateRecipeListView(DBHelper db) {
        recipesAdapter = new ArrayAdapter<RecipeModel>(RecipesActivity.this, android.R.layout.simple_list_item_1, db.getAllRecipes());
        listOfRecipes.setAdapter(recipesAdapter);
    }
}