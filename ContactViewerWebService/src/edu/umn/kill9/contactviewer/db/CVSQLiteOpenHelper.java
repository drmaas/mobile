package edu.umn.kill9.contactviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;

/**
 * User: drmaas
 * Date: 2/19/13
 *
 * Based on example found at http://www.vogella.com/articles/AndroidSQLite/article.html
 *
 * Seeding the DB is based on http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 *
 * Here the DAO pattern is implemented, could also use ContentProvider for a shared DB
 */
public class CVSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_ID = "_id"; //I think this has to be _id for android to understand it?
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_TITLE = "contact_title";
    public static final String COLUMN_EMAIL = "contact_email";
    public static final String COLUMN_PHONE = "contact_phone";
    public static final String COLUMN_TWITTER = "contact_twitter";

    private static final String DATABASE_NAME = "contactviewer.db";
    private static final int DATABASE_VERSION = 1;
    private static String LOCAL_DATABASE_PATH = "databases/" + DATABASE_NAME;

    private SQLiteDatabase db;

    private File dbFile;

    private final Context context;

    public CVSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbFile = context.getDatabasePath(DATABASE_NAME);
        this.context = context;
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        String dbname = this.dbFile.getAbsolutePath();
        if(!checkDataBase(dbname)) {
            copyDataBase(LOCAL_DATABASE_PATH, dbname);
        }
        db = super.getWritableDatabase();
        return db;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        String dbname = this.dbFile.getAbsolutePath();
        if(!checkDataBase(dbname)) {
            copyDataBase(LOCAL_DATABASE_PATH, dbname);
        }
        db = super.getReadableDatabase();
        return db;
    }

    @Override
    public synchronized void close() {
        if(db != null) {
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * utility for starting a transaction
     */
    public void startTransaction() {
        db.beginTransaction();
    }

    /**
     * Utility for committing all changes
     */
    public void commit() {
        try {
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    /**
     * Utility for rolling back a transaction
     */
    public void rollback() {
        db.endTransaction();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(String dbPath) {

        SQLiteDatabase checkDB = null;

        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e) {
            //database does't exist yet.
        }

        if(checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring bytestream.
     *
     * @throws IOException
     */
    private void copyDataBase(String assetDBPath, String dbName) {

        try {
            InputStream assetDB = context.getAssets().open(assetDBPath);

            //hack to create databases directory if not there
            new File(dbFile.getParent()).mkdir();

            OutputStream appDB = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = assetDB.read(buffer)) != -1) {
                appDB.write(buffer, 0, length);
            }

            appDB.flush();
            appDB.close();
            assetDB.close();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

}
