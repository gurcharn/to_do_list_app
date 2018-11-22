package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TaskDbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyTask.db";
    private final String TASKS_TABLE_NAME = "tasks";
    private final String TASKS_COLUMN_ID = "id";
    private final String TASKS_COLUMN_NAME = "name";
    private final String TASKS_COLUMN_PLACE = "place";
    private final String TASKS_COLUMN_MESSAGE = "message";
    private final String create_table = "create table " + TASKS_TABLE_NAME +"(" +
                                                TASKS_COLUMN_ID + " integer primary key autoincrement, " +
                                                TASKS_COLUMN_NAME + " text," +
                                                TASKS_COLUMN_PLACE + " text, " +
                                                TASKS_COLUMN_MESSAGE + " text" +
                                                ")";
    private final String drop_table = "drop table " + TASKS_TABLE_NAME;

    private final SQLiteDatabase getWritableDB = this.getWritableDatabase();
    private final SQLiteDatabase getReadableDB = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();

    TaskDbOpenHelper(Context c){
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_table);
        db.execSQL(create_table);
    }

    public boolean insertTask(TaskItem taskItem) {
        try {
            contentValues.put(TASKS_COLUMN_NAME, taskItem.getName());
            contentValues.put(TASKS_COLUMN_PLACE, taskItem.getPlace());
            contentValues.put(TASKS_COLUMN_MESSAGE, taskItem.getMessage());
            getWritableDB.insert(TASKS_TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<TaskItem> getAllTasks() {
        ArrayList<TaskItem> taskItems = new ArrayList<TaskItem>();
        Cursor cursor = getReadableDB.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
        cursor.moveToFirst();

        for (int i=0 ; i< cursor.getCount(); i++){
            taskItems.add(new TaskItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }

        return taskItems;
    }

    public TaskItem getTask(int id) {
        Cursor cursor = getReadableDB.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE " + TASKS_COLUMN_ID + "=" + id, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return new TaskItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }else {
            return new TaskItem();
        }
    }

    public boolean updateTask(TaskItem taskItem) {
        contentValues.put(TASKS_COLUMN_NAME, taskItem.getName());
        contentValues.put(TASKS_COLUMN_PLACE, taskItem.getPlace());
        contentValues.put(TASKS_COLUMN_MESSAGE, taskItem.getMessage());
        getWritableDB.update(TASKS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(taskItem.getId()) } );
        return true;
    }

    public boolean deleteTask(int id) {
        getWritableDB.delete(TASKS_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public boolean resetDb(){
        onUpgrade(getReadableDB, 0, 0);
        return true;
    }
}
