package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShoppingCartEntryActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    EditText itemField;
    EditText amountField;
    Button addButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_entry);

        //define UI components
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        itemField = findViewById(R.id.cart_item_edit);
        amountField = findViewById(R.id.cart_amount_edit);
        addButton = findViewById(R.id.cart_entry_save);
        backButton = findViewById(R.id.cart_entry_back);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartModel cartModel;

                DBHelper dbHelper;

                if (itemField.getText().toString().trim().length() > 0 && amountField.getText().toString().trim().length() > 0) {
                    //try to insert it into the db, or throw if not possible

                    dbHelper = new DBHelper(ShoppingCartEntryActivity.this);
                    try {
                        //declare fields to the instance
                        cartModel = new CartModel(itemField.getText().toString().trim(), amountField.getText().toString().trim());
                        //send the data to the database now

                    } catch (Exception e) {
                        Toast.makeText(ShoppingCartEntryActivity.this, "Error SCEA1: There was an issue saving your item, please try again!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(ShoppingCartEntryActivity.this, "Oops, looks like your item isn\'t filled out!", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean checkInsert = dbHelper.addItemToCart(cartModel);

                Toast.makeText(ShoppingCartEntryActivity.this, checkInsert ? "Added to cart!":"Error SCEA2: There was an issue saving your item, please try again!", Toast.LENGTH_SHORT).show();

                dbHelper.close();
                backButton.callOnClick();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
                overridePendingTransition(0,0);
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
                        startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Cart:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}