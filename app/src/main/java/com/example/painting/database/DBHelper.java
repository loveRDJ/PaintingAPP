package com.example.painting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //https://github.com/quocvn/food-sqlite-demo/blob/master/app/src/main/java/foodsqlitedemo/quocnguyen/com/foodsqlitedemo/FoodListAdapter.java

    //https://www.youtube.com/watch?v=cp2rL3sAFmI
    String TABLE_NAME = "PHOTOS";

    public DBHelper(Context context) {
        super(context,"PAINTING",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME +" (" +
                "ID INTEGER PRIMARY KEY, " +
                "NAME VARCHAR, " +
                "IMAGE BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, byte[] image){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("image",image);
        long result = database.insert(TABLE_NAME,null,values);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select*from "+TABLE_NAME,null);
//        return res;
//    }

    public boolean delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,"NAME = ?", new String[]{name});
        if(result==0){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from PHOTOS where NAME = ?", new String[]{name});
        return res;
    }
}
