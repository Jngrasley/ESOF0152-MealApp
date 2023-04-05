package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShoppingCartActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    ListView listOfItems;
    EditText searchField;
    Button emptyButton;
    Button addButton;
    CartItemAdapter itemAdapter;
    CartItemAdapter searchAdapter;

    public void updateItemsListView() {
        DBHelper dbHelper = new DBHelper(ShoppingCartActivity.this);
        itemAdapter = new CartItemAdapter(ShoppingCartActivity.this, dbHelper.getCartListView());
        listOfItems.setAdapter(itemAdapter);
        dbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        //define UI objects
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Cart);
        listOfItems = findViewById(R.id.list_shopping_cart);
        searchField = findViewById(R.id.item_search_field);
        emptyButton = findViewById(R.id.cart_clear_button);
        addButton = findViewById(R.id.cart_add_button);

        updateItemsListView();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartEntryActivity.class));
                overridePendingTransition(0,0);
            }
        });

        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(ShoppingCartActivity.this);
                dbHelper.onTruncate("SHOPPING_TABLE");
                Toast.makeText(ShoppingCartActivity.this, ("Shopping cart reset!"), Toast.LENGTH_SHORT).show();
                dbHelper.close();
                updateItemsListView();
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
                    updateItemsListView();
                } else {
                    //if words in box, update the list
                    DBHelper dbHelper = new DBHelper(ShoppingCartActivity.this);
                    searchAdapter = new CartItemAdapter(ShoppingCartActivity.this, dbHelper.searchCartListView(searchItem));
                    listOfItems.setAdapter(searchAdapter);
                    dbHelper.close();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        listOfItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //access the pressed object
                CartModel cartModel = (CartModel)adapterView.getItemAtPosition(i);

                //set up delete query
                DBHelper dbHelper = new DBHelper(ShoppingCartActivity.this);
                boolean checkDelete = dbHelper.deleteCartItem(cartModel);
                dbHelper.close();

                Toast.makeText(ShoppingCartActivity.this, checkDelete ? "Deleted Successfully" : "Error SCA1: There was an issue deleting your item, please try again!", Toast.LENGTH_SHORT).show();

                updateItemsListView();
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