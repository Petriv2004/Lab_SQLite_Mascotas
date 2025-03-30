package com.example.listviewsadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 1;
    public static final String TABLE_DRINKS = "Drinks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_RESOURCE_ID = "imageResourceId";
    public StarbuzzDataBaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DRINKS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE_RESOURCE_ID + " INTEGER);";
        db.execSQL(createTable);
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
        insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
        insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, int imageResourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(COLUMN_NAME, name);
        drinkValues.put(COLUMN_DESCRIPTION, description);
        drinkValues.put(COLUMN_IMAGE_RESOURCE_ID, imageResourceId);
        db.insert(TABLE_DRINKS, null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINKS);
        onCreate(db);
    }

    public Cursor getAllDrinks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DRINKS, null, null, null, null, null, null);
    }

    public Cursor getDrinkById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_DRINKS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                "1" 
        );
    }
}
