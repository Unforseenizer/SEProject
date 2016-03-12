package com.example.desktop.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventDataBaseAdapter {
    public static final int NAME_COLUMN = 1;
    static final String DATABASE_NAME = "Event.db";
    static final int DATABASE_VERSION = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "Event" +
            "( " + "ID" + " integer primary key autoincrement," + "EVENTNAME text, EVENTDATE text, EVENTLOCTION text, DESCRIPTION text); ";
    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public EventDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void close() {
        db.close();
    }

    public static String getSinlgeEntry(String userName) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public EventDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String eventname, String eventdate, String eventlocation, String description) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("EventName", eventname);
        newValues.put("EventDate", eventdate);
        newValues.put("EventLocation", eventlocation);
        newValues.put("Description", description);

        // Insert the row into your table
        db.insert("Event", null, newValues);
        newValues.clear();
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String UserName) {
        //String id=String.valueOf(ID);
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public void updateEntry(String eventname, String eventdate, String eventlocation, String description) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("EventName", eventname);
        updatedValues.put("EventDate", eventdate);
        updatedValues.put("EventLocation", eventlocation);
        updatedValues.put("Description", description);

        //String where="USERNAME = ?";
        //db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}
