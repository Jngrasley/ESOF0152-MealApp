package com.example.mealapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CartItemAdapter extends BaseAdapter {

    //baseadapter vars
    Context context;
    List<CartModel> cartItemList;
    LayoutInflater inflter;

    //build constructor
    public CartItemAdapter(Context applicationContext, List<CartModel> items) {
        this.context = applicationContext;
        this.cartItemList = items;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //redefine the view with the new data
        view = inflter.inflate(R.layout.activity_list_view_cart_item, null);
        //find the text fields
        TextView item = view.findViewById(R.id.cart_ingredient);
        TextView amount = view.findViewById(R.id.cart_count);

        //reassign the data
        item.setText("\u2022\t" + cartItemList.get(i).getName());
        amount.setText(cartItemList.get(i).getAmount());

        //finally return the new view attr
        return view;
    }
}