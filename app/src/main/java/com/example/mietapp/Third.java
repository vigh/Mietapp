package com.example.mietapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;


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
    public void createPdf(View view){
        PdfDocument myPdf = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page myPage = myPdf.startPage(myPageInfo);

        Paint myPaint = new Paint();
        int x = 10, y = 25;
        String Text = "asdasd";
        myPage.getCanvas().drawText(Text, x, y, myPaint);
        myPdf.finishPage(myPage);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        final File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);
        String current = "PDF" + ".pdf";
        File myFile = new File(directory, current);
        try {
            myPdf.writeTo(new FileOutputStream(myFile));
            FileOutputStream mFileOutStream = new FileOutputStream(myFile);
            mFileOutStream.flush();
            mFileOutStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        myPdf.close();
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

