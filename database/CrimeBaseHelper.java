package com.ehabhamdy.criminalintent2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ehabhamdy.criminalintent2.database.CrimeDbSchema.CrimeTable;

/**
 * Created by Ehab on 11/19/15.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crime-base.db";

    public CrimeBaseHelper(Context context){
        super(context, DATABASE_NAME, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CrimeTable.NAME + "(" +
                            "_id integer primary key autoincrement, "+
                            CrimeTable.cols.UUID + "," +
                            CrimeTable.cols.TITLE + "," +
                            CrimeTable.cols.DATE + "," +
                            CrimeTable.cols.SOLVED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
