package com.example.construktor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=8;
    public static final String DATABASE_NAME="TablNazv";
    public static final String DATABASE_QUES="TablVop";
    public static final String DATABASE_ANS="TablOtv";
    public static final String NameTest = "nameTest";
    public static final String ID = "Id";
    public static final String qId = "qId";
    public static final String textQues = "ques";
    public static final String ansId = "ansId";
    public static final String textAns ="ans";
    public static final String true_false = "TrueFalse";
    public static final String nvop="kolQ";
    public static final String time="time";

    public DataBase(@Nullable Context context, int version) {
        super(context, null,null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_NAME + "(" + ID + " integer primary key AUTOINCREMENT,"+ NameTest + " text,"+ time +" integer," + nvop +" integer" +")");
        db.execSQL("create table " + DATABASE_QUES + "(" + ID + " integer,"+ qId + " integer,"+ textQues +" text" +")");
        db.execSQL("create table " + DATABASE_ANS + "(" + ID + " integer,"+ qId + " integer,"+ ansId + " integer,"+ textAns +" text,"+ true_false +" integer" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DATABASE_QUES);
        db.execSQL("drop table " + DATABASE_NAME);
        db.execSQL("drop table " + DATABASE_ANS);
        onCreate(db);
    }

}

