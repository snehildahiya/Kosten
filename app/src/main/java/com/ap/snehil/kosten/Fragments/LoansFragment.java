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
public class LoansFragment extends Fragment{
    //public class LoansFragment extends Fragment implements View.OnClickListener{
View rootView;
    Button btnAddToOweList,btnAddToGetList;
    RecyclerView rvOweList,rvGetList;
    GetListAdapter getListAdapter;
    OweListAdapter oweListAdapter;
    LinearLayoutManager oLayoutManager,gLayoutManager;
    ArrayList<Record> OweList = new ArrayList<>();
    ArrayList<Record> GetList = new ArrayList<>();
    public static final String TAG="Loan Fragment";
    public LoansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_loans, container, false);
//        btnAddToOweList= (Button) rootView.findViewById(R.id.btnAddToOweList);
//        btnAddToGetList= (Button) rootView.findViewById(R.id.btnAddToGetList);
//        rvOweList=(RecyclerView)rootView.findViewById(R.id.rvOweList);
//        rvGetList=(RecyclerView)rootView.findViewById(R.id.rvGetList);
//        oLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
//        gLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
//        rvOweList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvGetList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        oweListAdapter = new OweListAdapter(getActivity(),OweList);
//        getListAdapter = new GetListAdapter(getActivity(),GetList);
//        rvOweList.setLayoutManager(oLayoutManager);
//        rvGetList.setLayoutManager(gLayoutManager);
//        rvOweList.setAdapter(oweListAdapter);
//        rvGetList.setAdapter(getListAdapter);
//        btnAddToOweList.setOnClickListener(this);
//        btnAddToGetList.setOnClickListener(this);

        return rootView;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//
//            case R.id.btnAddToOweList:
//                break;
//            case R.id.btnAddToGetList:
//
//                break;
//            default:
//                break;
//        }
//    }

}
