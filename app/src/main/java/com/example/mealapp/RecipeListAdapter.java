package com.example.mealapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecipeListAdapter extends BaseAdapter {

    //baseadapter vars
    Context context;
    List<RecipeModel> recipeList;
    LayoutInflater inflter;

    //build constructor
    public RecipeListAdapter(Context applicationContext, List<RecipeModel> recipes) {
        this.context = applicationContext;
        this.recipeList = recipes;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //redefine the view with the new data
        view = inflter.inflate(R.layout.activity_list_view_item, null);
        //find the text fields
        TextView name = view.findViewById(R.id.item_name);
        TextView desc = view.findViewById(R.id.item_desc);
        TextView ingr = view.findViewById(R.id.item_ingr);
        //reassign the data
        name.setText("\u2022\t" + recipeList.get(i).getName());
        desc.setText("Description - " + recipeList.get(i).getDescription());
        ingr.setText("Ingredients - " + RecipeModel.ingredientsToCSV(recipeList.get(i).getIngredients()));

        //finally return the new view attr
        return view;
    }
}