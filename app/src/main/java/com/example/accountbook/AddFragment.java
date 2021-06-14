package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddFragment extends Fragment implements DatePicker.OnDateChangedListener {
    Spinner spinner;
    EditText editText;
    EditText editTextArea;
    DatePicker dp;
    int year, monthOfYear, dayOfMonth;
    ArrayList<DBItem> record;
    private static final String TAG = "AddFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_add, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取Spinner控件    TYPE
        spinner = getActivity().findViewById(R.id.costspinner);
        //获取EditText控件 Sum、remarks
        editText = getActivity().findViewById(R.id.SUM);
        editTextArea=getActivity().findViewById(R.id.textArea);
        //获取DatePicker控件  Date
        dp = (DatePicker) getActivity().findViewById(R.id.adddatePicker);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dp.init(year,monthOfYear,dayOfMonth,this);

        Button button = getActivity().findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = String.valueOf(year)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(dayOfMonth);

                Log.i(TAG, "date: "+date);

                record =new ArrayList<DBItem>();
                DBItem dbItem = new DBItem();
                dbItem.setDate(date);
                dbItem.setSum(editText.getText().toString());
                dbItem.setType(spinner.getSelectedItem().toString());
                dbItem.setRemarks(editTextArea.getText().toString());
                record.add(dbItem);

                for(DBItem i : record ){
                    Log.i(TAG, "date: "+i.getDate()+" sum: "+i.getSum()+" type: "+i.getType()+" remarks: "+i.getRemarks());
                }

                //添加
                DBManager dbManager = new DBManager(getActivity());
                dbManager.addRecord(record);

                Log.i(TAG, "最新一条: "+"date: "+dbManager.listLatest().getDate()+" sum: "+dbManager.listLatest().getSum()+
                        " type: "+dbManager.listLatest().getType()+" remarks: "+dbManager.listLatest().getRemarks());

            }
        });
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        AddFragment.this.year = year;
        AddFragment.this.monthOfYear = monthOfYear;
        AddFragment.this.dayOfMonth = dayOfMonth;
    }


}
