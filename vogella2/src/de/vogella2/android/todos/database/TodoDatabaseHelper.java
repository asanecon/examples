package de.vogella2.android.todos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todotable.db";
    private static final int DATABASE_VERSION = 1;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called during creation of the database.
    @Override
    public void onCreate(SQLiteDatabase database) {
        TodoTable.onCreate(database);
    }

    // Method is called on upgrade.
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        TodoTable.onUpgrade(database, oldVersion, newVersion);
    }
}
