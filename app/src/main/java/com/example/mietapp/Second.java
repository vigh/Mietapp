package com.example.mietapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;

public class Second extends AppCompatActivity {

    int counter = 0;
    Button Speichern;
    Button getSignature;
    String Name, Handy, Bemerkung, Adresse, Von, Bis, ID;
    TextInputLayout Namebox;
    TextInputLayout Handybox;
    TextInputLayout Bemerkungbox;
    TextInputLayout Adressbox;
    TextInputEditText Vonbox;
    TextInputEditText Bisbox;

    public static final int SIGNATURE_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Namebox = findViewById(R.id.Namebox);
        Handybox = findViewById(R.id.Handybox);
        Adressbox = findViewById(R.id.Adressebox);
        Bemerkungbox = findViewById(R.id.Bemerkungbox);
        Vonbox = findViewById(R.id.Vonbox);
        Bisbox = findViewById(R.id.Bisbox);
        Speichern = findViewById(R.id.Speichernbox);
        getSignature = findViewById(R.id.signature);


        getSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second.this, CaptureSignature.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
            }
        });

        Speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter ++;
                Name = Namebox.getEditText().getText().toString().trim();
                Handy = Handybox.getEditText().getText().toString().trim();
                Adresse = Adressbox.getEditText().getText().toString().trim();
                Bemerkung = Bemerkungbox.getEditText().getText().toString().trim();
                Bis = Bisbox.getText().toString();
                Von = Vonbox.getText().toString();

                CustomerModel customerModel;
                try{
                    customerModel = new CustomerModel(ID, Name, Handy,Adresse,Von,Bis,Bemerkung);
                    Snackbar.make(view, customerModel.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }

                catch (Exception e){
                    Snackbar.make(view, "Falsche eingaben", Snackbar.LENGTH_SHORT).show();
                customerModel = new CustomerModel(ID, "Error","Error","Error","Error","Error","Error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(Second.this);
                boolean succes = dataBaseHelper.addOne(customerModel);
            }

        });

        Vonbox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                final Calendar calendar= Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Second.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Vonbox.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                },year, month,day);
                datePickerDialog.show();
            }

        });
        Bisbox.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

                    final Calendar calendar= Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(Second.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Bisbox.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        }
                    },year, month,day);
                    datePickerDialog.show();
                }
        });


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto1();
            }
        });


        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goto3();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    Goto1();

                    Bundle bundle = data.getExtras();
                    String status = bundle.getString("status");
                    if (status.equalsIgnoreCase("done")) {
                        Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 105, 50);
                        toast.show();
                    }
                }
                break;
        }

    }
    private void Goto1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void Goto2(){
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }
    private void Goto3(){
        Intent intent = new Intent(this, Third.class);
        startActivity(intent);
    }
}