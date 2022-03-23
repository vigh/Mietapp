package com.example.mietapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Pop extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);

        TextView showID = findViewById(R.id.showID);
        TextView showName = findViewById(R.id.showName);
        TextView showVon = findViewById(R.id.showVon);
        TextView showBis = findViewById(R.id.showBis);
        TextView showHandy = findViewById(R.id.showHandy);
        TextView showAdresse = findViewById(R.id.showAdresse);
        TextView showBemerkung = findViewById(R.id.showBemerkung);
        ImageView Signo = findViewById(R.id.showSigno);

        Intent i = getIntent();
        String Name = i.getStringExtra("CustomerName");
        String ID = i.getStringExtra("CustomerId");
        String Handy = i.getStringExtra("CustomerHandy");
        String Von = i.getStringExtra("CustomerVon");
        String Bis = i.getStringExtra("CustomerBis");
        String Adresse = i.getStringExtra("CustomerAdresse");
        String Bemerkung = i.getStringExtra("CustomerBemerkung");

//        String IDs = getString(IDh);
//        int IDi=Integer.parseInt(IDs,16);

        Log.d(Name, "Name-Pop");
//        Log.d(getString(IDh), "ID-Pop");
        Log.d(ID,"ID-Pop");

        showID.setText(ID);
        showName.setText(Name);
        showHandy.setText(Handy);
        showAdresse.setText(Adresse);
        showVon.setText(Von);
        showBis.setText(Bis);
        showBemerkung.setText(Bemerkung);

        File imgFile = new  File("/data/data/com.example.mietapp/app_external_dir/" + ID + ".png");
//        File imgFile = new  File("/data/data/com.example.mietapp/app_external_dir/ '\"+ID+\"' .png");
//        File imgFile = new  File(Environment.getExternalStorageDirectory() + "/data/data/com.example.mietapp/app_external_dir/20.png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Signo.setImageBitmap(myBitmap);
            Log.d(null, "File exists");

        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
       getWindow().setLayout((int)(width*.8),(int)(height*.6));

    }

}
