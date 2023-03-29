package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipeEntryActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    Button backButton;
    Button saveButton;
    EditText recipeNameEdit;
    EditText recipeDescEdit;
    EditText recipeIngredientsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_entry);

        //define UI components
            //buttons
        backButton = findViewById(R.id.recipe_entry_back);
        saveButton = findViewById(R.id.recipe_entry_save);
        recipeNameEdit = findViewById(R.id.recipe_name_edit);
        recipeDescEdit = findViewById(R.id.recipe_desc_edit);
        recipeIngredientsEdit = findViewById(R.id.recipe_ingredients_edit);
            //nav menu
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Recipes);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                overridePendingTransition(0,0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define a new recipe obj
                RecipeModel recipe;

                DBHelper dbHelper;
                if (recipeNameEdit.getText().toString().trim().length() > 0 && recipeDescEdit.getText().toString().trim().length() > 0) {
                    //try to insert it into the db, or throw if not possible

                    dbHelper = new DBHelper(RecipeEntryActivity.this);

                    try {
                        //declare fields to the instance
                        recipe = new RecipeModel(-1, recipeNameEdit.getText().toString(), recipeDescEdit.getText().toString(), RecipeModel.CSVToIngredients(recipeIngredientsEdit.getText().toString()));
                        //send the data to the database now

                    } catch (Exception e) {
                        Toast.makeText(RecipeEntryActivity.this, "Error RE1: There was an issue saving your recipe, please try again!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(RecipeEntryActivity.this, "Make sure the name and description are filled out.", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean checkInsert = dbHelper.addRecipeToList(recipe);

                Toast.makeText(RecipeEntryActivity.this, checkInsert ? "Added Successfully":"Error RE2: There was an issue saving your recipe, please try again!", Toast.LENGTH_SHORT).show();

                dbHelper.close();
                backButton.callOnClick();
            }
        });



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
                        startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                        overridePendingTransition(0,0);
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