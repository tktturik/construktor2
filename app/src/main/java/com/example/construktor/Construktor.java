package com.example.construktor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.construktor.Activity2.idTest;
import static com.example.construktor.MainActivity.DF_VERSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.HashSet;
import java.util.Objects;

public class Construktor extends AppCompatActivity implements View.OnClickListener {
    EditText vopros,ans1,ans2,ans3,ans4;
    TextView tx;
    private Integer n,j=1,time;
    private Integer qId=1,ansId=1,box1=0,box2=0,box3=0,box4=0;
    static String a1,a2,a3,a4,vop,DataBaseName;
    RadioGroup rb1;
    Button b1;
    DialogFragment df;
    DataBase db;
    SQLiteDatabase dbhelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.construktor);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DF_VERSION=1;
        DataBaseName=(getIntent().getExtras()).getString("nazv");
        n = (getIntent().getExtras()).getInt("chislo");
        time=(getIntent().getExtras()).getInt("time");
        tx=findViewById(R.id.textView);
        vopros = findViewById(R.id.vopros);
        ans1 = findViewById(R.id.otvet1);
        ans2 = findViewById(R.id.otvet2);
        ans3 = findViewById(R.id.otvet3);
        ans4 = findViewById(R.id.otvet4);
        rb1=findViewById(R.id.rGroup);
        b1 = findViewById(R.id.button4);
        df = new DialogFragm();
        df.show(getSupportFragmentManager(),"df");
        b1.setOnClickListener(this);
        db= new DataBase(this,7);
        tx.setText(String.valueOf(qId)+"/"+String.valueOf(n));


    }
    @Override
    public void onClick(View v) {
        switch (rb1.getCheckedRadioButtonId()){
            case R.id.radioButton:
                box1=1;
                break;
            case R.id.radioButton2:
                box2=1;
                break;
            case R.id.radioButton3:
                box3=1;
                break;
            case R.id.radioButton4:
                box4=1;
                break;
        }
        a1 = ans1.getText().toString();
        a2 = ans2.getText().toString();
        a3 = ans3.getText().toString();
        a4 = ans4.getText().toString();
        vop=vopros.getText().toString();



        if (v.getId() == R.id.button4) {
            if (qId <= n) {
                Log.d("228", qId+" ");
                dbhelp = db.getWritableDatabase();
                ContentValues c = new ContentValues();
                c.clear();
                c.put(DataBase.ID,idTest);
                c.put(DataBase.qId,qId);
                c.put(DataBase.textQues,vop);
                dbhelp.insert(DataBase.DATABASE_QUES,null,c);
                c.clear();
                for(int i =1;i<=4;i++){
                    switch (i){
                        case 1:
                            c.put(DataBase.qId,qId);
                            c.put(DataBase.ansId,i);
                            c.put(DataBase.textAns,a1);
                            c.put(DataBase.true_false,box1);
                            break;
                        case 2:
                            c.put(DataBase.qId,qId);
                            c.put(DataBase.ansId,i);
                            c.put(DataBase.textAns,a2);
                            c.put(DataBase.true_false,box2);
                            break;
                        case 3:
                            c.put(DataBase.qId,qId);
                            c.put(DataBase.ansId,i);
                            c.put(DataBase.textAns,a3);
                            c.put(DataBase.true_false,box3);
                            break;
                        case 4:
                            c.put(DataBase.qId,qId);
                            c.put(DataBase.ansId,i);
                            c.put(DataBase.textAns,a4);
                            c.put(DataBase.true_false,box4);
                            break;
                        default:
                            break;
                    }
                    dbhelp.insert(DataBase.DATABASE_ANS,null,c);
                }


                refresh();

            }
            qId++;
            tx.setText(String.valueOf(j)+"/"+String.valueOf(n));

            if (qId> n) {
                Log.d("tabla","TABLICA TabOtv");
                Cursor curos = dbhelp.query(DataBase.DATABASE_ANS,null,null,null,null,null,null);
                logCursor(curos);
                curos.close();
                Log.d("tabla","------------");
                Log.d("tabla","TABLICA TabVop");
                Cursor curos2 = dbhelp.query(DataBase.DATABASE_QUES,null,null,null,null,null,null);
                logCursor(curos2);
                curos2.close();
                Log.d("tabla","----------");
                j--;
                Log.d("tabla","---raw---");
                String query = "select * "+
                        "from NazvTesta " +
                        "where kolQ = ?";
                Cursor cur = dbhelp.rawQuery(query,new String[] {"1"});
                logCursor(cur);
                cur.close();
                Cursor cursor;
                String table1 = "NazvTesta";
                String[] columns2 = {"NazvTesta.kolQ", "NazvTesta.time"};
                String selection2 = "time = ?";
                String[] selectionArgs2 = {"30"};
                cursor = dbhelp.query(table1, columns2, selection2, selectionArgs2, null, null, null);
                logCursor(cursor);
                cursor.close();
                DF_VERSION=2;
                dbhelp.close();
                df.show(getSupportFragmentManager(),"df");
                tx.setText(String.valueOf(j)+"/"+String.valueOf(n));
            }

        }
    }
    private void refresh(){
        String s="";
        ans1.setText(s);
        ans2.setText(s);
        ans3.setText(s);
        ans4.setText(s);
        vopros.setText(s);

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