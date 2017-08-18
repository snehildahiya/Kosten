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
    RecyclerView rvBalanceList;
    BalanceListAdapter balanceListAdapter;
    LinearLayoutManager bLayoutManager;
    ArrayList<Record> BalanceList = new ArrayList<>();
    public static final String TAG="Loan Fragment";
    TextView displayView;
    MoneyExchangeRecords moneyExchangeRecords;
    public LoansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_loans, container, false);
        displayView= (TextView) rootView.findViewById(R.id.tvDisplay);
       rvBalanceList=(RecyclerView)rootView.findViewById(R.id.rvBalaListList);
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
                startActivity(intent);
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
// To access our database, we instantiate our subclass of SQLiteOpenHelper
// and pass the context, which is the current activity.
        MoneyExchangeRecords moneyExchangeRecords = new MoneyExchangeRecords(getActivity());

        // Create and/or open a database to read from it
        SQLiteDatabase db = moneyExchangeRecords.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + MoneyContract.MoneyEntry.TABLE_NAME, null);

        Log.d("get", String.valueOf(cursor.getCount()));
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            displayView.setText("Number of rows in pets database table: " + String.valueOf(cursor.getCount()));
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.*/
            cursor.close();
        }
    }

}
