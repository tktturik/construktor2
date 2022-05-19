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

import static com.example.construktor.MainActivity.DF_VERSION;


public class Consturktor2 extends AppCompatActivity implements View.OnClickListener {
    TextView An1, An2, An3, An4, tx2, nomer, corrAns, procCorrAns, time;
    DataBase db;
    static float correct = 0;
    static String title;
    DialogFragm df;
    boolean rb1, rb2, rb3, rb4;
    private Integer i = 1;
    static Integer n2,secunds;
    CountDownTimer cTimer;
    String NowTabl;
    // private final String[] stolbci = {DataBase.an1, DataBase.an2, DataBase.an3, DataBase.an4,DataBase.q1,DataBase.bool,DataBase.nvop,DataBase.time};
    private final String [] NameTestStolbci = {DataBase.NameTest,DataBase.nvop,DataBase.time};



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
        db = new DataBase(this, 7);
        df = new DialogFragm();
        Log.d("123", "oshibka123");
        DF_VERSION = 3;
        NowTabl = "0";
                //(getIntent().getExtras()).getString("id");
        title = DataBase.DATABASE_QUES;
        SQLiteDatabase dbhelper = db.getReadableDatabase();
        String filt = "Id = ?";
        String query = "select * "+
        "from NazvTesta " +
        "where NazvTesta.Id = ?";
        Cursor cur = dbhelper.rawQuery(query,new String[]{"0"});
        logCursor(cur);
        //n2 = cur.getInt((int)cur.getColumnIndex(DataBase.nvop));
        //secunds = cur.getInt((int)cur.getColumnIndex(DataBase.time));
        logCursor(cur);
        cur.close();

        //  //Cursor cur = dbhelper.query(DataBase.DATABASE_QUES, stolbci, null, null, null, null, null);
        //  cur.moveToFirst();
        //  i++;
        //  int idTime=cur.getColumnIndex(DataBase.time);
        //  int idAns1 = cur.getColumnIndex(DataBase.an1);
        //  int idAns2 = cur.getColumnIndex(DataBase.an2);
        //  int idAns3 = cur.getColumnIndex(DataBase.an3);
        //  int idAns4 = cur.getColumnIndex(DataBase.an4);
        //  int idVop = cur.getColumnIndex(DataBase.q1);
        ////  int idBool=cur.getColumnIndex(DataBase.bool);
        ////  int nvopr=cur.getColumnIndex(DataBase.nvop);
        ////  int bool=cur.getInt(idBool);
        //  if (bool==1){
        //      rb1=true;
        //  }else if (bool==2){
        //      rb2=true;
        //  }else if(bool==3){
        //      rb3=true;
        //  }else if(bool==4){
        //      rb4=true;
        //  }
        //  An1.setText(cur.getString(idAns1));
        //  An2.setText(cur.getString(idAns2));
        //  An3.setText(cur.getString(idAns3));
        //  An4.setText(cur.getString(idAns4));
        //  tx2.setText(cur.getString(idVop));
        //  n2=cur.getInt(nvopr);
        //  if (cur.getInt(idTime)==0){
        //      time.setText("");
        //  }else reverseTimer(cur.getInt(idTime));
        //  cur.close();
        //  dbhelper.close();
        //  nomer.setText(String.valueOf(i)+"/"+String.valueOf(n2));


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
        SQLiteDatabase dbhelper = db.getReadableDatabase();


 //   switch (v.getId()) {
 //       case R.id.ans1:
 //           if (rb1) {
 //               correct++;
 //           }
 //           break;
 //       case R.id.ans2:
 //           if (rb2) {
 //               correct++;
 //           }
 //           break;
 //       case R.id.ans3:
 //           if (rb3) {
 //               correct++;
 //           }
 //           break;
 //       case R.id.ans4:
 //           if (rb4) {
 //               correct++;
 //           }
 //           break;
 //   }

        if (i < n2) {
            String nomerVop = String.valueOf(i);
            String sqlQuery = "select TablVop.ques, TablOtv.textAns "+
                    "from TablOtv "+
                    "inner join TablVop "+
                    "on TablOtv.qId = TablVop.qId "+
                    "inner join NazvTesta "+
                    "on TablVop.Id = NazvTesta.Id " +
                    "where NazvTesta.Id = ? and TablVop.qId =? and  + TablOtv.ansId = ?";
            Cursor cur = dbhelper.rawQuery(sqlQuery,new String[]{NowTabl,nomerVop,"1","2","3","4"});
            tx2.setText(cur.getString((int) cur.getColumnIndex(DataBase.textQues)));
            for (int j=1;j<=4;j++){
                switch (j){
                    case 1:
                        An1.setText(cur.getString((int)cur.getColumnIndex(DataBase.textAns)));
                        break;
                    case 2:
                        An2.setText(cur.getString((int)cur.getColumnIndex(DataBase.textAns)));
                        break;
                    case 3:
                        An3.setText(cur.getString((int)cur.getColumnIndex(DataBase.textAns)));
                        break;
                    case 4:
                        An4.setText(cur.getString((int)cur.getColumnIndex(DataBase.textAns)));
                        break;
                    default:
                        break;
                }
            }
            i++;
            }
        else {
            dbhelper.close();
            i=1;
            try {
                cTimer.cancel();
                df.show(getSupportFragmentManager(),"df");
            }catch (NullPointerException e){
                df.show(getSupportFragmentManager(),"df");
            }
            //Toast.makeText(this,String.valueOf(ans+"%"),Toast.LENGTH_LONG).show();
            //long k= dbhelper.delete(DataBase.DATABASE_QUES,null,null);
            //Log.d("228","----"+k+"----");

        }
            // Log.d("228","i="+i+" "+n2);
            // while (cur.moveToNext()){
            //     int idAns1 = cur.getColumnIndex(DataBase.an1);
            //     int idAns2 = cur.getColumnIndex(DataBase.an2);
            //     int idAns3 = cur.getColumnIndex(DataBase.an3);
            //     int idAns4 = cur.getColumnIndex(DataBase.an4);
            //     int idVop = cur.getColumnIndex(DataBase.q1);
            //     Log.d("228",cur.getString(idAns1)+" "+cur.getString(idAns2)+" "+cur.getString(idAns3)+" "+cur.getString(idAns4)+" "+cur.getString(idVop));
            /// }
            //     cur.moveToPosition(i);
            //     i++;
            //     nomer.setText(String.valueOf(i)+"/"+String.valueOf(n2));

            //     //int id = cur.getColumnIndex(DataBase.ID);
            //     int idAns1 = cur.getColumnIndex(DataBase.an1);
            //     int idAns2 = cur.getColumnIndex(DataBase.an2);
            //     int idAns3 = cur.getColumnIndex(DataBase.an3);
            //     int idAns4 = cur.getColumnIndex(DataBase.an4);
            //     int idVop = cur.getColumnIndex(DataBase.q1);
            //     int idBool=cur.getColumnIndex(DataBase.bool);
            //     int bool=cur.getInt(idBool);
            //     if (bool==1){
            //         rb1=true;
            //     }else if (bool==2){
            //         rb2=true;
            //     }else if(bool==3){
            //         rb3=true;
            //     }else if(bool==4){
            //         rb4=true;
            //     }
            //     An1.setText(cur.getString(idAns1));
            //     An2.setText(cur.getString(idAns2));
            //     An3.setText(cur.getString(idAns3));
            //     An4.setText(cur.getString(idAns4));
            //     tx2.setText(cur.getString(idVop));
            // }



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







