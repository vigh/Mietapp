package com.example.mietapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PopAGB extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showagb);

        TextView agbtext = findViewById(R.id.AGBtext);
        agbtext.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().
        setLayout((int)(width*.8),(int)(height*.6));
}
}
