package com.ap.snehil.kosten.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 22-08-2017.
 */

public class CashlessDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String TAG="Database";
    public static final String DATABASE_NAME ="cash_cashlessDb.db";

    public CashlessDb(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CASHLESS_TABLE =  "CREATE TABLE " + CashlessContract.CashlessEntry.TABLE_NAME + " ("
                + CashlessContract.CashlessEntry.COLUMN__DATE + " TEXT, "
                + CashlessContract.CashlessEntry.COLUMN_EXPENDITURE+ " TEXT );";
        String SQL_CASH_TABLE =  "CREATE TABLE " + CashlessContract.CashEntry.TABLE_NAME_CASH + " ("
                + CashlessContract.CashEntry.COLUMN_DATE_CASH + " TEXT, "
                + CashlessContract.CashEntry.COLUMN_EXPENDITURE_CASH+ " TEXT, "
                + CashlessContract.CashEntry.COLUMN_IMAGE+ " TEXT );";

        db.execSQL(SQL_CASHLESS_TABLE);
        db.execSQL(SQL_CASH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
