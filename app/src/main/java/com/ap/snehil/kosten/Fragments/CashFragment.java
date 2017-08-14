package com.ap.snehil.kosten.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import com.ap.snehil.kosten.Adapters.CashListAdapter;
import com.ap.snehil.kosten.MainActivity;
import com.ap.snehil.kosten.Modals.Cash;
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

import static android.R.attr.data;
import static com.ap.snehil.kosten.Adapters.MyFragmentPagerAdapter.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashFragment extends Fragment implements View.OnClickListener {
    public static final String TAG="CashFragment";
  EditText etCost,etDate;
    Button btnUploadImg,btnAddToList,btnViewList;
    View rootView;
    Uri path;
    RecyclerView rvCashList;
    CashListAdapter cashListAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Cash> cashList=new ArrayList<>();
    final File sdcard = Environment.getExternalStorageDirectory();
    File file=new  File(sdcard,"cashlistfile.txt");
    public CashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_cash, container, false);
        etCost= (EditText) rootView.findViewById(R.id.etCost);
        etDate= (EditText) rootView.findViewById(R.id.etDate);
        btnUploadImg= (Button) rootView.findViewById(R.id.btnUploadImg);
        btnAddToList= (Button) rootView.findViewById(R.id.btnAddToList);
        btnViewList= (Button) rootView.findViewById(R.id.btnViewList);
        rvCashList= (RecyclerView) rootView.findViewById(R.id.rvCashList);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        rvCashList.setLayoutManager(new LinearLayoutManager(getActivity()));
        cashListAdapter = new CashListAdapter(getActivity(),cashList);
        rvCashList.setLayoutManager(mLayoutManager);
        rvCashList.setAdapter(cashListAdapter);
        btnUploadImg.setOnClickListener(this);
        btnAddToList.setOnClickListener(this);
        btnViewList.setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onClick(View v) {
             switch (v.getId()){
                 case R.id.btnUploadImg:
                     AddImage();
                     break;
                 case R.id.btnAddToList:
                     writeDataToFile(file,etCost.getText().toString(),etDate.getText().toString(),path.toString());

                     break;
                 case R.id.btnViewList:
                     readDataFromFile(new File(sdcard, "cashlistfile.txt"));
                     break;
                 default:
                     break;
             }
    }
    ArrayList<Cash> read (File file) throws IOException {
        Log.d(TAG, "read: Reading the file ****************");
        ArrayList<Cash> newList = new ArrayList<Cash>();
        File fileToRead = file;
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(fileToRead);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while(line != null){
                String s=" ",s1=" ",s2=" ";
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
                    if(x==2 && d=='a'){
                        char c =line.charAt(i);
                        if(line.charAt(i) !=' ') {
                            s2 = s2 + c;
                        }
                        else{
                            d=line.charAt(i);
                            x=x+1;

                        }

                    }

                }
                Log.d(TAG, "read: "+s+" "+s1+" "+s2);
                Uri path=Uri.parse(s2);
                newList.add(new Cash(s,s1,path));
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
        Log.d(TAG, "readDataFromFile: I am inside ********************");
        final String[] result = {""};
        PermissionManager.askForPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {
                        if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Log.d(TAG, "onGranted: Permission granted to read ******************");
                            try {
                                Log.d(TAG, "onGranted: file has permission to read");
                                cashList = read(file);
                                cashListAdapter.updateCashList(cashList);

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




    void write (File file,String s, String s1, String path) throws IOException {
        Log.d(TAG, "write: writing the file  ************************");
        File fileToWrite = file;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite,true));
        Log.d(TAG, "write: "+s+" "+s1+" "+path+" **************************");
        writer.write(s+" "+s1+" "+path);
        writer.newLine();
        writer.close();


    }
    private void writeDataToFile(final File file, final String s, final String s1, final String path) {
        Log.d(TAG, "writeDataToFile: I am inside *********************************************");
        PermissionManager.askForPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, new PermissionManager.OnPermissionResultListener() {
                    @Override
                    public void onGranted(String permission) {
                        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Log.d(TAG, "onGranted: permission gramted to write");
                            try {
                                Log.d(TAG, "onGranted: permission is granted");
                                write(file,s,s1,path);
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


    private void AddImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        this.startActivityForResult(intent, 0);
    }
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {

        if(resCode == Activity.RESULT_OK && data != null) {
            path = data.getData();
            Log.d(TAG, "onActivityResult: "+ path);
        }
    }


}
