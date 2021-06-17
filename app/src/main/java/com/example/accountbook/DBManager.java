package com.example.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;
    String date = "DATE";
    String money = "MONEY";
    String type = "TYPE";
    String remarks = "REMARKS";


    public DBManager(Context context) {

        this.dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    //添加记录
    public void addRecord(ArrayList<DBItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (DBItem item : list) {
            ContentValues values = new ContentValues();
            values.put(date, item.getDate());
            values.put(money, item.getMoney());
            values.put(type, item.getType());
            values.put(remarks, item.getRemarks());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public DBItem Find(String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, selection, selectionArgs, null, null, null);
        DBItem dbItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            dbItem = new DBItem();
            //通过游标查询索引来查询值
            dbItem.setDate(cursor.getString(cursor.getColumnIndex(date)));
            dbItem.setMoney(cursor.getFloat(cursor.getColumnIndex(money)));
            dbItem.setType(cursor.getString(cursor.getColumnIndex(type)));
            dbItem.setRemarks(cursor.getString(cursor.getColumnIndex(remarks)));

            cursor.close();///关闭游标
        } else {
            dbItem = new DBItem();
            dbItem.setDate("");
            dbItem.setMoney(0f);
            dbItem.setType("");
            dbItem.setRemarks("");
        }
        db.close();
        return dbItem;
    }


    public DBItem CountSum(String[] sqlArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select SUM(MONEY) from " + TBNAME + " where TYPE=? and DATE like ? group by ID";
        Cursor cursor = db.rawQuery(sql, sqlArgs);
        DBItem dbItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            dbItem = new DBItem();
            //通过游标查询索引来查询值
            dbItem.setMoney(cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)")));

            cursor.close();///关闭游标
        } else {
            dbItem = new DBItem();
            dbItem.setMoney(0f);
            Log.i("这里为空：", "CountSum: 空值");
        }
        db.close();
        return dbItem;
    }

    public DBItem listLatest() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        DBItem dbItem = null;
        if (cursor != null) {
            dbItem = Find("ID=?", new String[]{String.valueOf(cursor.getCount())});
        }
        cursor.close();///关闭游标
        db.close();
        return dbItem;
    }

    public List<DBItem> listAll() {
        List<DBItem> list = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if (cursor != null ) {
            list = new ArrayList<DBItem>();
            while (cursor.moveToNext()) {
                DBItem dbItem = new DBItem();
                dbItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                dbItem.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                dbItem.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                dbItem.setMoney(cursor.getFloat(cursor.getColumnIndex("MONEY")));
                dbItem.setRemarks(cursor.getString(cursor.getColumnIndex("REMARKS")));
                list.add(dbItem);
            }
            cursor.close();///关闭游标
        }
        db.close();
        return list;
    }
}