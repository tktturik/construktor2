package com.example.construktor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import java.util.Objects;

import static com.example.construktor.Activity2.db;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button add,open;
    SQLiteDatabase dbhelp;
    public static int DF_VERSION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DF_VERSION=1;
        add = findViewById(R.id.button2);
        open = findViewById(R.id.button);

        add.setOnClickListener(this);
        open.setOnClickListener(this);


        try {
            dbhelp = db.getWritableDatabase();
            Log.d("tabla1","TABLICA TablNazv");
            Cursor cur = dbhelp.query(DataBase.DATABASE_NAME,null,null,null,null,null,null,null);
            logCursor(cur);
            Log.d("tabla1","------------");
            Log.d("tabla1","TABLICA TabOtv");
            cur = dbhelp.query(DataBase.DATABASE_ANS,null,null,null,null,null,null);
            logCursor(cur);
            Log.d("tabla1","------------");
            Log.d("tabla1","TABLICA TabVop");
            cur = dbhelp.query(DataBase.DATABASE_QUES,null,null,null,null,null,null);
            logCursor(cur);
            cur.close();
            Log.d("tabla1","----------");
        }catch (NullPointerException e) {
             Log.d("tabla","aaaaaaaa");
             db = new DataBase(this,8);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent intent1= new Intent(this,ListTest.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                Intent intent= new Intent(this,Activity2.class);
                startActivity(intent);
                break;
        }
    }


    void logCursor(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : cursor.getColumnNames()) {
                        str = str.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("tabla", str);
                } while (cursor.moveToNext());
            }
        } else Log.d("tabla", "Cursor is null");
    }

}
