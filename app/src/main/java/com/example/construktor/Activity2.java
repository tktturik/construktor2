package com.example.construktor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;

import static com.example.construktor.DataBase.ID;
import static com.example.construktor.DataBase.arrQ;
import static com.example.construktor.ListTest.ad;


public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    Button next;
    EditText chislo, title,time;
    ImageView strelka;
    public Integer p,sec;
    boolean b1=false,b2=false,b3=false;
    String nazvanie;
    public static Integer idTest=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity2);
        Window w = getWindow();
        //ad = new ArrayAdapter(this, R.layout.razmetka, R.id.textView, arrQ);
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        next = findViewById(R.id.button3);
        next.setOnClickListener(this);
        time=findViewById(R.id.time);
        strelka=findViewById(R.id.imageView2);
        chislo = findViewById(R.id.editTextNumber);
        title = findViewById(R.id.title);



    }

    @Override
    public void onClick(View v) {
        Log.d("aye", "oshibka");
        if (chislo.getText().length() == 0 || Integer.parseInt(chislo.getText().toString()) <= 0) {
            chislo.setText("");
            chislo.setHint("Натуральное число");
            chislo.setHintTextColor(getResources().getColor(R.color.red));
            //Toast.makeText(this,"chislo", Toast.LENGTH_LONG).show();
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
            p = Integer.parseInt(chislo.getText().toString());
            nazvanie = title.getText().toString();
            sec = Integer.parseInt(time.getText().toString());
            Log.d("tabla","TABLICA NazvTesta");
            DataBase db= new DataBase(this,7);
            SQLiteDatabase dbhelp = db.getWritableDatabase();
            ContentValues c = new ContentValues();
            c.clear();
            c.put(DataBase.ID,idTest);
            c.put(DataBase.NameTest,nazvanie);
            c.put(DataBase.time,sec);
            c.put(DataBase.nvop,p);
            dbhelp.insert(DataBase.DATABASE_NAME,null,c);
            Cursor cur = dbhelp.query(DataBase.DATABASE_NAME,null,null,null,null,null,null,null);
            logCursor(cur);
            cur.close();




            try {
                Log.d("123", "oshibk22a2");
                if (arrQ.contains(title.getText().toString())) {
                    Toast.makeText(this, "takoe uje est", Toast.LENGTH_SHORT).show();
                } else {
                    arrQ.add(nazvanie);
                    Log.d("123", "oshibka2");
                    Intent intent = new Intent(this, Construktor.class);
                    b1=false;
                    b2 =false;
                    b3=false;
                    intent.putExtra("chislo", p);
                    intent.putExtra("nazv", String.valueOf(ID));
                    intent.putExtra("time",sec);
                    startActivity(intent);
                }
            } catch (NullPointerException e) {
                arrQ = new HashSet<>();
                arrQ.add(nazvanie);
                b1=false;
                b2=false;
                b3=false;
                Log.d("123", "oshibka2");
                Intent intent = new Intent(this, Construktor.class);
                intent.putExtra("chislo", p);
                intent.putExtra("nazv", nazvanie);
                intent.putExtra("time",sec);
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

