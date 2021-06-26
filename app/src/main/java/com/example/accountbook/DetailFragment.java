package com.example.accountbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置长按事件处理
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除数据")
                        .setMessage("删除当前数据")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBItem dbItem = list.get(position);
                                db.delete(dbItem.getId());
                                list.remove(position);//删除数据
                                adapter.notifyDataSetChanged();//通知适配器已经删除了数据
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setNeutralButton("清空数据", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder bd = new AlertDialog.Builder(getActivity());
                                bd.setTitle("清空数据")
                                        .setMessage("清空所有数据")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.deleteAll();
                                                list.clear();
                                                adapter.notifyDataSetChanged();//通知适配器已经删除了数据
                                            }
                                        })
                                        .setNegativeButton("取消", null);
                                bd.create().show();

                            }

                        });
                builder.create().show();
            }
        });

        //通过刷新按钮刷新新添加的记录
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DBItem> list = (ArrayList<DBItem>) db.listAll();
                DetailAdapter adapter = new DetailAdapter(getActivity(), R.layout.list_item, list);
                listView.setAdapter(adapter);
            }
        });
    }
}
