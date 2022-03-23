package com.example.mietapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


public class Third extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto2();
            }
        });


        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto1();
            }
        });
    }
    private void Goto2(){
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }
    private void Goto1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

