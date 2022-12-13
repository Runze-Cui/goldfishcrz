package com.example.goldfish;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HuancunSqlite extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table History ("
            + "uid integer primary key autoincrement, "
            + "pic integer, "
            + "checks text, "
            + "type text, "
            + "socre text, "
            + "id integer)";

    public HuancunSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
