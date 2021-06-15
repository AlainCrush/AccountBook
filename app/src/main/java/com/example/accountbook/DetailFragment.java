package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    Button btquery,btupdate;
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
        btquery = getActivity().findViewById(R.id.detail_query);
        btupdate=getActivity().findViewById(R.id.detail_update);
        btquery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String selections = "DATE like ?";
                String selectionArgs[]={"2021-6-%"};
                DBManager db = new DBManager(getActivity());
                Log.i(TAG, "onClick: ++++++"+db.Find(selections,selectionArgs).getDate()+"有输出");

            }
        });

        }
    }
