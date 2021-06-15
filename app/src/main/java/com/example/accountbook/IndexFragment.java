package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

public class IndexFragment extends Fragment {
    Spinner spinner;
    TextView textView1,textView2;
    TextView dt,dt1,dt2;
    TextView wt,wt1,wt2;
    TextView mt,mt1,mt2;
    TextView yt,yt1,yt2;
    EditText editText;
    private static final String TAG = "IndexFragment";
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.frame_index, container, false);
        }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner =getActivity().findViewById(R.id.monthspinner);
        editText =getActivity().findViewById(R.id.monthlyavailability);
        textView1=getActivity().findViewById(R.id.totalcost);
        textView2=getActivity().findViewById(R.id.monthlyincome);

        dt = getActivity().findViewById(R.id.latest);
        dt1 = getActivity().findViewById(R.id.tdcost);
        dt2 = getActivity().findViewById(R.id.tdincome);

        wt = getActivity().findViewById(R.id.week);
        wt1 = getActivity().findViewById(R.id.weekcost);
        wt2 = getActivity().findViewById(R.id.weekincome);

        mt = getActivity().findViewById(R.id.month);
        mt1 = getActivity().findViewById(R.id.monthcost);
        mt2 = getActivity().findViewById(R.id.monthincome);

        yt = getActivity().findViewById(R.id.year);
        yt1 = getActivity().findViewById(R.id.yearcost);
        yt2 = getActivity().findViewById(R.id.yearincome);


        Date date = new Date();
        String noyear =String.valueOf(date.getYear()+1900);
        String monthstr = spinner.getSelectedItem().toString();

        DBManager db = new DBManager(getActivity());

        String[] IncomeArgs = {"收入",noyear+"-"+monthstr+"-"};
        String[] CostArgs = {"支出",noyear+"-"+monthstr+"-"};

        String totalcost =String.valueOf(db.CountSum(CostArgs).getMoney());
        String totalincome =String.valueOf(db.CountSum(IncomeArgs).getMoney());

        Log.i(TAG, "onActivityCreated: totalcost"+totalcost);
        Log.i(TAG, "onActivityCreated: totalincome"+totalincome);
        textView1.setText(totalcost);
        textView2.setText(totalincome);

        dt.setText("最近一笔 "+db.listLatest().getType()+" : "+db.listLatest().getRemarks()+" "+db.listLatest().getMoney());
        }
    }
