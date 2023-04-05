package com.example.mealapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that defines what the recipes consist of for data, and their appropriate methods.
 */
public class RecipeModel {
    private int ID;
    private String name;
    private String description;
    private List<String> ingredients;

    //class constructors
        //full constructor
    public RecipeModel(int ID, String name, String description, List<String> ingredients) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }
        //empty constructor
    public RecipeModel() {}

    //getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    //toStrings
        //toString with no ingredients for listview
    @Override
    public String toString() {
        return "RecipeModel{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public String toListViewString() {
        return (ID + ": " + name + ",\n" + description);
    }

    //takes a list of ingredients and puts it to a csv for the DB to read properly.
    public static String ingredientsToCSV(List<String> ingredientsList) {
        return String.join(
                ",",
                ingredientsList
        );
    }

    //take a csv string from the DB and put to a list
    public static List<String> CSVToIngredients(String CSV) {
        return Arrays.asList(CSV.split(",", -1));
    }
}
