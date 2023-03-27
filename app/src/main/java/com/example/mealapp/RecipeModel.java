package com.example.mealapp;

import java.util.List;

/**
 * Class that defines what the recipes consist of for data, and their appropriate methods.
 */
public class RecipeModel {
    private String name;
    private String description;
    private List<String> ingredients;

    //class constructors
        //full constructor
    public RecipeModel(String name, String description, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }
        //empty constructor
    public RecipeModel() {}

    //getters and setters
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
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
