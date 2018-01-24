package com.example.desktop.myproject.Activity;

import android.provider.BaseColumns;

/**
 * Created by Desktop on 23 ม.ค. 2561.
 */

public class StudentUser {

    public static final String TABLE = "user";

    public class Column {
        public static final String STUDENT_ID = BaseColumns._ID;
        public static final String STUDENT_PHONE = "phone";
        public static final String STUDENT_PASSWORD = "password";
    }

    private int id;
    private String phone;
    private String password;

    // Constructor
    public StudentUser(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public StudentUser() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}