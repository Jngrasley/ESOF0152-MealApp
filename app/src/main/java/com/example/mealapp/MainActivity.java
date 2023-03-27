package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    TextView dailyLunchView;
    CalendarView mealsCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define UI objects
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Meals);
        dailyLunchView = findViewById(R.id.daily_lunch);
        mealsCalendar = findViewById(R.id.mealsCalendar);


        //control the date change on calendarview
        mealsCalendar.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            
        });

        //control the activity view based on navigation listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //send to page based on item selected
                switch (item.getItemId())
                {
                    case R.id.Meals: //also known as main activity
                        return true;
                    case R.id.Cart:
                        startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Recipes:
                        startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}