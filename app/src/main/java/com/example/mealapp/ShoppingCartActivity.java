package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShoppingCartActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        TextView cartTextView = findViewById(R.id.firsttextbox3);

        cartTextView.setMovementMethod(new ScrollingMovementMethod());
        cartTextView.setText("jfvnsajnfjewanbgvjhdfbvhjdafbvjhdbvb jhdfbv hjdfbnvjkdfbhverijbv renvhuisldhnvnuildafhnvarduivhn sriv ndxjkdv bdfv iuvgreuigrfduivfduvjkcbvdfbvdfjkbnvjkdafnbfjkdhnb dfijbn dfkjnbfdjkv cjkn vvjkcn vmnv  nb  jkfnkjszndjknbbv hildfbv uirevuirefvburihncviudfsbnvi hbdfhvjbcdjkbvnfdkjahavn kjdfhnbv ijfald bnviuaedfebi");


        //define UI objects
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Cart);

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