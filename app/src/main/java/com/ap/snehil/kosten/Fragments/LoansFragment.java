package com.ap.snehil.kosten.Fragments;


import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ap.snehil.kosten.Adapters.GetListAdapter;
import com.ap.snehil.kosten.Adapters.OweListAdapter;
import com.ap.snehil.kosten.Modals.Record;
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

import static android.R.attr.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoansFragment extends Fragment implements View.OnClickListener{
View rootView;
    EditText etName,etAmount;
    Button btnAddToOweList,btnAddToGetList;
    RecyclerView rvOweList,rvGetList;
    GetListAdapter getListAdapter;
    OweListAdapter oweListAdapter;
    LinearLayoutManager oLayoutManager,gLayoutManager;
    ArrayList<Record> OweList = new ArrayList<>();
    ArrayList<Record> GetList = new ArrayList<>();
    final File sdcard = Environment.getExternalStorageDirectory();
    File fileOwe=new  File(sdcard,"owelistfile.txt");
    File fileGet=new  File(sdcard,"getlistfile.txt");
    public static final String TAG="Loan Fragment";
    public LoansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_loans, container, false);
        etAmount= (EditText) rootView.findViewById(R.id.etAmount);
        etName= (EditText) rootView.findViewById(R.id.etName);
        btnAddToOweList= (Button) rootView.findViewById(R.id.btnAddToOweList);
        btnAddToGetList= (Button) rootView.findViewById(R.id.btnAddToGetList);
        rvOweList=(RecyclerView)rootView.findViewById(R.id.rvOweList);
        rvGetList=(RecyclerView)rootView.findViewById(R.id.rvGetList);
        oLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        gLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        rvOweList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGetList.setLayoutManager(new LinearLayoutManager(getActivity()));
        oweListAdapter = new OweListAdapter(getActivity(),OweList);
        getListAdapter = new GetListAdapter(getActivity(),GetList);
        rvOweList.setLayoutManager(oLayoutManager);
        rvGetList.setLayoutManager(gLayoutManager);
        rvOweList.setAdapter(oweListAdapter);
        rvGetList.setAdapter(getListAdapter);
        btnAddToOweList.setOnClickListener(this);
        btnAddToGetList.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnAddToOweList:
                Log.d(TAG, "onClick: ***********************888");
                writeDataToFile(fileGet,etName.getText().toString(),etAmount.getText().toString(),0);
                break;
            case R.id.btnAddToGetList:
                writeDataToFile(fileOwe,etName.getText().toString(),etAmount.getText().toString(),1);

                break;
            default:
                break;
        }
    }
    ArrayList<Record> read (File file) throws IOException {
        Log.d(TAG, "read: Now reading the file **************88888");
        ArrayList<Record> newList = new ArrayList<Record>();
        File fileToRead = file;
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(fileToRead);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while(line != null){
                String s=" ",s1=" ";
                int x=0;
                for(int i=0;i<line.length();i++)
                {
                    char d='a';
                    if(x==0 ){
                        char c =line.charAt(i);
                        if(line.charAt(i) !=' ') {
                            s = s + c;
                        }
                        else{
                            d=line.charAt(i);
                            x=x+1;
                        }

                    }
                    if(x==1 && d=='a'){
                        char c =line.charAt(i);
                        if(line.charAt(i) !=' ') {
                            s1 = s1 + c;
                        }
                        else{
                            d=line.charAt(i);
                            x=x+1;

                        }

                    }

                }
                Log.d(TAG, "read: "+s+" "+s1);
                newList.add(new Record(s,s1));
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
    String readDataFromFile (final File file,final int x) {
        Log.d(TAG, "readDataFromFile: i am inside *********************");
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
                            Log.d(TAG, "onGranted: Permission granted to read***********888");
                            try {
                                if(x==1){
                                    OweList = read(file);
                                    Log.d(TAG, "onGranted: "+OweList);
                                    oweListAdapter.updateOweList(OweList);
                                }
                                if(x==0) {
                                    GetList = read(file);
                                    Log.d(TAG, "onGranted: "+GetList);
                                    getListAdapter.updateGetList(GetList);
                                }
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



    private void write(File fileOwe, String s, String s1,int x) throws IOException {
        Log.d(TAG, "write: now writing file ***************************");
        File fileToWrite2 = fileOwe;
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(fileToWrite2,true));
        writer2.write(s+" "+s1);
        writer2.newLine();
        writer2.close();
        if(x==0){
            readDataFromFile(new File(sdcard, "getlistfile.txt"),0);

        }
        if(x==1){
            readDataFromFile(new File(sdcard, "owelistfile.txt"),1);
        }


    }
    private void writeDataToFile(final File fileOwe, final String s, final String s1,final int x) {
        Log.d(TAG, "writeDataToFile: i am inside *****************8");
        PermissionManager.askForPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS
                }, new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {
                        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Log.d(TAG, "onGranted: permission granted************************");
                            try {
                                write(fileOwe,s,s1,x);
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
