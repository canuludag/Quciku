package com.quicku.translate.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.quicku.translate.BuildConfig;
import com.quicku.translate.utils.Constants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LastTranslatedWordsDatabase extends SQLiteOpenHelper {
    //TODO:cu Rewrite the database with Kotlin
    // Default database path
    private static String DATABASE_PATH = BuildConfig.DATABASE_PATH;
    // Database version
    private static int DATABASE_VERSION = Constants.DATABASE_VERSION;
    // Database name
    private static String DATABASE_NAME = Constants.DATABASE_NAME;
    // words table
    private static String COLUMN_WORDS_DATE = Constants.COLUMN_WORDS_DATE;
    private static String COLUMN_WORDS_TEXT = Constants.COLUMN_WORDS_TEXT;
    // Database path in the assets
    private static String DATABASE_ASSETS_PATH = Constants.DATABASE_ASSETS_PATH;
    // New database
    private SQLiteDatabase lastTranslatedWordsDb;
    private final Context context;

    // Default constructor
    public LastTranslatedWordsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Create a new database and get the data from DBWordlist.sqlite. Insert it
    // into new one
    public void createDatabase() throws IOException {

        // Checks if SQLite database is exist in the application. To avoid
        // re-creating it every time when the app installs
        boolean isDatabaseExist = checkDatabase();

        if (isDatabaseExist) {
            // Database already exist. So don't need to re-create it.
        } else {
            // With this method we'll be able create new empty database so we
            // can fill it with our own DBWordlist.sqlite database
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // For checking the database that if it exist
    public boolean checkDatabase() {

        SQLiteDatabase controlDatabase = null;

        try {
            String controlDatabasePath = DATABASE_PATH + DATABASE_NAME;
            controlDatabase = SQLiteDatabase.openDatabase(controlDatabasePath,
                    null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException sqle) {

        }

        // If there is a database that opened before, just close this instance
        // and dont create
        if (controlDatabase != null) {
            controlDatabase.close();
        }

        return controlDatabase != null ? true : false;
    }

    // copying existing sqlite database from assets to the newly created
    // database
    private void copyDatabase() throws IOException {

        // Open local database as an inputstream
        InputStream isDatabase = context.getAssets().open(DATABASE_ASSETS_PATH);
        // Path of newly created empty database
        String newDatabasePath = DATABASE_PATH + DATABASE_NAME;
        // Open database as an output stream
        OutputStream osDatabase = new FileOutputStream(newDatabasePath);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = isDatabase.read(buffer)) > 0) {
            osDatabase.write(buffer, 0, length);
        }

        // Close the streams
        osDatabase.flush();
        osDatabase.close();
        isDatabase.close();
    }

    // For openning new database
    public void openDatabase() throws SQLException {
        // Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        lastTranslatedWordsDb = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    // For closing new database
    @Override
    public synchronized void close() {
        if (lastTranslatedWordsDb != null)
            lastTranslatedWordsDb.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // For instering searched words to the database
    public void insertWord(String tableName, HashMap<String, String> queryValues) {
        // Getting the database that exist. It's read and writable
        SQLiteDatabase database = this.getWritableDatabase();
        String word = queryValues.get(COLUMN_WORDS_TEXT);
        String checkIfWordExistQuery = "SELECT * FROM " + tableName
                + " WHERE " + COLUMN_WORDS_TEXT + "='" + word + "'";
        Cursor c = database
                .rawQuery(checkIfWordExistQuery, null);

        if (c.getCount() == 0) {
            // To store data in it. Same as HashMap structure
            ContentValues contentValues = new ContentValues();

            // putting into correct table
            switch (tableName) {
                case "words":
                    Log.i("DATABASE", queryValues.get(COLUMN_WORDS_TEXT));

                    // put all the data
                    contentValues.put(COLUMN_WORDS_TEXT, queryValues.get(COLUMN_WORDS_TEXT));
                    contentValues.put(COLUMN_WORDS_DATE, getDateTime());
                    break;
            }

            // then insert the data into table
            long result = database.insert(tableName, null, contentValues);

            Log.i("DATABASE", "Result: " + result);
            Log.d("DATABASE", "getAllWords: " + getAllWords(tableName).size());

            database.close();

        } else {
            database.close();
        }
    }


    // For deleting every row inside the database
    public void deleteAllData(String tableName) {
        SQLiteDatabase database = this.getWritableDatabase();
        // just give the name of the table and give other paramaters null
        database.delete(tableName, null, null);
        database.close();
    }

    // For getting every word data inside database
    public ArrayList<HashMap<String, String>> getAllWords(String tableName) {
        // List for storing data
        ArrayList<HashMap<String, String>> wordsArrayList = new ArrayList<>();
        String selectAllWordsQuery = "SELECT * FROM " + tableName + " ORDER by " + COLUMN_WORDS_DATE + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();

        // get all the data inside Cursor object
        Cursor cursor = database.rawQuery(selectAllWordsQuery, null);

        Log.i("DATABASE", String.valueOf(cursor.getCount()));

        switch (tableName) {
            case "words": // Table name
                // Iterating through data inside the cursor and getting it to
                // HashMap
                if (cursor.moveToFirst()) {
                    do {
                        try {
                            HashMap<String, String> wordMap = new HashMap<>();
                            wordMap.put(COLUMN_WORDS_TEXT, cursor.getString(0));
                            wordsArrayList.add(0, wordMap);

                            Log.i("DATABASE", wordsArrayList.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } while (cursor.moveToNext());

                    cursor.close();
                }
                break;
        }

        database.close();

        return wordsArrayList;

    }

    // For getting last words by an amount
    public ArrayList<HashMap<String, String>> getLastWordsInAmount(int amount) {

        // List for storing data
        ArrayList<HashMap<String, String>> latestWordsList = new ArrayList<>();

        String selectLatestWordsQuery = "SELECT * FROM (SELECT * FROM "
                + "words_latest " + "ORDER by " + COLUMN_WORDS_DATE
                + " DESC limit" + String.valueOf(amount) + ") ORDER by " + COLUMN_WORDS_DATE + " DESC";

        SQLiteDatabase database = this.getReadableDatabase();

        // get all the data inside Cursor object
        Cursor cursor = database.rawQuery(selectLatestWordsQuery, null);

        // Iterating through data inside the cursor and getting it to
        // HashMap
        if (cursor.moveToFirst()) {

            do {
                HashMap<String, String> wordMap = new HashMap<>();
                wordMap.put(COLUMN_WORDS_TEXT, cursor.getString(0));
                latestWordsList.add(wordMap);
            } while (cursor.moveToNext());

            cursor.close();

        }
        database.close();

        return latestWordsList;
    }

    // For getting only one row of data from database
    public HashMap<String, String> getWord(String tableName, String word) {

        HashMap<String, String> wordMap = new HashMap<>();
        SQLiteDatabase database = this.getReadableDatabase();

        switch (tableName) {
            case "words_latest":
                String selectWordQueryLatest = "SELECT * FROM " + tableName
                        + " WHERE " + COLUMN_WORDS_TEXT + "='" + word + "'";
                Cursor cursorLatest = database
                        .rawQuery(selectWordQueryLatest, null);

                if (cursorLatest.moveToFirst()) {
                    do {
                        wordMap.put(COLUMN_WORDS_TEXT, cursorLatest.getString(0));
                    } while (cursorLatest.moveToNext());
                    cursorLatest.close();

                }

                break;
        }

        database.close();

        return wordMap;

    }
}
