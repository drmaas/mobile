package edu.umn.contactviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * User: drmaas
 * Date: 2/19/13
 *
 * Based on example found at http://www.vogella.com/articles/AndroidSQLite/article.html
 */
public class CVSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_TITLE = "contact_title";
    public static final String COLUMN_EMAIL = "contact_email";
    public static final String COLUMN_PHONE = "contact_phone";
    public static final String COLUMN_TWITTER = "contact_twitter";

    private static final String DATABASE_NAME = "contactviewer.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " +
            TABLE_CONTACT +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_TITLE + ", " +
            COLUMN_EMAIL + ", " +
            COLUMN_PHONE + ", " +
            COLUMN_TWITTER + ");";

    public CVSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String msg = "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data";
        Log.w(CVSQLiteOpenHelper.class.getName(), msg);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

}
