package com.example.mietapp;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static com.example.mietapp.DataBaseHelper.COLUMN_ID;
import static com.example.mietapp.DataBaseHelper.CUSTOMER_TABLE;

public class MainActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, getAllItems());
        list.setAdapter(adapter);

        RecyclerViewSwipeHelper swipeHelper = new RecyclerViewSwipeHelper(this, list) {
            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        MainActivity.this,
                        "",
                        R.drawable.icons8_delete_64,
                        Color.parseColor("#FF3C30"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                removeItem((int) viewHolder.itemView.getTag());

                            }
                        }
                ));

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        MainActivity.this,
                        "",
                        R.drawable.icons8_info_64,
                        Color.parseColor("#FF9502"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                                showItem((int) viewHolder.itemView.getTag());
                            }
                        }
                ));

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        MainActivity.this,
                        "",
                        R.drawable.icons8_print_100,
                        Color.parseColor("#FFC0CB"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                                showItemPDF((int) viewHolder.itemView.getTag());
                            }
                        }
                ));
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        MainActivity.this,
                        "",
                        R.drawable.mail,
                        Color.parseColor("#ffffff"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                sendEmail((int) viewHolder.itemView.getTag());
                            }
                        }
                ));

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(list);

        findViewById(R.id.neue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto2();
                Snackbar.make(view, "Reservierung aufnehmen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void removeItem(int id) {
        mDatabase.delete(CUSTOMER_TABLE, COLUMN_ID + "=" + id, null);
        adapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        String [] columns = {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_CUSTOMER_NAME, DataBaseHelper.COLUMN_CUSTOMER_ADRESSE, DataBaseHelper.COLUMN_CUSTOMER_HANDY, DataBaseHelper.COLUMN_CUSTOMER_VON, DataBaseHelper.COLUMN_CUSTOMER_BIS, DataBaseHelper.COLUMN_CUSTOMER_BEMERKUNG};
        return mDatabase.query(
                DataBaseHelper.CUSTOMER_TABLE,
                columns,
                null,
                null,
                null,
                null,
                DataBaseHelper.COLUMN_CUSTOMER_VON+" DESC"
                );
           }

    private void showItem(int id){

        Cursor oneCursor = mDatabase.rawQuery("SELECT * FROM CUSTOMER_TABLE WHERE ID = '"+id+"'", null);
        CustomerModel customer = new CustomerModel(null, null, null,null,null,null,null);

        if (oneCursor.moveToFirst()){
            do {
                customer.setId(oneCursor.getString(0));
                customer.setName(oneCursor.getString(1));
                customer.setHandy(oneCursor.getString(2));
                customer.setAdresse(oneCursor.getString(3));
                customer.setVon(oneCursor.getString(4));
                customer.setBis(oneCursor.getString(5));
                customer.setBemerkung(oneCursor.getString(6));
            } while(oneCursor.moveToNext());
        }
        Log.d(String.valueOf(customer.getId()), "ID-MainActivity");
        Log.d(customer.getName(), "Name-MainActivity");

        Intent i = new Intent(getApplicationContext(), Pop.class);

        i.putExtra("CustomerId", customer.getId());
        i.putExtra("CustomerName", customer.getName());
        i.putExtra("CustomerHandy", customer.getHandy());
        i.putExtra("CustomerAdresse", customer.getAdresse());
        i.putExtra("CustomerVon", customer.getVon());
        i.putExtra("CustomerBis", customer.getBis());
        i.putExtra("CustomerBemerkung", customer.getBemerkung());
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
        startActivity(i);
    }
    private void showItemPDF(int id){

        Cursor oneCursor = mDatabase.rawQuery("SELECT * FROM CUSTOMER_TABLE WHERE ID = '"+id+"'", null);
        CustomerModel customer = new CustomerModel(null, null, null,null,null,null,null);

        if (oneCursor.moveToFirst()){
            do {
                // Passing values
                customer.setId(oneCursor.getString(0));
                customer.setName(oneCursor.getString(1));
                customer.setHandy(oneCursor.getString(2));
                customer.setAdresse(oneCursor.getString(3));
                customer.setVon(oneCursor.getString(4));
                customer.setBis(oneCursor.getString(5));
                customer.setBemerkung(oneCursor.getString(6));
                // Do something Here with values
            } while(oneCursor.moveToNext());
        }
        String ids = customer.getId(), Name = customer.getName(),Handy = customer.getHandy(),Adresse = customer.getAdresse(),Von = customer.getVon(),Bis = customer.getBis(),Bemerkung = customer.getBemerkung();
        createPDF(ids,Name,Handy,Adresse,Von,Bis,Bemerkung);
        Log.d(ids, "ids - Show Item PDF");
    }

    private void sendEmail(int id){
        Cursor oneCursor = mDatabase.rawQuery("SELECT * FROM CUSTOMER_TABLE WHERE ID = '"+id+"'", null);
        CustomerModel customer = new CustomerModel(null, null, null,null,null,null,null);
        if (oneCursor.moveToFirst()){
            do {
                // Passing values
                customer.setId(oneCursor.getString(0));
                customer.setName(oneCursor.getString(1));
                customer.setHandy(oneCursor.getString(2));
                customer.setAdresse(oneCursor.getString(3));
                customer.setVon(oneCursor.getString(4));
                customer.setBis(oneCursor.getString(5));
                customer.setBemerkung(oneCursor.getString(6));
                // Do something Here with values
            } while(oneCursor.moveToNext());
        }
        Intent i = new Intent(getApplicationContext(), Popmail.class);
        i.putExtra("CustomerId", customer.getId());
        i.putExtra("CustomerName", customer.getName());
        i.putExtra("CustomerHandy", customer.getHandy());
        i.putExtra("CustomerAdresse", customer.getAdresse());
        i.putExtra("CustomerVon", customer.getVon());
        i.putExtra("CustomerBis", customer.getBis());
        i.putExtra("CustomerBemerkung", customer.getBemerkung());
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
        startActivity(i);
    }

    private void createPDF(String ids, String Name, String Handy, String Adresse, String Von, String Bis, String Bemerkung) {
        File directory = null;
        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory()
                    + "/Data/");
            File photoDirectory = new File(Environment.getDataDirectory()
                    + "/Data-Screenshots/");
            /*
             * this checks to see if there are any previous test photo files
             * if there are any photos, they are deleted for the sake of
             * memory
             */
            if (photoDirectory.exists()) {
                File[] dirFiles = photoDirectory.listFiles();
                if (dirFiles.length != 0) {
                    for (int ii = 0; ii <= dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                }
            }
            // if no directory exists, create new directory
            if (!directory.exists()) {
                directory.mkdir();
            }

            // if phone DOES have sd card
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
             directory = new File(Environment.getExternalStorageDirectory()
                    + "/Mietapp_PDF/");
            File photoDirectory = new File(
                    Environment.getExternalStorageDirectory()
                            + "/Data-Screenshots/");
            if (photoDirectory.exists()) {
                File[] dirFiles = photoDirectory.listFiles();
                if (dirFiles.length > 0) {
                    for (int ii = 0; ii < dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                    dirFiles = null;
                }
            }
            // if no directory exists, create new directory to store test
            // results
            if (!directory.exists()) {
                directory.mkdir();
            }
        }// end of SD card checking

        PdfDocument myPdf = new PdfDocument();
        Bitmap logo = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page myPage = myPdf.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();
        Paint myPaint = new Paint();
        Paint myText = new Paint();
        myPaint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawPaint(myPaint);

        logo = Bitmap.createScaledBitmap(logo,logo.getWidth()/10,logo.getHeight()/10,true);

        canvas.drawBitmap(logo,10,10,null);


        Bitmap signo = BitmapFactory.decodeFile("/data/data/com.example.mietapp/app_external_dir/" + ids + ".png");

        int x=20, y=100;

        myPage.getCanvas().drawText("Kundenname:",x,y,myText);
        myPage.getCanvas().drawText(Name,100+x,y,myText);
        myPage.getCanvas().drawText("Handynummer:",x,20+y,myText);
        myPage.getCanvas().drawText(Handy,100+x,20+y,myText);
        myPage.getCanvas().drawText("Kundenadresse:",x,40+y,myText);
        myPage.getCanvas().drawText(Adresse,100+x,40+y,myText);
        myPage.getCanvas().drawText("Abholung:",x,60+y,myText);
        myPage.getCanvas().drawText(Von, 100+x,60+y,myText);
        myPage.getCanvas().drawText("Rückgabe:",x,80+y,myText);
        myPage.getCanvas().drawText(Bis, 100+x,80+y,myText);
        myPage.getCanvas().drawText("Bemerkung:",x,100+y,myText);
        myPage.getCanvas().drawText(Bemerkung, 100+x,100+y,myText);
        myPage.getCanvas().drawText("Unterschrift:",x,220+y,myText);
        try {
            signo = Bitmap.createScaledBitmap(signo,signo.getWidth()/10,signo.getHeight()/10,true);
            canvas.drawBitmap(signo,100,320,null);
        }
        catch (Exception e) {
        System.out.println("Unterschrift Fehler");
        }

        myPdf.finishPage(myPage);

        Date currentTime = Calendar.getInstance().getTime();
        String current = Name + "_" + currentTime + ".pdf";
        Log.d(String.valueOf(directory), "Directory");
        File myFile = new File(directory, current);
        try {
            myPdf.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        myPdf.close();
        Log.d(String.valueOf(Name), "PDF-Name");
        Log.d(String.valueOf(myFile), "PDF-Path");
        Uri path = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", myFile);

        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenIntent.setClipData(ClipData.newRawUri("", path));
        pdfOpenIntent.setDataAndType(path, "application/pdf");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Log.d(String.valueOf(path), "Path");

        try {
            startActivity(pdfOpenIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(this,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

        }
    }


    private void Goto2() {
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info_settings) {
            Intent i = new Intent(getApplicationContext(), Appinfo.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}