package com.example.mietapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


import static com.example.mietapp.DataBaseHelper.COLUMN_CUSTOMER_NAME;
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
                        "X",
                        android.R.drawable.ic_dialog_alert,
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
                        "info",
                        android.R.drawable.ic_dialog_info,
                        Color.parseColor("#FF9502"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                                showItem((int) viewHolder.itemView.getTag());
                            }
                        }
                ));

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(list);



/*        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(list);

 */

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


//        File directory =
//        File mypath =  new File(directory, current);
        //            Files.deleteIfExists(Paths.get(mypath + id + ".jpg"));
//            Files.deleteIfExists(Paths.get(mypath + id + ".png"));
//        mypath.delete();
//        Log.d(String.valueOf(mypath), "mypath in MainActivity");


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

/*

//showID.setText(Customer.getId());

//        showID.setText(String.valueOf(ID));

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.showinfo, null);

        // create the popup window

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
*/
//        return mDatabase.query(CUSTOMER_TABLE,null, COLUMN_ID = String.valueOf(id),null,null,null,null);
    }


    private void Goto2() {
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }
/*    private void Showinfo(int id){
        getOneItem(id);



        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.showinfo, null);

        // create the popup window

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        adapter.swapCursor(getAllItems());
    }
*/
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}