package com.example.desktop.myproject.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Desktop on 24 ม.ค. 2561.
 */

public class DatabaseStudent extends SQLiteOpenHelper implements UserManagerHelper{
    public static final String TAG = DatabaseStudent.class.getSimpleName();

    private static final String TABLE_STUDENT = "student";

    private static final String COLUMN_STUDENT_ID = "stu_id";
    private static final String COLUMN_STUDENT_PHONE = "stu_phone";
    private static final String COLUMN_STUDENT_PASSWORD = "stu_password";
    private SQLiteDatabase mDatabase;

    public DatabaseStudent(Context context){
        super(context, UserManagerHelper.DATABASE_NAME, null, UserManagerHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)" ,
                StudentUser.TABLE,
                StudentUser.Column.STUDENT_ID,
                StudentUser.Column.STUDENT_PHONE,
                StudentUser.Column.STUDENT_PASSWORD
        );
        db.execSQL(CREATE_TABLE_USER);

        Log.i(TAG, CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER  = "DROP TABLE IF EXISTS " + TABLE_STUDENT;
        db.execSQL(DROP_USER );
        Log.i(TAG, DROP_USER);
        onCreate(mDatabase);
    }

    @Override
    public long registerUser(StudentUser user) {
        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentUser.Column.STUDENT_PHONE, user.getPhone());
        values.put(StudentUser.Column.STUDENT_PASSWORD, user.getPassword());

        long result = mDatabase.insert(StudentUser.TABLE, null, values);
        mDatabase.close();

        return result;
    }

    @Override
    public StudentUser checkUserLogin(StudentUser user) {
        mDatabase = this.getReadableDatabase();

        Cursor cursor = mDatabase.query(StudentUser.TABLE,
                null,
                StudentUser.Column.STUDENT_PHONE + " = ? AND " +
                        StudentUser.Column.STUDENT_PASSWORD + " = ?",
                new String[]{user.getPhone(), user.getPassword()},
                null,
                null,
                null);

        StudentUser currentUser = new StudentUser();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                currentUser.setId((int) cursor.getLong(0));
                currentUser.setPhone(cursor.getString(1));
                currentUser.setPassword(cursor.getString(2));
                mDatabase.close();
                return currentUser;
            }
        }
        return null;
    }

    @Override
    public int changePassword(StudentUser user) {
        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentUser.Column.STUDENT_PHONE, user.getPhone());
        values.put(StudentUser.Column.STUDENT_PASSWORD, user.getPassword());

        int row = mDatabase.update(StudentUser.TABLE,
                values,
                StudentUser.Column.STUDENT_ID + " = ?",
                new String[] {String.valueOf(user.getId())});

        mDatabase.close();
        return row;
    }
}
