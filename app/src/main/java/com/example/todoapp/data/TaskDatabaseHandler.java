package com.example.todoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.todoapp.R;
import com.example.todoapp.model.TaskList;
import com.example.todoapp.model.UserAccounts;
import com.example.todoapp.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    public TaskDatabaseHandler(Context context) {
        super(context, Util.TASK_DATABASE_NAME, null, Util.TASK_DATABASE_VERSION);
    }

    /*
    Create database table -
    syntax - Create_table_name(id, username, password)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + Util.TASK_TABLE_NAME + "("
                + Util.KEY_TASK_ID + " INTEGER PRIMARY KEY," + Util.KEY_TASK + " TEXT,"
                + Util.KEY_TASK_DESCR + " TEXT," + Util.KEY_DATE_NAME + " LONG" + ")";

        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.TASK_DATABASE_NAME});

        /* recreate table */
        onCreate(db);

    }

    /* CREATE data */
    public void addTaskName(TaskList taskList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_TASK, taskList.getTaskName());
        values.put(Util.KEY_TASK_DESCR, taskList.getTaskDescription());
        values.put(Util.KEY_DATE_NAME, java.lang.System.currentTimeMillis());


        /* insert data into row */
        db.insert(Util.TASK_TABLE_NAME, null, values);
        Log.d("DbHandler", "addTask " + "task added" + "date added");
        db.close();
    }

    /* READ data */
    public TaskList getTaskList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TASK_TABLE_NAME,
                new String[]{Util.KEY_TASK_ID, Util.KEY_TASK, Util.KEY_TASK_DESCR, Util.KEY_DATE_NAME},
                Util.KEY_TASK_ID +"=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        TaskList taskList = new TaskList();

        taskList.setId(Integer.parseInt(cursor.getString(0)));
        taskList.setTaskName(cursor.getString(1));
        taskList.setTaskDescription(cursor.getString(2));

        /* format timestamp to be readable */
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong
                (cursor.getColumnIndex(Util.KEY_DATE_NAME))).getTime());

        taskList.setTimeStamp(formattedDate);

        return taskList;
    }

    /* return ALL tasks */

    public List<TaskList> getAllTaskList() {
        List<TaskList> taskLists = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TASK_TABLE_NAME,
                new String[]{Util.KEY_TASK_ID, Util.KEY_TASK_DESCR, Util.KEY_DATE_NAME,},
                            null, null, null, null,
                            Util.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                TaskList taskList = new TaskList();
                taskList.setId(Integer.parseInt(cursor.getString(0)));
                taskList.setTaskName(cursor.getString(1));
                taskList.setTaskDescription(cursor.getString(2));

                /* format timestamp to be readable */
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong
                        (cursor.getColumnIndex(Util.KEY_DATE_NAME))).getTime());

                taskList.setTimeStamp(formattedDate);


                /* add contact object to list */
                taskLists.add(taskList);

            } while (cursor.moveToNext());
        }

        return taskLists;
    }

    /*
    Update an account:
    update(table_name, values, where id = n )
     */
    public int updateTask(TaskList taskList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_TASK, taskList.getTaskName());
        values.put(Util.KEY_TASK_DESCR, taskList.getTaskDescription());
/*
        values.put(Util.KEY_DATE_NAME, taskList.getTimeStamp());
*/

        /* update row */
        return db.update(Util.TASK_TABLE_NAME, values, Util.KEY_TASK_ID + "=?",
                new String[]{String.valueOf(taskList.getId())});
    }

    /* DELETE an account */
    public void deleteTask(TaskList taskList) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TASK_TABLE_NAME, Util.KEY_TASK_ID + "=?",
                new String[]{String.valueOf(taskList.getId())});

        db.close();
    }

    /*
    get user count
    May use this for something
     */
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TASK_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
