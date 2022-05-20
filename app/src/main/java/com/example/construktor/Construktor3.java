package com.example.construktor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static com.example.construktor.Activity2.db;
import static com.example.construktor.MainActivity.DF_VERSION;

public class Construktor3 extends AppCompatActivity implements View.OnClickListener {
    Button upDateBtn;
    EditText ed1, ed2, ed3, ed4, edVop;
    TextView nomerVop;
    RadioGroup radioGroup2;
    RadioButton rb1, rb2, rb3, rb4;
    SQLiteDatabase dbhelp;
    Cursor cur;
    String currTest, red1, red2, red3, red4, redvop;
    int box1 = 0, box2 = 0, box3 = 0, box4 = 0;
    Integer currQues = 1, n3;
    DialogFragm df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.construktor3);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        upDateBtn = findViewById(R.id.Con3btn);
        ed1 = findViewById(R.id.otv1);
        ed2 = findViewById(R.id.otv2);
        ed3 = findViewById(R.id.otv3);
        ed4 = findViewById(R.id.otv4);
        rb1 = findViewById(R.id.krujok);
        rb2 = findViewById(R.id.krujok2);
        rb3 = findViewById(R.id.krujok3);
        rb4 = findViewById(R.id.krujok4);
        edVop = findViewById(R.id.vopros3);
        radioGroup2 = findViewById(R.id.rGroup2);
        nomerVop = findViewById(R.id.nomerVop);

        currTest = (getIntent().getExtras()).getString("id");
        dbhelp = db.getWritableDatabase();
        cur = dbhelp.rawQuery("select TablNazv.kolQ from TablNazv where TablNazv.Id = ?", new String[]{currTest});
        logCursor(cur);
        cur.moveToFirst();
        int idKolQ = cur.getColumnIndex(DataBase.nvop);
        n3 = cur.getInt(idKolQ);
        nomerVop.setText(String.valueOf(currQues) + "/" + String.valueOf(n3));
        loadText(currTest, currQues);
        df = new DialogFragm();
        DF_VERSION=2;


    }


    @Override
    public void onClick(View v) {

        if (currQues <n3) {
            saveText();
            currQues++;
            loadText(currTest, currQues);
            nomerVop.setText(String.valueOf(currQues) + "/" + String.valueOf(n3));
        } else {
            nomerVop.setText(String.valueOf(currQues) + "/" + String.valueOf(n3));
            saveText();
            df.show(getSupportFragmentManager(),"df");


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

    void loadText(String idTest, int currQues) {
        String query = "select TablVop.ques from TablVop where TablVop.Id = ? and TablVop.qId = ?";
        cur = dbhelp.rawQuery(query,new String[]{idTest,String.valueOf(currQues)});
        cur.moveToFirst();
        int idvop = cur.getColumnIndex(DataBase.textQues);
        edVop.setText(cur.getString(idvop));
        query = "select TablOtv.ans , TablOtv.TrueFalse " +
                "from TablOtv " +
                "inner join TablVop " +
                "on TablOtv.qId = TablVop.qId " +
                "inner join TablNazv " +
                "on TablNazv.Id = TablOtv.Id " +
                "where TablVop.qId = ? and TablNazv.Id = ? and  TablOtv.ansId = ?";
        for (int i = 1; i <= 4; i++) {
            cur = dbhelp.rawQuery(query, new String[]{String.valueOf(currQues), idTest, String.valueOf(i)});
            switch (i) {
                case 1:
                    cur.moveToFirst();
                    int idAns1 = cur.getColumnIndex(DataBase.textAns);
                    int idTrFl = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrFl) == 1) {
                        rb1.setChecked(true);
                    } else rb1.setChecked(false);
                    ed1.setText(cur.getString(idAns1));
                    break;
                case 2:
                    cur.moveToFirst();
                    int idAns2 = cur.getColumnIndex(DataBase.textAns);
                    int idTrFl2 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrFl2) == 1) {
                        rb2.setChecked(true);
                    } else rb2.setChecked(false);
                    ed2.setText(cur.getString(idAns2));
                    break;
                case 3:
                    cur.moveToFirst();
                    int idAns3 = cur.getColumnIndex(DataBase.textAns);
                    int idTrFl3 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrFl3) == 1) {
                        rb3.setChecked(true);
                    } else rb3.setChecked(false);
                    ed3.setText(cur.getString(idAns3));
                    break;
                case 4:
                    cur.moveToFirst();
                    int idAns4 = cur.getColumnIndex(DataBase.textAns);
                    int idTrF4 = cur.getColumnIndex(DataBase.true_false);
                    if (cur.getInt(idTrF4) == 1) {
                        rb4.setChecked(true);
                    } else rb4.setChecked(false);
                    ed4.setText(cur.getString(idAns4));
                    break;
                default:
                    break;

            }
        }
    }
    void saveText(){
        ContentValues c = new ContentValues();
        red1 = ed1.getText().toString();
        red2 = ed2.getText().toString();
        red3 = ed3.getText().toString();
        red4 = ed4.getText().toString();
        redvop = edVop.getText().toString();
        switch (radioGroup2.getCheckedRadioButtonId()) {
            case R.id.krujok:
                box1 = 1;
                break;
            case R.id.krujok2:
                box2 = 1;
                break;
            case R.id.krujok3:
                box3 = 1;
                break;
            case R.id.krujok4:
                box4 = 1;
                break;

        }
        c.put(DataBase.textQues, redvop);
        dbhelp.update(DataBase.DATABASE_QUES, c, "Id = ? and qId = ? ", new String[]{currTest, String.valueOf(currQues)});
        c.clear();
        for (int i = 1; i <= 4; i++) {
            switch (i) {
                case 1:
                    c.clear();
                    c.put(DataBase.ID, currTest);
                    c.put(DataBase.qId, currQues);
                    c.put(DataBase.ansId, i);
                    c.put(DataBase.textAns, red1);
                    c.put(DataBase.true_false, box1);
                    dbhelp.update(DataBase.DATABASE_ANS, c, "Id = ? and qId = ? and ansId = ? ", new String[]{currTest, String.valueOf(currQues), String.valueOf(i)});
                    break;
                case 2:
                    c.clear();
                    c.put(DataBase.ID, currTest);
                    c.put(DataBase.qId, currQues);
                    c.put(DataBase.ansId, i);
                    c.put(DataBase.textAns, red2);
                    c.put(DataBase.true_false, box2);
                    dbhelp.update(DataBase.DATABASE_ANS, c, "Id = ? and qId = ? and ansId = ? ", new String[]{currTest, String.valueOf(currQues), String.valueOf(i)});
                    break;
                case 3:
                    c.clear();
                    c.put(DataBase.ID, currTest);
                    c.put(DataBase.qId, currQues);
                    c.put(DataBase.ansId, i);
                    c.put(DataBase.textAns, red3);
                    c.put(DataBase.true_false, box3);
                    dbhelp.update(DataBase.DATABASE_ANS, c, "Id = ? and qId = ? and ansId = ? ", new String[]{currTest, String.valueOf(currQues), String.valueOf(i)});
                    break;
                case 4:
                    c.clear();
                    c.put(DataBase.ID, currTest);
                    c.put(DataBase.qId, currQues);
                    c.put(DataBase.ansId, i);
                    c.put(DataBase.textAns, red4);
                    c.put(DataBase.true_false, box4);
                    dbhelp.update(DataBase.DATABASE_ANS, c, "Id = ? and qId = ? and ansId = ? ", new String[]{currTest, String.valueOf(currQues), String.valueOf(i)});
                    break;
                default:
                    break;
            }
        }
    }
}
