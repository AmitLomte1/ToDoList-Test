package com.hfad.todolist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;



public class DbHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "JUSTPC";
    private static final int DB_VER = 1;
    public  static  final String DB_TABLE =  "Task";
    public static final String  DB_COLUMN = "TaskName";
    public static final String  DB_PRIORITY = "TaskPriority";
    public DbHelper(Context context) {
        super(context,DB_NAME,null,DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Task (_id INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT NOT NULL,TaskPriority TEXT NOT NULL);";
        db.execSQL(str);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(str);
        onCreate(db);
    }

    public void insertNewTask(String task, String Priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        values.put(DB_PRIORITY,Priority);
        db.insert(DB_TABLE,null,values);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,"TaskName = ?", new String[] {task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from Task order by TaskPriority";
        Cursor cursor = db.rawQuery(query,null);
        int index = cursor.getColumnIndex(DB_COLUMN);
        while(cursor.moveToNext()){
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
