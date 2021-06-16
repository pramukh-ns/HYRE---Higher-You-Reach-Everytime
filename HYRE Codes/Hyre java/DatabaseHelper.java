package com.example.hyreeee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "HYRE.db";
    public static final String TABLE_NAME = "LOGIN_table";
    public static final String col_1 = "USERNAME" ;
    public static final String col_2 = "PASSWORD" ;
    public static final String col_3 = "NAME" ;
    public static final String col_4 = "TYPE_OF_USER" ;
    private static final String login = "CREATE TABLE " +TABLE_NAME+ "(" +col_1 + " VARCHAR(255) PRIMARY KEY,"+col_2+
            " VARCHAR(255) ,"+ col_3 +" VARCHAR(255) ,"+col_4 +" VARCHAR(255) "+")";

    public DatabaseHelper(Context context ) {
        // super(context, name, factory, version);
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(login);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);
    }
    public boolean insertdata(String USERNAME, String PASSWORD,String NAME, String TYPE_OF_USER){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(col_1,USERNAME);
        contentValues.put(col_2,PASSWORD);
        contentValues.put(col_3,NAME);
        contentValues.put(col_4,TYPE_OF_USER);
        long result =  db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        ArrayList<String> buf = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+col_1 +","+ col_2+" from "+TABLE_NAME,null);
        if(res.moveToFirst()){
            
        }
        return res;
    }

}
