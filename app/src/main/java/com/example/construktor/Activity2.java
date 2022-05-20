package com.example.construktor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    Button next;
    EditText chislo, title,time;
    ImageView strelka;
    public Integer kolQ,sec;
    boolean b1=false,b2=false,b3=false;
    String nazvanie;
    static DataBase db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity2);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        next = findViewById(R.id.button3);
        time=findViewById(R.id.time);
        strelka=findViewById(R.id.imageView2);
        chislo = findViewById(R.id.editTextNumber);
        title = findViewById(R.id.title);

        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (chislo.getText().length() == 0 || Integer.parseInt(chislo.getText().toString()) <= 0) {
            chislo.setText("");
            chislo.setHint("Натуральное число");
            chislo.setHintTextColor(getResources().getColor(R.color.red));
        }else {
            b1=true;
            chislo.setHint(getResources().getString(R.string.kolvop));
        }
        if (title.getText().length() == 0) {
            title.setText("");
            title.setHintTextColor(getResources().getColor(R.color.red));
        } else {
            b2=true;
        }
        if (time.getText().length()==0 || Integer.parseInt(time.getText().toString())<0){
            time.setText("");
            time.setHintTextColor(getResources().getColor(R.color.red));
        }else {
            b3=true;
            strelka.setImageDrawable(getResources().getDrawable(R.drawable.png));
        }
        if (b1 && b2 && b3){
            kolQ = Integer.parseInt(chislo.getText().toString());
            nazvanie = title.getText().toString();
            sec = Integer.parseInt(time.getText().toString());
            SQLiteDatabase dbhelp = db.getWritableDatabase();
            ContentValues c = new ContentValues();
            c.clear();
            c.put(DataBase.NameTest,nazvanie);
            c.put(DataBase.time,sec);
            c.put(DataBase.nvop,kolQ);
            dbhelp.insert(DataBase.DATABASE_NAME,null,c);
            Log.d("tabla1","TABLICA TablNazv");
            Cursor cur = dbhelp.query(DataBase.DATABASE_NAME,null,null,null,null,null,null,null);
            logCursor(cur);
            Log.d("tabla1","------------");
            Log.d("tabla1","TABLICA TabOtv");
            cur= dbhelp.query(DataBase.DATABASE_ANS,null,null,null,null,null,null);
            logCursor(cur);

            Log.d("tabla1","------------");
            Log.d("tabla1","TABLICA TabVop");
            cur= dbhelp.query(DataBase.DATABASE_QUES,null,null,null,null,null,null);
            logCursor(cur);

            Log.d("tabla1","----------");




            try {
                cur = dbhelp.rawQuery("select * from TablNazv where nameTest = ?",new String[]{nazvanie});
                if (cur.getCount()>1) {
                    Toast.makeText(this, "takoe uje est", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, Construktor.class);
                    b1=false;
                    b2 =false;
                    b3=false;
                    intent.putExtra("chislo", kolQ);
                    intent.putExtra("nazv", nazvanie);
                    intent.putExtra("time",sec);
                    cur.close();
                    startActivity(intent);
                }
            } catch (NullPointerException e) {
                b1=false;
                b2=false;
                b3=false;
                Intent intent = new Intent(this, Construktor.class);
                intent.putExtra("chislo", kolQ);
                intent.putExtra("nazv", nazvanie);
                intent.putExtra("time",sec);
                cur.close();
                startActivity(intent);
            }


        }
    }
    @SuppressLint("Range")
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

