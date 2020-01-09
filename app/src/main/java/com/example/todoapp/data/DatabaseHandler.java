package com.example.todoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todoapp.R;
import com.example.todoapp.model.UserAccounts;
import com.example.todoapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    /* pass in DB name */
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    /*
    Create database table -
    syntax - Create_table_name(id, username, password)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERNAME_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_USERNAME + " TEXT,"
                + Util.KEY_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_USERNAME_TABLE);
    }

    /* Used for upgrading table version */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        /* recreate table */
        onCreate(db);
    }

    /* Implement CRUD operations */

    /* CREATE data */
    public void addUsername(UserAccounts userAccounts) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_USERNAME, userAccounts.getUserName());
        values.put(Util.KEY_PASSWORD, userAccounts.getPassword());


        /* insert data into row */
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DbHandler", "addUserAccount " + "item added");
        db.close();
    }

    /* READ data */
    public UserAccounts getUserAccounts(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_USERNAME, Util.KEY_PASSWORD},
                Util.KEY_ID +"=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UserAccounts userAccounts = new UserAccounts();

        userAccounts.setId(Integer.parseInt(cursor.getString(0)));
        userAccounts.setUserName(cursor.getString(1));
        userAccounts.setPassword(cursor.getString(2));

        return userAccounts;
    }

    /* return ALL accounts */

    public List<UserAccounts> getAllUserAccounts() {
        List<UserAccounts> userAccountsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                UserAccounts userAccounts = new UserAccounts();
                userAccounts.setId(Integer.parseInt(cursor.getString(0)));
                userAccounts.setUserName(cursor.getString(1));
                userAccounts.setPassword(cursor.getString(2));

                /* add contact object to list */
                userAccountsList.add(userAccounts);

            } while (cursor.moveToNext());
        }

        return userAccountsList;
    }
}
