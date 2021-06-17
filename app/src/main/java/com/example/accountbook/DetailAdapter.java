package com.example.accountbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends ArrayAdapter {

    public DetailAdapter(@NonNull Context context, int resource, ArrayList<DBItem> dbItems) {
        super(context, resource, dbItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        DBItem list = (DBItem) getItem(position);
        TextView tw_date = (TextView) itemView.findViewById(R.id.query_date);
        TextView tw_type = (TextView) itemView.findViewById(R.id.query_type);
        TextView tw_money = (TextView) itemView.findViewById(R.id.query_money);
        TextView tw_remarks = (TextView) itemView.findViewById(R.id.query_remarks);
        tw_date.setText(list.getDate());
        tw_type.setText(list.getType());
        tw_money.setText(String.valueOf(list.getMoney()));
        tw_remarks.setText(list.getRemarks());

        return itemView;
    }
}