package com.example.esmeralda.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Esmeralda on 15.5.2016..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tlak.db";
    public static final String TABLE_NAME = "tlak_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "sistolicki";
    public static final String COL_3 = "dijastolicki";
    public static final String COL_4 = "puls";
    public static final String COL_5 = "datum";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy");
    String currentDateandTime = sdf.format(new Date());

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, sistolicki INTEGER, dijastolicki INTEGER, puls INTEGER, datum INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int sistolicki,int dijastolicki,int puls) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,sistolicki);
        contentValues.put(COL_3,dijastolicki);
        contentValues.put(COL_4,puls);
        contentValues.put(COL_5, currentDateandTime);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME +" ORDER BY ID DESC", null);
        return res;
    }

    public Cursor getZadnjaTri(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME +" ORDER BY ID DESC LIMIT 3", null);
        return res;
    }

}
