 package com.example.construktor;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

import static com.example.construktor.Activity2.db;
import static com.example.construktor.MainActivity.DF_VERSION;


public class Consturktor2 extends AppCompatActivity implements View.OnClickListener {
    TextView An1, An2, An3, An4, tx2, nomer, corrAns, procCorrAns, time;
    static float correct = 0;
    DialogFragm df;
    boolean rb1 = false, rb2 = false, rb3=false, rb4=false;
    private Integer currQues=1;
    static Integer n2,secunds;
    CountDownTimer cTimer;
    static String currTest,query;
    SQLiteDatabase dbhelper;
    Cursor cur;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.construktor2);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        tx2 = findViewById(R.id.ques);
        time = findViewById(R.id.textView4);
        corrAns = findViewById(R.id.corrAns);
        procCorrAns = findViewById(R.id.procCorrAns);
        nomer = findViewById(R.id.nomer);
        An1 = findViewById(R.id.ans1);
        An2 = findViewById(R.id.ans2);
        An3 = findViewById(R.id.ans3);
        An4 = findViewById(R.id.ans4);
        
        An1.setOnClickListener(this);
        An2.setOnClickListener(this);
        An3.setOnClickListener(this);
        An4.setOnClickListener(this);
        
        currTest=(getIntent().getExtras()).getString("id");
        df = new DialogFragm();
        DF_VERSION = 3;

        dbhelper = db.getReadableDatabase();
        query = "select TablNazv.time, TablNazv.kolQ from TablNazv where TablNazv.Id = ?";
        cur = dbhelper.rawQuery(query,new String[]{currTest});
        logCursor(cur);
        cur.moveToFirst();
        int idN2 = cur.getColumnIndex(DataBase.nvop);
        int idSec = cur.getColumnIndex(DataBase.time);
        n2 = cur.getInt(idN2);
        secunds = cur.getInt(idSec);

        nomer.setText(String.valueOf(currQues)+"/"+String.valueOf(n2));

        query= "select TablVop.ques from TablVop where TablVop.Id = ? and TablVop.qId = ?";
        cur = dbhelper.rawQuery(query,new String[]{currTest,String.valueOf(currQues)});
        cur.moveToFirst();
        int idVop = cur.getColumnIndex(DataBase.textQues);
        tx2.setText(cur.getString(idVop));
       
        query ="select TablOtv.ans, TablOtv.TrueFalse from TablOtv where TablOtv.Id = ? and TablOtv.qId = ? and TablOtv.ansId = ?";
        
        for(int i =1;i<=4;i++){
            cur = dbhelper.rawQuery(query,new String[]{currTest,String.valueOf(currQues),String.valueOf(i)});
            logCursor(cur);
            switch (i){
                case 1:
                    cur.moveToFirst();
                    int idAns1 = cur.getColumnIndex(DataBase.textAns);
                    int idTrFl = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrFl)==1){
                        rb1=true;
                    }
                    An1.setText(cur.getString(idAns1));
                    break;
                case 2:
                    cur.moveToFirst();
                    int idAns2 = cur.getColumnIndex(DataBase.textAns);
                    int idTrF2 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrF2)==1){
                        rb2=true;
                    }
                    An2.setText(cur.getString(idAns2));
                    break;
                case 3:
                    cur.moveToFirst();
                    int idAns3 = cur.getColumnIndex(DataBase.textAns);
                    int idTrF3 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrF3)==1){
                        rb3=true;
                    }
                    An3.setText(cur.getString(idAns3));
                    break;
                case 4:
                    cur.moveToFirst();
                    int idAns4 = cur.getColumnIndex(DataBase.textAns);
                    int idTrF4 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrF4)==1){
                        rb4=true;
                    }
                    An4.setText(cur.getString(idAns4));
                    break;
                default:
                    break;
            }
        }
        if (secunds==0){
            time.setText("");
        }else reverseTimer(secunds);
        currQues++;
    }

    public void reverseTimer(int Seconds) {

        cTimer = new CountDownTimer(Seconds * 1000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds %= 60;
                time.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                cancelTimer();
                df.show(getSupportFragmentManager(), "df");
            }
        }.start();
    }
    public void cancelTimer() {
        cTimer.cancel();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.ans1:
               if (rb1) {
                   correct++;
               }
               break;
           case R.id.ans2:
               if (rb2) {
                   correct++;
               }
               break;
           case R.id.ans3:
               if (rb3) {
                   correct++;
               }
               break;
           case R.id.ans4:
               if (rb4) {
                   correct++;
               }
               break;
       }

        if (currQues <= n2) {
            nomer.setText(String.valueOf(currQues)+"/"+String.valueOf(n2));

            query = "select TablVop.ques from TablVop where TablVop.Id = ? and TablVop.qId = ?";
            cur= dbhelper.rawQuery(query,new String[]{currTest,String.valueOf(currQues)});
            cur.moveToFirst();
            int idVop = cur.getColumnIndex(DataBase.textQues);
            tx2.setText(cur.getString(idVop));
            String query4 ="select TablOtv.ans, TablOtv.TrueFalse from TablOtv where TablOtv.Id = ? and TablOtv.qId = ? and TablOtv.ansId = ?";

            for(int i =1;i<=4;i++){
                cur = dbhelper.rawQuery(query,new String[]{currTest,String.valueOf(currQues),String.valueOf(i)});
                logCursor(cur);
                switch (i){
                    case 1:
                        cur.moveToFirst();
                        int idAns1 = cur.getColumnIndex(DataBase.textAns);
                        int idTrFl = cur.getColumnIndex(DataBase.true_false);
                        if (cur.getInt(idTrFl)==1){
                            rb1=true;
                        }
                        An1.setText(cur.getString(idAns1));
                        break;
                    case 2:
                        cur.moveToFirst();
                        int idAns2 = cur.getColumnIndex(DataBase.textAns);
                        int idTrF2 = cur.getColumnIndex(DataBase.true_false);
                        if (cur.getInt(idTrF2)==1){
                            rb2=true;
                        }
                        An2.setText(cur.getString(idAns2));
                        break;
                    case 3:
                        cur.moveToFirst();
                        int idAns3 = cur.getColumnIndex(DataBase.textAns);
                        int idTrF3 = cur.getColumnIndex(DataBase.true_false);
                        if (cur.getInt(idTrF3)==1){
                            rb3=true;
                        }
                        An3.setText(cur.getString(idAns3));
                        break;
                    case 4:
                        cur.moveToFirst();
                        int idAns4 = cur.getColumnIndex(DataBase.textAns);
                        int idTrF4 = cur.getColumnIndex(DataBase.true_false);
                        if (cur.getInt(idTrF4)==1){
                            rb4=true;
                        }
                        An4.setText(cur.getString(idAns4));
                        break;
                    default:
                       break;
                }
            }
            
           
           
            currQues++;
            }
        else {
            cur.close();
            currQues=1;
            try {
                cTimer.cancel();
                df.show(getSupportFragmentManager(),"df");
            }catch (NullPointerException e){
                df.show(getSupportFragmentManager(),"df");
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
                    Log.d("tabla2", str);
                } while (cursor.moveToNext());
            }
        } else Log.d("tabla", "Cursor is null");
    }
    }







