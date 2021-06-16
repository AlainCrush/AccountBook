package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailFragment extends Fragment {
    Button btupdate;
    TextView tw_date,tw_type,tw_money,tw_remarks;
    private static final String TAG = "DetailFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frame_detail, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tw_date = getActivity().findViewById(R.id.query_date);
        tw_type = getActivity().findViewById(R.id.query_type);
        tw_money = getActivity().findViewById(R.id.query_money);
        tw_remarks = getActivity().findViewById(R.id.query_remarks);
        btupdate = getActivity().findViewById(R.id.detail_update);

        DBManager db = new DBManager(getActivity());
        ArrayList<DBItem> list = (ArrayList<DBItem>)db.listAll();

        btupdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        }
    }
