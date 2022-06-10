package edu.csu.JustMusic.musicplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "music.db";
    public static final int VERSION = 1;
    private Context mContext;

    //建表语句定义成字符串常量
    public static final String CREATE_USER = "create table user ("
            + "account text primary key,"
            + "password text)";

    //创建DB对象时的构造函数
    public DataBaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
