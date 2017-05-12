package com.example.jay.todo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseAccessToDo {
    private SQLiteDatabase database;
    private DatabaseOpenHelper openHelper;
    private static volatile DatabaseAccessToDo instance;

    private DatabaseAccessToDo(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static synchronized DatabaseAccessToDo getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessToDo(context);
        }
        return instance;
    }
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void saveToDo(ToDo toDo) {
        ContentValues values = new ContentValues();
        values.put("date", toDo.getTime());
        values.put("title", toDo.getTitle());
        values.put("description", toDo.getDescription());
        values.put("toDoDate", toDo.getTimeDueDate());
        values.put("status", toDo.getStatus());

        Log.e("DBA Time : ", String.valueOf(new Date(toDo.getTime())));
        Log.e("DBA DueDate : ", String.valueOf(toDo.getTimeDueDate()));
        Log.e("DBA Title : ",toDo.getTitle());

        database.insert(DatabaseOpenHelper.TABLE_TODO, null, values);

    }

    public void updateToDo(ToDo toDo) {
        ContentValues values = new ContentValues();
        values.put("date", toDo.getTime()); //different in Notes [values.put("date", new Date().getTime());]
        values.put("title", toDo.getTitle());
        values.put("description", toDo.getDescription());
        values.put("toDoDate", toDo.getToDoDate());
        values.put("status", toDo.getStatus());
        String date = Long.toString(toDo.getTime());
        database.update(DatabaseOpenHelper.TABLE_TODO, values, "date = ?", new String[]{date});
    }

    public void updateToDoStatus(ToDo toDo) {
        ContentValues values = new ContentValues();
        //values.put("date", toDo.getTime()); //different in Notes [values.put("date", new Date().getTime());]
        values.put("status", toDo.getStatus());
        String date = Long.toString(toDo.getTime());
        database.update(DatabaseOpenHelper.TABLE_TODO, values, "date = ?", new String[]{date});
    }

    public void deleteToDo(ToDo toDo) {
        String date = Long.toString(toDo.getTime());
        database.delete(DatabaseOpenHelper.TABLE_TODO, "date = ?", new String[]{date});
    }

    public List getAllToDos() {
        List toDoList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From todo ORDER BY date ASC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            long toDoDate = cursor.getLong(3);
            int status = cursor.getInt(4);
            toDoList.add(new ToDo(time, title,description,toDoDate,status));
            cursor.moveToNext();
        }
        cursor.close();
        return toDoList;
    }
}
