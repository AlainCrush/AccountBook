package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Date;
import java.util.HashMap;

public class AddFragment extends Fragment implements DatePicker.OnDateChangedListener {
    Spinner spinner;
    EditText editText;
    EditText editTextArea;
    DatePicker dp;
    int year, monthOfYear, dayOfMonth;
    ArrayList<DBItem> record;

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
                String date = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
                record =new ArrayList<DBItem>();
                DBItem dbItem = new DBItem();
                dbItem.setDate(date);
                dbItem.setMoney(Float.valueOf(editText.getText().toString()));


                dbItem.setType(spinner.getSelectedItem().toString());
                dbItem.setRemarks(editTextArea.getText().toString());
                record.add(dbItem);

                //添加
                DBManager dbManager = new DBManager(getActivity());
                dbManager.addRecord(record);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("您已成功提交数据")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editText.setText("0.0");
                                editTextArea.setText("");

                            }
                        });
                builder.create().show();

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
