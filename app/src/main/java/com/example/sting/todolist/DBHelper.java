package com.example.sting.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * This DBHelper handles the creation of the database and tables needed.
 * All operations such as adding and deleting tasks and changing task's status are
 * set up here.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToDoList.db";
    public static final String TASKS_TABLE_NAME = "tasks";
    public static final String TASKS_COLUMN_ID = "id";
    public static final String TASKS_COLUMN_TASK = "task";
    public static final String TASKS_COLUMN_DESCRIPTION = "description";
    public static final String TASKS_COLUMN_STATUS = "status";

    //Constructor to create an instance of DBHelper
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table tasks " +
                        "(id integer primary key autoincrement," +
                        " task text,description text,status text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    //Inserts new task in database
    public void insertTask (String task, String description, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("task", task);
        contentValues.put("description", description);
        contentValues.put("status", status);
        db.insert("tasks", null, contentValues);
    }

    //Returns the task searched by "id"
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    //Returns number of rows in table
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME);
        return numRows;
    }

    //Updates task with new status
    public void updateTaskStatus (Integer id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("status", status);
        db.update("tasks", contentValues, "id = ? ",
                new String[] { Integer.toString(id) } );
    }

    //Deletes a task
    public void deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks","id = ? ",new String[] { Integer.toString(id) });
    }

    //Returns all tasks stored in database
    public ArrayList<Tasks> getAllTasks() {
        ArrayList<Tasks> arrayList = new ArrayList<Tasks>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tasks", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            arrayList.add(new Tasks(res.getInt(res.getColumnIndex(TASKS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(TASKS_COLUMN_TASK)),
                    res.getString(res.getColumnIndex(TASKS_COLUMN_DESCRIPTION)),
                    res.getString(res.getColumnIndex(TASKS_COLUMN_STATUS))));
            res.moveToNext();
        }
        return arrayList;
    }
}
