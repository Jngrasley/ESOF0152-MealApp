package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    CalendarView mealsCalendar;
    Spinner breakfastSpinner;
    Spinner lunchSpinner;
    Spinner dinnerSpinner;
    ArrayAdapter spinnerAdapter;
    Long currentDate;
    String selectedDate;

    public void updateSpinnerView(DBHelper dbHelper) {
        spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, dbHelper.getAllRecipeNames());
        breakfastSpinner.setAdapter(spinnerAdapter);
        lunchSpinner.setAdapter(spinnerAdapter);
        dinnerSpinner.setAdapter(spinnerAdapter);

        breakfastSpinner.setSelected();
        lunchSpinner.setSelected();
        dinnerSpinner.setSelected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define UI objects
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Meals);

        mealsCalendar = findViewById(R.id.mealsCalendar);
        breakfastSpinner = findViewById(R.id.daily_breakfast_spinner);
        lunchSpinner = findViewById(R.id.daily_lunch_spinner);
        dinnerSpinner = findViewById(R.id.daily_dinner_spinner);
        currentDate = mealsCalendar.getDate();

        DBHelper dbHelper = new DBHelper(MainActivity.this);

        dbHelper.close();


        //control the date change on calendarView
        mealsCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String newDate = dateToString(year, month+1, day);
                Toast.makeText(MainActivity.this, newDate, Toast.LENGTH_SHORT).show();
                selectedDate = newDate;


            }
        });

        //control the activity view based on navigation listener
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
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
        });

    }

    public String dateToString(int y, int m, int d) {
        return (y + "/" + m + "/" + d);
    }

    public String[] stringToDate(String date) {
        return date.split("/");
    }
}