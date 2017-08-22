package com.ap.snehil.kosten.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.ap.snehil.kosten.Database.CashlessContract;
import com.ap.snehil.kosten.Database.CashlessDb;
import com.ap.snehil.kosten.Database.MoneyContract;
import com.ap.snehil.kosten.Database.MoneyExchangeRecords;
import com.ap.snehil.kosten.Fragments.CashlessFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by HP on 15-08-2017.
 */

public class SMSReader extends BroadcastReceiver {
    CashlessDb cashlessDb;

    // SmsManager class is responsible for all SMS related actions
    final SmsManager sms = SmsManager.getDefault();
    public static final String TAG = "pui";

    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: ");

        // Get the SMS message received
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                Log.d(TAG, "onCreateView: reading sms");
               // Cursor c= context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, "address=?" ,phoneNumber,null);
                Cursor c = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                c.moveToFirst();
                int value = 0;
                if (c.getString(c.getColumnIndexOrThrow("body")).contains("Thank you for using your SBI Debit Card")) {
                    String s = c.getString(c.getColumnIndexOrThrow("body")).toString();
                    String d = s.substring(71, 80);
                    Log.d(TAG, "onClick: " + d);
                    String y = " ";
                    for (int x = 0; x < 9; x++) {
                        char C = d.charAt(x);
                        Log.d(TAG, "onClick: " + C);
                        if (C != ' ') {
                            if (value != 1) {
                                y = y + C;
                            }
                            Log.d(TAG, "onClick: " + y);
                        } else {
                            value = 1;
                        }
                    }
                    String date =  c.getString(c.getColumnIndex("date"));
                    Long timestamp = Long.parseLong(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timestamp);
                    Date finaldate = calendar.getTime();
                    String smsDate = finaldate.toString();
                    Toast.makeText(context, "SMS RECEIVED:", Toast.LENGTH_LONG).show();
                    c.close();
                    cashlessDb = new CashlessDb(context);


                    SQLiteDatabase db = cashlessDb.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(CashlessContract.CashlessEntry.COLUMN__DATE, smsDate);
                    values.put(CashlessContract.CashlessEntry.COLUMN_EXPENDITURE, y);
                    db.insert(CashlessContract.CashlessEntry.TABLE_NAME, null, values);

                }



            }

        } catch(Exception e){
            e.printStackTrace();
        }



    }
}
