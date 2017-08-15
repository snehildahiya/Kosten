package com.ap.snehil.kosten.Fragments;


import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ap.snehil.kosten.Adapters.CashlessListAdapter;
import com.ap.snehil.kosten.Modals.Record;
import com.ap.snehil.kosten.Modals.SMSdata;
import com.ap.snehil.kosten.Permissions.PermissionManager;
import com.ap.snehil.kosten.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.path;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashlessFragment extends Fragment {
    public static final String TAG="cashless fragment";
    RecyclerView rvCashlessList;
    ArrayList<SMSdata> smsData= new ArrayList<>();
    CashlessListAdapter cashlessListAdapter;
    View rootView;
    static final File sdcard = Environment.getExternalStorageDirectory();
    static File file=new  File(sdcard,"CashlessListfile1.txt");
    public CashlessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_cashless, container, false);
        rvCashlessList= (RecyclerView) rootView.findViewById(R.id.rvCashlessList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);
        rvCashlessList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cashlessListAdapter = new CashlessListAdapter(smsData,this.getActivity());
        rvCashlessList.setLayoutManager(mLayoutManager);
        rvCashlessList.setAdapter(cashlessListAdapter);
        if(file.exists()){
            readDataFromFile(file);
        }
        else{
            writeDataToFile(file);
        }

        return rootView;
    }
    private File ReturnFile(File file){
        return file;
    }
    ArrayList<SMSdata> read (File file) throws IOException {
        Log.d(TAG, "read: now ready to read the sms ***************8888");
        ArrayList<SMSdata> newList = new ArrayList<SMSdata>();
        File fileToRead = file;
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(fileToRead);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while(line != null){
                String date=" ",cost=" ";
                int x=0;
                for(int i=0;i<line.length();i++)
                {
                    char c =line.charAt(i);
                    char d='a';
                        if(c ==' '){
                            x=x+1;
                        }
                        if(x<6){
                            date = date + c;
                        }
                      if(x>=6 && d !='a') {
                          d = line.charAt(i);
                      }

                    if(x>=6 && d=='a'){
                        if(c !=' ') {
                            cost = cost + c;
                        }

                    }

                }
                newList.add(new SMSdata(date,cost));
                line = reader.readLine();
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {

            }
        }
        return newList;
    }
    String readDataFromFile (final File file) {
        final String[] result = {""};
        PermissionManager.askForPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS
                }, new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {
                        if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            try {
                                Log.d(TAG, "onGranted: Permission to read the file given **********************");
                                smsData = read(file);
                                Log.d(TAG, "onGranted: "+smsData);
                                cashlessListAdapter.updateCashlessList(smsData);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(getActivity(),
                                "We can't do this without your permission",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        return result[0];
    }

    private void write (File file) throws IOException {
        Log.d(TAG, "write: file ready to be written ****************************");
        File fileToWrite = file;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite,true));
        Cursor c= getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null ,null,null);
        if(c.moveToFirst()) {
            for(int i=0; i < c.getCount(); i++) {
                int value = 0;
                if(c.getString(c.getColumnIndexOrThrow("body")).contains("Thank you for using your SBI Debit Card")){
                    String s =c.getString(c.getColumnIndexOrThrow("body")).toString();
                    String d=s.substring(71,80);
                    String y=" ";
                    for(int x=0 ;x<9;x++){
                        char C = d.charAt(x);
                        if(C!=' '){
                            if(value!=1){
                                y=y+C;}
                        }
                        else {
                            value = 1;
                        }
                    }
                    String date =  c.getString(c.getColumnIndex("date"));
                    Long timestamp = Long.parseLong(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timestamp);
                    Date finaldate = calendar.getTime();
                    String smsDate = finaldate.toString();
                    Log.d(TAG, "write: " +smsDate +" "+y+"******************************");
                    writer.write(smsDate+" "+y);
                    writer.newLine();
                }
                c.moveToNext();
            }
        }
        c.close();
        writer.close();
        readDataFromFile(file);

    }
       public static void GetIncomingMessage(String Date, String Value) throws IOException {
           File fileToWrite = file;
           BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite,true));
           writer.write(Date+" "+Value);
           writer.newLine();
           writer.close();
       }
    private void writeDataToFile(final File file) {
        Log.d(TAG, "writeDataToFile: i am inside write data to file");
        PermissionManager.askForPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS
                }, new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {
                        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE ) ){

                                try {
                                    Log.d(TAG, "onGranted: Going to write the file *******************");
                                    write(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(getActivity(),
                                "We can't do this without your permission",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
