package com.ap.snehil.kosten.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ap.snehil.kosten.Database.MoneyContract.MoneyEntry;


/**
 * Created by HP on 16-08-2017.
 */

public class MoneyExchangeRecords extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME ="moneyExchangeReader.db";
public  MoneyExchangeRecords(Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_MONEY_TABLE =  "CREATE TABLE " + MoneyEntry.TABLE_NAME + " ("
                + MoneyEntry.COLUMN_PERSON_NAME + " TEXT NOT NULL, "
                + MoneyEntry.COLUMN__DATE + " TEXT, "
                + MoneyEntry.COLUMN__REASON + " TEXT, "
                + MoneyEntry.COLUMN_AMOUNT_BORROWED + " INTEGER NOT NULL, "
                + MoneyEntry.COLUMN_AMOUNT_GIVEN+ " INTEGER NOT NULL );";
       db.execSQL(SQL_MONEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
