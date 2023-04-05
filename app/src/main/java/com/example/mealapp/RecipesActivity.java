package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipesActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    ListView listOfRecipes;
    EditText searchField;
    Button addRecipeButton;
    Button refreshRecipeButton;
    //ArrayAdapter recipesAdapter;
    RecipeListAdapter recipesAdapter;
    RecipeListAdapter searchAdapter;

    public void updateRecipeListView() {
        DBHelper db = new DBHelper(RecipesActivity.this);
        //recipesAdapter = new ArrayAdapter<String>(RecipesActivity.this, android.R.layout.simple_list_item_1, db.getAllRecipesListView());
        recipesAdapter = new RecipeListAdapter(RecipesActivity.this, db.getAllRecipesListView());
        listOfRecipes.setAdapter(recipesAdapter);
        db.close();
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
        searchField = findViewById(R.id.recipe_search_field);
        addRecipeButton = findViewById(R.id.new_recipe_button);
        refreshRecipeButton = findViewById(R.id.refresh_recipe_button);
            //functionality of listview
        updateRecipeListView();

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecipeEntryActivity.class));
                overridePendingTransition(0,0);
            }
        });

        refreshRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecipeListView();
                searchField.setText("");
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchItem = searchField.getText().toString();

                if (searchItem.isEmpty() || searchItem.equals(null)) {
                    //don't do anything, just update to old adapter
                    updateRecipeListView();
                } else {
                    //if words in box, update the list
                    DBHelper dbHelper = new DBHelper(RecipesActivity.this);
                    searchAdapter = new RecipeListAdapter(RecipesActivity.this, dbHelper.searchAllRecipesListView(searchItem));
                    listOfRecipes.setAdapter(searchAdapter);
                    dbHelper.close();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        listOfRecipes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //access the pressed object
                RecipeModel recipe = (RecipeModel)adapterView.getItemAtPosition(i);

                //set up delete query
                DBHelper dbHelper = new DBHelper(RecipesActivity.this);
                boolean checkDelete = dbHelper.deleteRecipe(recipe);
                dbHelper.close();

                Toast.makeText(RecipesActivity.this, checkDelete ? "Deleted Successfully" : "Error RA1: There was an issue deleting your recipe, please try again!", Toast.LENGTH_SHORT).show();

                updateRecipeListView();
                return checkDelete;
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