package com.example.todoapp.util;



public class Util {

    /* user database items */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userAccounts_db";
    public static final String TABLE_NAME = "userAccounts";

    /* define user table columns */

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";

    /* task database items */
    public static final int TASK_DATABASE_VERSION = 1;
    public static final String TASK_DATABASE_NAME = "task_db";
    public static final String TASK_TABLE_NAME = "tasks";

    /* define task table columns */
    public static final  String KEY_TASK_ID = "taskId";
    public static final String KEY_TASK = "taskName";
    public static final String KEY_TASK_DESCR = "taskDescription";
    public static final String KEY_DATE_NAME = "timeStamp";






}