package com.example.goldfish;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.goldfish.bean.Huancun;

import java.util.ArrayList;
import java.util.List;

public class HUancunDao {
    public static final String DB_NAME = "history_dbname";

    public static final int VERSION = 1;

    private static HUancunDao sqliteDB;

    private SQLiteDatabase db;

    private HUancunDao(Context context) {
        HuancunSqlite dbHelper = new HuancunSqlite(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }


    public synchronized static HUancunDao getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new HUancunDao(context);
        }
        return sqliteDB;
    }


    public void delete(Context context,String id) {
        HuancunSqlite dbHelper = new HuancunSqlite(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("History", "uid=?", new String[] { id });
    }
    public void change(Context context, Huancun history) {
        HuancunSqlite dbHelper = new HuancunSqlite(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", history.getId());
        values.put("uid", history.getUid());
        values.put("pic", history.getPic());
        values.put("checks", history.getCheck());
        values.put("socre", history.getDiff());
        values.put("type", history.getType());

        db.update("History", values, "uid=?", new String[]{history.getUid()+""});
    }


    public void  save(Huancun user) {
        try {
            db.execSQL("insert into History(pic,checks,type,socre,id) values(?,?,?,?,?) ", new String[]{
                    user.getPic()+"",
                    user.getCheck()+"",
                    user.getType(),
                    user.getDiff(),
                    user.getId()+"",


            });
        } catch (Exception e) {
            Log.d("����", e.getMessage().toString());
        }
    }
    @SuppressLint("Range")
    public List<Huancun> load(String type){
        List<Huancun> list = new ArrayList<>();
        Cursor cursor = db
                .query("History", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (type.equals(cursor.getString(cursor.getColumnIndex("type")))){
                    Huancun user = new Huancun();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                    user.setCheck(cursor.getString(cursor.getColumnIndex("checks")));
                    user.setType(cursor.getString(cursor.getColumnIndex("type")));
                    user.setDiff(cursor.getString(cursor.getColumnIndex("socre")));
                    user.setPic(cursor.getInt(cursor.getColumnIndex("pic")));
                    list.add(user);
                }

            } while (cursor.moveToNext());
        }
        return list;
    }
}
