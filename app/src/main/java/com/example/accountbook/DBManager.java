package com.example.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;
    String date = "DATE";
    String sum = "SUM";
    String type = "TYPE";
    String remarks = "REMARKS";


    public DBManager(Context context){

        this.dbHelper = new DBHelper(context);
        TBNAME =DBHelper.TB_NAME;
    }

    //添加记录
    public void addRecord(ArrayList<DBItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(DBItem item : list){
            ContentValues values = new ContentValues();
            values.put(date,item.getDate());
            values.put(sum,item.getSum());
            values.put(type,item.getType());
            values.put(remarks,item.getRemarks());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    public DBItem FindByID(String selection,String[] selectionArgs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(TBNAME,null,selection,selectionArgs,null,null,null);
        DBItem dbItem = null;
        if (cursor!=null&&cursor.moveToFirst()){
            dbItem = new DBItem();

            //通过游标查询索引来查询值
            dbItem.setDate(cursor.getString(cursor.getColumnIndex(date)));
            dbItem.setSum(cursor.getString(cursor.getColumnIndex(sum)));
            dbItem.setType(cursor.getString(cursor.getColumnIndex(type)));
            dbItem.setRemarks(cursor.getString(cursor.getColumnIndex(remarks)));

            cursor.close();///关闭游标
        }
        db.close();
        return dbItem;
    }


    public DBItem listLatest(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        DBItem dbItem = null;
        if (cursor!=null){
            dbItem = FindByID("ID=?",new String[]{String.valueOf(cursor.getCount())});
            }
            cursor.close();///关闭游标
        db.close();
        return  dbItem;
    }
}
