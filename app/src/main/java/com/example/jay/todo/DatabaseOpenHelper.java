package com.example.jay.todo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "stickknotee.db";
    public static final String TABLE_TODO = "todo";

    public static final int VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todo(date INTEGER PRIMARY KEY, title TEXT, description TEXT, toDoDate INTEGER, status INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
