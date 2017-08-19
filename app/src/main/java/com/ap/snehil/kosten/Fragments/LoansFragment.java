package com.ap.snehil.kosten.Fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ap.snehil.kosten.Adapters.BalanceListAdapter;
import com.ap.snehil.kosten.Database.MoneyContract;
import com.ap.snehil.kosten.Database.MoneyExchangeRecords;
import com.ap.snehil.kosten.EditorActivity;
import com.ap.snehil.kosten.MainActivity;
import com.ap.snehil.kosten.Modals.Record;
import com.ap.snehil.kosten.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoansFragment extends Fragment{
    //public class LoansFragment extends Fragment implements View.OnClickListener{
View rootView;
    TextView tvOwe,tvGet;
    RecyclerView rvBalanceList;
    BalanceListAdapter balanceListAdapter;
    LinearLayoutManager bLayoutManager;
    ArrayList<Record> BalanceList = new ArrayList<>();
    public static final String TAG="Loan Fragment";
    MoneyExchangeRecords moneyExchangeRecords;
    public LoansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_loans, container, false);
       rvBalanceList=(RecyclerView)rootView.findViewById(R.id.rvBalaListList);
        tvOwe=(TextView) rootView.findViewById(R.id.tvOwe);
        tvGet=(TextView) rootView.findViewById(R.id.tvGet);
        bLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        rvBalanceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        balanceListAdapter = new BalanceListAdapter(getActivity(),BalanceList);
        rvBalanceList.setLayoutManager(bLayoutManager);
        rvBalanceList.setAdapter(balanceListAdapter);
        FloatingActionButton fabAddNewEntery = (FloatingActionButton) rootView.findViewById(R.id.fabAddnNewEntery);
        fabAddNewEntery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                startActivityForResult(intent,1);
            }
        });

        moneyExchangeRecords = new MoneyExchangeRecords(getActivity());
        //insertEntery();
        displayDatabaseInfo();
        return rootView;
    }
//    private void insertEntery() {
//        // Read from input fields
//        // Use trim to eliminate leading or trailing white space
//
//        // Create database helper
//        // Gets the database in write mode
//        SQLiteDatabase db = moneyExchangeRecords.getWritableDatabase();
//
//        // Create a ContentValues object where column names are the keys,
//        // and pet attributes from the editor are the values.
//        ContentValues values = new ContentValues();
//        values.put(MoneyContract.MoneyEntry.COLUMN_PERSON_NAME,"nikita");
//        values.put(MoneyContract.MoneyEntry.COLUMN__DATE,"2/8/2017");
//        values.put(MoneyContract.MoneyEntry.COLUMN__REASON, "pizza");
//        values.put(MoneyContract.MoneyEntry.COLUMN_AMOUNT_BORROWED, "0");
//        values.put(MoneyContract.MoneyEntry.COLUMN_AMOUNT_GIVEN, "40");
//
//
//        // Insert a new row for pet in the database, returning the ID of that new row.
//        long newRowId = db.insert(MoneyContract.MoneyEntry.TABLE_NAME, null, values);
//
//        // Show a toast message depending on whether or not the insertion was successful
//        if (newRowId == -1) {
//            // If the row ID is -1, then there was an error with insertion.
//            Toast.makeText(getActivity(), "Error with saving entery", Toast.LENGTH_SHORT).show();
//        } else {
//            // Otherwise, the insertion was successful and we can display a toast with the row ID.
//            Toast.makeText(getActivity(), "Entery saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
//        }
//    }


    private void displayDatabaseInfo() {
        int owe=0,get=0;
// To access our database, we instantiate our subclass of SQLiteOpenHelper
// and pass the context, which is the current activity
        ArrayList<Record> newBalanceList = new ArrayList<>();
        // Create and/or open a database to read from it
        SQLiteDatabase db = moneyExchangeRecords.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        //Cursor cursor = db.rawQuery("SELECT * FROM " + MoneyContract.MoneyEntry.TABLE_NAME, null);
        Cursor cursor = db.query(
                MoneyContract.MoneyEntry.TABLE_NAME,   // The table to query
                null,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        Log.d("get", String.valueOf(cursor.getCount()));
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            //displayView.setText("Number of rows in pets database table: " + String.valueOf(cursor.getCount()));

            int nameColumnIndex = cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_PERSON_NAME);
            int dateColumnIndex = cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN__DATE);
            int reasonColumnIndex = cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN__REASON);
            int givenColumnIndex = cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_AMOUNT_GIVEN);
            int takenColumnIndex = cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_AMOUNT_BORROWED);

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String currentReason = cursor.getString(reasonColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentGiven = cursor.getString(givenColumnIndex);
                String currentTaken = cursor.getString(takenColumnIndex);
                owe=owe+Integer.valueOf(currentTaken);
                get=get+Integer.valueOf(currentGiven);
                // Display the values from each column of the current row in the cursor in the TextView
             newBalanceList.add(new Record(currentName,currentDate,currentReason,currentGiven,currentTaken));

            }
            BalanceList=newBalanceList;
           tvOwe.setText(String.valueOf(owe));
            tvGet.setText(String.valueOf(get));
            balanceListAdapter.updateOweList(BalanceList);

        } finally {
            // Always close the cursor when you're done reading from it. This releases all i
            // resources and makes it invalid.*/
            cursor.close();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayDatabaseInfo();
    }
}
