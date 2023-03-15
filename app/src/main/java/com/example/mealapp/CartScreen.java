package com.example.mealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class CartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        TextView cartTextView = findViewById(R.id.firsttextbox3);

        cartTextView.setMovementMethod(new ScrollingMovementMethod());
        cartTextView.setText("jfvnsajnfjewanbgvjhdfbvhjdafbvjhdbvb jhdfbv hjdfbnvjkdfbhverijbv renvhuisldhnvnuildafhnvarduivhn sriv ndxjkdv bdfv iuvgreuigrfduivfduvjkcbvdfbvdfjkbnvjkdafnbfjkdhnb dfijbn dfkjnbfdjkv cjkn vvjkcn vmnv  nb  jkfnkjszndjknbbv hildfbv uirevuirefvburihncviudfsbnvi hbdfhvjbcdjkbvnfdkjahavn kjdfhnbv ijfald bnviuaedfebi");

    }
}