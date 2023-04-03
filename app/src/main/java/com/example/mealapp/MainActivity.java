package com.example.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //declared UI objects
    BottomNavigationView bottomNavigationView;
    Button saveButton;
    Button clearButton;
    CalendarView mealsCalendar;
    TextView currentBreakfast;
    TextView currentLunch;
    TextView currentDinner;
    Spinner breakfastSpinner;
    Spinner lunchSpinner;
    Spinner dinnerSpinner;
    ArrayAdapter spinnerAdapter;
    long currentDate;
    String currentDateString;

    public void updateSpinnerView() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, dbHelper.getAllRecipeNames());
        breakfastSpinner.setAdapter(spinnerAdapter);
        lunchSpinner.setAdapter(spinnerAdapter);
        dinnerSpinner.setAdapter(spinnerAdapter);

        List<String> list = dbHelper.getMealsForDay(currentDateString);

        currentBreakfast.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        currentLunch.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        currentDinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        if (list.isEmpty()) {
            currentBreakfast.setText("-");
            currentLunch.setText("-");
            currentDinner.setText("-");
        } else {
            currentBreakfast.setText(list.get(0));
            currentLunch.setText(list.get(1));
            currentDinner.setText(list.get(2));
        }

        dbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define UI objects
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setSelectedItemId(R.id.Meals);

        saveButton = findViewById(R.id.calendar_button_save);
        clearButton = findViewById(R.id.calendar_button_clear);

        mealsCalendar = findViewById(R.id.mealsCalendar);
        currentBreakfast = findViewById(R.id.current_breakfast);
        currentLunch = findViewById(R.id.current_lunch);
        currentDinner = findViewById(R.id.current_dinner);
        breakfastSpinner = findViewById(R.id.daily_breakfast_spinner);
        lunchSpinner = findViewById(R.id.daily_lunch_spinner);
        dinnerSpinner = findViewById(R.id.daily_dinner_spinner);
        currentDate = mealsCalendar.getDate();
        currentDateString = dateToString(currentDate);

        updateSpinnerView();

        //control the date change on calendarView
        mealsCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                currentDateString = dateToString(year, month+1, day);
                updateSpinnerView();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data first
                String breakfast = breakfastSpinner.getSelectedItem().toString();
                String lunch = lunchSpinner.getSelectedItem().toString();
                String dinner = dinnerSpinner.getSelectedItem().toString();

                DBHelper dbHelper = new DBHelper(MainActivity.this);
                long checkInsert = dbHelper.saveMealForDay(currentDateString, breakfast, lunch, dinner);
                Toast.makeText(MainActivity.this, checkInsert >= 0 ? "Added Successfully":"Error MA1: There was an issue saving your meals, please try again!", Toast.LENGTH_SHORT).show();
                dbHelper.close();
                updateSpinnerView();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);

                //get data fields
                if (currentBreakfast.getText().toString().equals("-") &&
                        currentLunch.getText().toString().equals("-") &&
                        currentDinner.getText().toString().equals("-")) {
                    //this means that the date is empty
                    //do nothing
                    Toast.makeText(MainActivity.this, "Looks like there are no meals set on that day!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean checkDelete = dbHelper.deleteMealForDay(currentDateString);
                Toast.makeText(MainActivity.this, checkDelete ? "Cleared Successfully":"Error MA2: There was an issue removing your meals, please try again!", Toast.LENGTH_SHORT).show();

                dbHelper.close();

                updateSpinnerView();
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

    public String dateToString(long d) {
        //convert the long data into a Date obj
        Date date= new Date(d);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
        return SDF.format(date);
    }

    public String[] stringToDateArray(String date) {
        return date.split("/");
    }
}