package com.example.mietapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.jar.Attributes;

public class Popmail extends Activity {

    TextInputLayout emailAdresse;
    Button emailSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailxmt);

        emailAdresse = findViewById(R.id.emailAdresse);
        emailSendButton = findViewById(R.id.emailSend);

        emailSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailSend();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.2));
    }

    private void mailSend() {
        String recipientList = emailAdresse.getEditText().getText().toString();
        String[] recipients = recipientList.split(",");

        Intent i = getIntent();
        String Name = i.getStringExtra("CustomerName");
        String Handy = i.getStringExtra("CustomerHandy");
        String Von = i.getStringExtra("CustomerVon");
        String Bis = i.getStringExtra("CustomerBis");
        String Adresse = i.getStringExtra("CustomerAdresse");
        String Bemerkung = i.getStringExtra("CustomerBemerkung");

        String message = "Sehr geehrte Frau/Herr "+Name+","+'\n'+'\n'+"vielen Dank für Ihre Anfrage und das Interesse an unseren Produkten."
                +'\n'+"Hiermit erhalten Sie ihre Bestellübersicht:"+'\n'+"Datum von: "+Von+" bis: "+Bis+'\n'+"Beschreibung: "+Bemerkung
                +'\n'+'\n'+"Sie haben noch Fragon oder Wünche? Bitte zögern Sie nicht, uns anzurufen." +'\n'+'\n'+"Mit freundlichen Grüßen"
                +'\n'+"Benz Wein- und Getränkemarkt Team Deizisau"+'\n'+"Telefon: 07153 9285584"+'\n'+"Öffnungzeiten: Montag-Samstag von 8 bis 20 Uhr";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Benz Wein- und Getränkemärkte GmbH");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Wähle ein App aus"));
    }
}
