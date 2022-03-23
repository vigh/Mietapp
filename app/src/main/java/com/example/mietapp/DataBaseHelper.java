package com.example.mietapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_HANDY = "CUSTOMER_HANDY";
    public static final String COLUMN_CUSTOMER_ADRESSE = "CUSTOMER_ADRESSE";
    public static final String COLUMN_CUSTOMER_VON = "CUSTOMER_VON";
    public static final String COLUMN_CUSTOMER_BIS = "CUSTOMER_BIS";
    public static final String COLUMN_CUSTOMER_BEMERKUNG = "CUSTOMER_BEMERKUNG";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(Context context) {
        super(context, CUSTOMER_TABLE, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_HANDY + " TEXT, " + COLUMN_CUSTOMER_ADRESSE + " TEXT, " + COLUMN_CUSTOMER_VON + " TEXT, " + COLUMN_CUSTOMER_BIS + " TEXT, " + COLUMN_CUSTOMER_BEMERKUNG + " TEXT" + ");";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        onCreate(db);

    }

    public boolean addOne(CustomerModel customerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_HANDY, customerModel.getHandy());
        cv.put(COLUMN_CUSTOMER_ADRESSE, customerModel.getAdresse());
        cv.put(COLUMN_CUSTOMER_VON, customerModel.getVon());
        cv.put(COLUMN_CUSTOMER_BIS, customerModel.getBis());
        cv.put(COLUMN_CUSTOMER_BIS, customerModel.getBis());
        cv.put(COLUMN_CUSTOMER_BEMERKUNG, customerModel.getBemerkung());


        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
/*    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        String queryString = "Select * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String customerHandy = cursor.getString(2);
                String customerAdresse = cursor.getString(3);
                String customerVon = cursor.getString(4);
                String customerBis = cursor.getString(5);
                String customerBemerkung = cursor.getString(6);

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerHandy, customerAdresse, customerVon, customerBis, customerBemerkung);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());
        }
        else {
        }
        cursor.close();
        db.close();
        return returnList;

    }
    public String getOne (int id){

        String queryString = "Select * FROM CUSTOMER_TABLE WHERE COLUMN_ID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

    }

 */
}
