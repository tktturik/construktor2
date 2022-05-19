package com.example.construktor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import static com.example.construktor.DataBase.arrQ;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button add,open;
    DialogFragment df;
    SharedPreferences sp;
    Set<String> aye;
    DataBase db;
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
        add.setOnClickListener(this);
        open = findViewById(R.id.button);
        df = new DialogFragm();
        //df.show(getSupportFragmentManager(),"df");
        open.setOnClickListener(this);
      // db = new DataBase(this,7);
      // dbhelp = db.getReadableDatabase();
      // dbhelp.delete(DataBase.DATABASE_ANS,null,null);
      // dbhelp.delete(DataBase.DATABASE_QUES,null,null);
      // dbhelp.delete(DataBase.DATABASE_NAME,null,null);






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
    public void load(){
            sp=getPreferences(MODE_PRIVATE);
            aye = sp.getStringSet("arr",new HashSet<>());
            try {
                for(String s:arrQ){
                    Log.d("ayeQ",s);
                }
            }catch (Exception e){

            }
            Log.d("aye","1");
            for(String s:aye){
                Log.d("aye",s);
            }
            //arrQ = new HashSet<>(aye);


        }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }
    void save(){
        sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putStringSet("arr",arrQ);
        ed.apply();
        Toast.makeText(this,"sohri",Toast.LENGTH_LONG).show();
        try{
            for(String s:arrQ){
                Log.d("aye2",s);
            }
        }catch (NullPointerException e){
        }
    }
}
