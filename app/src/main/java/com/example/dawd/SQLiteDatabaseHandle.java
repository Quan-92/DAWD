package com.example.dawd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandle extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employeeData";
    private static final String TABLE_EMPLOYEE= "Employee";
    private static final String KEY_ID = "id";
    private static final String EMPLOYEE_NAME = "employeeName";
    private static final String EMPLOYEE_DESIGNATION = "employeeDesignation";
    private static final String EMPLOYEE_SALARY = "employeeSalary";

    public SQLiteDatabaseHandle(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + EMPLOYEE_NAME + " TEXT,"
                + EMPLOYEE_DESIGNATION + " TEXT,"
                + EMPLOYEE_SALARY + " DOUBLE" + ")";
        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_NAME, employee.getName());
        values.put(EMPLOYEE_DESIGNATION, employee.getDesignation());
        values.put(EMPLOYEE_SALARY, employee.getSalary());


        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
    }


    Employee getEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[] { KEY_ID,

                        EMPLOYEE_NAME, EMPLOYEE_DESIGNATION, EMPLOYEE_SALARY }, KEY_ID + "=?",
                new String[] { String.valueOf(id)
                }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee((cursor.getString(1)),
                cursor.getString(2), cursor.getDouble(3));
        return employee;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(0)));
                employee.setName(cursor.getString(1));
                employee.setDesignation(cursor.getString(2));
                employee.setSalary(cursor.getDouble(3));
                // Adding country to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        return employeeList;
    }

    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_NAME, employee.getName());
        values.put(EMPLOYEE_DESIGNATION, employee.getDesignation());
        values.put(EMPLOYEE_SALARY, employee.getSalary());


        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
    }

    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
        db.close();
    }

    public void deleteAllEmployees(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE,null,null);
        db.close();
    }
}