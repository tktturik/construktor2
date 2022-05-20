package com.example.construktor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.construktor.Activity2.db;



public class ListTest extends AppCompatActivity implements View.OnClickListener {
    ImageView nazad;
    ListView ls;
    public static ArrayAdapter ad;
    SQLiteDatabase dphelp;
    ArrayList<String > nazvaniya;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.listec);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ls = findViewById(R.id.list);

        nazad = findViewById(R.id.imageView);
        nazad.setOnClickListener(this);

        dphelp = db.getReadableDatabase();
        String query = "select TablNazv.nameTest from TablNazv";
        Cursor cur = dphelp.rawQuery(query, null);
        logCursor(cur);
        cur.moveToFirst();

        try {
            if (cur.moveToFirst() ) {
                do {
                    int idNameTest = cur.getColumnIndex(DataBase.NameTest);
                    nazvaniya.add(cur.getString(idNameTest));
                } while (cur.moveToNext());
                cur.close();
                ad = new ArrayAdapter<>(this, R.layout.razmetka, R.id.textView, nazvaniya);
                ls.setAdapter(ad);
            }
        } catch (Exception e) {
            nazvaniya = new ArrayList<>();
            if (cur.moveToFirst()) {
                do {
                    int idNameTest = cur.getColumnIndex(DataBase.NameTest);
                    nazvaniya.add(cur.getString(idNameTest));
                } while (cur.moveToNext());
                cur.close();
            }
                ad = new ArrayAdapter<>(this, R.layout.razmetka, R.id.textView, nazvaniya);
                ls.setAdapter(ad);

        }
    }
    public void btnRemoveClick(View v)
    {
        final int position = ls.getPositionForView((View) v.getParent());
        String tablica = (String) ls.getItemAtPosition(position);
        dphelp = db.getReadableDatabase();
        Cursor cur = dphelp.rawQuery("select TablNazv.Id from TablNazv where nameTest = ?",new String[]{tablica});
        cur.moveToFirst();
        int idDelet = cur.getColumnIndex(DataBase.ID);
        int idDeleti = cur.getInt(idDelet);

        dphelp.delete(DataBase.DATABASE_NAME,"Id = " + String.valueOf(idDeleti),null);
        dphelp.delete(DataBase.DATABASE_QUES, "Id = " + String.valueOf(idDeleti),null);
        dphelp.delete(DataBase.DATABASE_ANS, "Id = "+String.valueOf(idDeleti),null);
        cur = dphelp.rawQuery("select * from TablNazv",null);
        logCursor(cur);
        cur= dphelp.rawQuery("select * from TablVop",null);
        logCursor( cur);
        cur = dphelp.rawQuery("select * from TablOtv",null);
        logCursor(cur);
        cur.close();
        ad.remove(ad.getItem(position));
        nazvaniya.remove(tablica);
        ad.notifyDataSetChanged();
    }
    public void btnStartClick(View v){
        final int position = ls.getPositionForView((View) v.getParent());
        String tablica = (String) ls.getItemAtPosition(position);
        String query2  = "select TablNazv.Id from TablNazv where TablNazv.nameTest = ?";
        Cursor cur2 = dphelp.rawQuery(query2,new String[]{tablica});
        cur2.moveToFirst();
        int idTest = cur2.getColumnIndex(DataBase.ID);
        String id = String.valueOf(cur2.getInt(idTest));
        Intent inte2 = new Intent(this,Consturktor2.class);
        inte2.putExtra("id",id);
        cur2.close();
        startActivity(inte2);
    }
    public void btnUpdate(View v){
        final int position = ls.getPositionForView((View) v.getParent());
        String tablica = (String) ls.getItemAtPosition(position);
        String query3  = "select TablNazv.Id from TablNazv where TablNazv.nameTest = ?";
        Cursor cur3 = dphelp.rawQuery(query3,new String[]{tablica});
        cur3.moveToFirst();
        int idTest = cur3.getColumnIndex(DataBase.ID);
        String id = String.valueOf(cur3.getInt(idTest));
        Intent inte3 = new Intent(this,Construktor3.class);
        inte3.putExtra("id",id);
        cur3.close();
        startActivity(inte3);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imageView){
            Intent inte = new Intent(this, MainActivity.class);
            startActivity(inte);
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

