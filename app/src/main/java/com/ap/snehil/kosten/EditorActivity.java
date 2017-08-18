package com.ap.snehil.kosten;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ap.snehil.kosten.Database.MoneyContract;
import com.ap.snehil.kosten.Database.MoneyExchangeRecords;

public class EditorActivity extends AppCompatActivity {
EditText etName,etDate,etReason,etGiven,etTaken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        etName= (EditText) findViewById(R.id.etName);
        etDate= (EditText) findViewById(R.id.etDate);
        etReason= (EditText) findViewById(R.id.etPurpose);
        etGiven= (EditText) findViewById(R.id.etGiven);
        etTaken= (EditText) findViewById(R.id.etTaken);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    private void insertEntery() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = etName.getText().toString().trim();
        String DateString = etDate.getText().toString().trim();
        String ReasonString = etReason.getText().toString().trim();
        String GivenString = etGiven.getText().toString().trim();
        String TakenString = etTaken.getText().toString().trim();

        // Create database helper
        MoneyExchangeRecords moneyExchangeRecords = new MoneyExchangeRecords(this);

        // Gets the database in write mode
        SQLiteDatabase db = moneyExchangeRecords.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(MoneyContract.MoneyEntry.COLUMN_PERSON_NAME, nameString);
        values.put(MoneyContract.MoneyEntry.COLUMN__DATE, DateString);
        values.put(MoneyContract.MoneyEntry.COLUMN__REASON, ReasonString);
        values.put(MoneyContract.MoneyEntry.COLUMN_AMOUNT_BORROWED, TakenString);
        values.put(MoneyContract.MoneyEntry.COLUMN_AMOUNT_GIVEN, GivenString);


        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(MoneyContract.MoneyEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving entery", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Entery saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertEntery();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
