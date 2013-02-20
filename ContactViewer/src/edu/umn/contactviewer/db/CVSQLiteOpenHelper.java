package edu.umn.contactviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

        if(!dbFile.exists()) {
            db = super.getWritableDatabase();
            copyDataBase(LOCAL_DATABASE_PATH, db.getPath());
        }
        return super.getWritableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if(!dbFile.exists()) {
            db = super.getReadableDatabase();
            copyDataBase(LOCAL_DATABASE_PATH, db.getPath());
        }
        return super.getReadableDatabase();
    }

    public synchronized void open() {
        getWritableDatabase();
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
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     *
     * TODO the contact table is not appearing in the android db on the device, viewed from monitor
     * 
     * @throws IOException
     */
    private void copyDataBase(String assetDBPath, String dbName) {

        try {
            InputStream assetDB = context.getAssets().open(assetDBPath);
            OutputStream appDB = new FileOutputStream(dbFile,false);

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
