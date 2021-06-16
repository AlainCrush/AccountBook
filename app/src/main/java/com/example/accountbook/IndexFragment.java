package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IndexFragment extends Fragment{
    Spinner spinner;
    TextView textView1, textView2;
    TextView dt, dt1, dt2;
    TextView wt, wt1, wt2;
    TextView mt, mt1, mt2;
    TextView yt, yt1, yt2;
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

        Date date = new Date();
        String noyear = String.valueOf(date.getYear() + 1900);
        String nomonth = String.valueOf(date.getMonth()+1);
        textView1 = getActivity().findViewById(R.id.totalcost);
        String[] CostArgs = {"支出", noyear + "-" + nomonth + "-"+"%"};//模糊查询一定要加在字符串数组里加入 百分号%
        spinner = getActivity().findViewById(R.id.monthspinner);
        spinner.setSelection(date.getMonth(),true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String info=adapterView.getItemAtPosition(i).toString();//获取i所在的文本
                DBManager dbManager = new DBManager(getActivity());
                String[] spinnerArgs = {"支出", noyear + "-" + info + "-"+"%"};
                String spinnerlcost = String.valueOf(dbManager.CountSum(spinnerArgs).getMoney());
                textView1.setText(spinnerlcost);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DBManager dbManager = new DBManager(getActivity());
                String spinnerlcost = String.valueOf(dbManager.CountSum(CostArgs).getMoney());
                textView1.setText(spinnerlcost);
            }
        });

        DBManager db = new DBManager(getActivity());
        textView2 = getActivity().findViewById(R.id.monthlyincome);
        String[] IncomeArgs = {"收入", noyear + "-" + nomonth + "-"+"%"};
        String totalincome = String.valueOf(db.CountSum(IncomeArgs).getMoney());
        textView2.setText(totalincome);

        editText = getActivity().findViewById(R.id.monthlyavailability);
        String etext = editText.getText().toString();
        if (!etext.equals("0.00")) {
            editText.setText(String.valueOf(Float.valueOf(etext) - db.CountSum(CostArgs).getMoney()));
        }

        dt = getActivity().findViewById(R.id.latest);
        dt.setText("最近一笔 " + db.listLatest().getType() + " : " + db.listLatest().getRemarks() + " " + db.listLatest().getMoney());
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        dt1 = getActivity().findViewById(R.id.tdcost);
        String[] dt1Args = {"支出", dd.format(date)+"%"};

        dt1.setText(String.valueOf(db.CountSum(dt1Args).getMoney()));
        dt2 = getActivity().findViewById(R.id.tdincome);
        String[] dt2Args = {"收入", dd.format(date)+"%"};
        dt2.setText(String.valueOf(db.CountSum(dt2Args).getMoney()));

        wt = getActivity().findViewById(R.id.week);
        wt1 = getActivity().findViewById(R.id.weekcost);
        wt2 = getActivity().findViewById(R.id.weekincome);


        mt = getActivity().findViewById(R.id.month);
        mt1 = getActivity().findViewById(R.id.monthcost);
        mt1.setText(String.valueOf(db.CountSum(CostArgs).getMoney()));
        mt2 = getActivity().findViewById(R.id.monthincome);
        mt2.setText(String.valueOf(db.CountSum(IncomeArgs).getMoney()));


        yt = getActivity().findViewById(R.id.year);
        yt1 = getActivity().findViewById(R.id.yearcost);
        String[] yt1Args={"支出",noyear+"-"+"%"};
        yt1.setText(String.valueOf(db.CountSum(yt1Args).getMoney()));
        yt2 = getActivity().findViewById(R.id.yearincome);
        String[] yt2Args={"收入",noyear+"-"+"%"};
        yt2.setText(String.valueOf(db.CountSum(yt2Args).getMoney()));


    }
}
