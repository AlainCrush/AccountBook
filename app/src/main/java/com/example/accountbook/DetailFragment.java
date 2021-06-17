package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailFragment extends Fragment {
    TextView tw_date, tw_type, tw_money, tw_remarks;
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

        ListView listView = getView().findViewById(R.id.listView);
        DBManager db = new DBManager(getActivity());
        ArrayList<DBItem> list = (ArrayList<DBItem>) db.listAll();

        //设置适配器
        DetailAdapter adapter = new DetailAdapter(getActivity(), R.layout.list_item, list);
        listView.setAdapter(adapter);//

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager db = new DBManager(getActivity());
                ArrayList<DBItem> list = (ArrayList<DBItem>) db.listAll();
                DetailAdapter adapter = new DetailAdapter(getActivity(), R.layout.list_item, list);
                listView.setAdapter(adapter);
            }
        });
    }
}
