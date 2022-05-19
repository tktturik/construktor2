package com.example.construktor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


import static com.example.construktor.DataBase.arrQ;


public class ListTest extends AppCompatActivity implements View.OnClickListener {
    ImageView nazad;
    ListView ls;
    public static ArrayAdapter ad;
    DataBase db;
    SQLiteDatabase dphelp;
    ArrayList<String > nazvaniya;
    SharedPreferences sp;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.listec);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ls=findViewById(R.id.list);
        nazad=findViewById(R.id.imageView);
        nazad.setOnClickListener(this);
        try {
            nazvaniya=new ArrayList<>(arrQ);
            ad = new ArrayAdapter<>(this, R.layout.razmetka, R.id.textView, nazvaniya);
            ls.setAdapter(ad);
        }catch (NullPointerException e){
            nazvaniya = new ArrayList<>();
            ad=new ArrayAdapter<>(this,R.layout.razmetka,R.id.textView,nazvaniya);
            ls.setAdapter(ad);
        }
    //  try {
    //      ad=new ArrayAdapter(this,R.layout.razmetka,R.id.textView,arrQ);
    //      ls.setAdapter(ad);
    //  }catch (NullPointerException e){

    //      arrQ = new ArrayList<>();
    //      ad=new ArrayAdapter(this,R.layout.razmetka,R.id.textView,arrQ);
    //      ls.setAdapter(ad);
    //  }
    }
    public void btnRemoveClick(View v)
    {
        final int position = ls.getPositionForView((View) v.getParent());
        String tablica = (String) ls.getItemAtPosition(position);
        db= new DataBase(this,7);
        dphelp = db.getReadableDatabase();
        dphelp.delete(tablica,null,null);
        dphelp.close();
        ad.remove(ad.getItem(position));
        arrQ.remove(tablica);
        save();
        ad.notifyDataSetChanged();
    }
    public void btnStartClick(View v){
        final int position = ls.getPositionForView((View) v.getParent());
        String tablica = (String) ls.getItemAtPosition(position);
        Intent inte2 = new Intent(this,Consturktor2.class);
        db= new DataBase(this,7);
        //inte.putExtra("tabl",tablica);
        startActivity(inte2);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imageView){
            Intent inte = new Intent(this, MainActivity.class);
            startActivity(inte);
            }
        }
    void save(){
         sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putStringSet("arr",arrQ);
        ed.commit();
        Toast.makeText(this,"sohri",Toast.LENGTH_LONG).show();
        try{
            for(String s:arrQ){
                Log.d("aye2",s);
            }
        }catch (NullPointerException e){
        }
    }


}

