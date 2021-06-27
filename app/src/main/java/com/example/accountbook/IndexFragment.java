package com.example.accountbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexFragment extends Fragment {
    Spinner spinner;
    TextView textView1, textView2, textView3, budget;
    TextView dt, dt1, dt2;
    TextView wt, wt1, wt2;
    TextView mt, mt1, mt2;
    TextView yt, yt1, yt2;
    String budget_SP_KEY = "Budget";
    ImageButton imb;


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
        show();
        imb = getActivity().findViewById(R.id.imageButtom);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

    }

    public void show() {
        Date date = new Date();
        String noyear = String.valueOf(date.getYear() + 1900);
        String nomonth = String.valueOf(date.getMonth() + 1);
        String noday = String.valueOf(date.getDate());
        Log.i("sd", "show: "+noyear);
        Log.i("sd", "show: "+nomonth);
        Log.i("sd", "show: "+noday);
        String[] CostArgs = {"支出", noyear + "-" + nomonth + "-" + "%"};//模糊查询一定要加在字符串数组里加入 百分号%
        textView1 = getActivity().findViewById(R.id.totalcost);
        DBManager db = new DBManager(getActivity());

        textView1.setText(String.valueOf(db.countSum(CostArgs).getMoney()));
        spinner = getActivity().findViewById(R.id.monthspinner);
        spinner.setSelection(date.getMonth(), true);
        //Spinner设置监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String info = adapterView.getItemAtPosition(i).toString();//获取i所在的文本
                String[] spinnerArgs = {"支出", noyear + "-" + info + "-" + "%"};
                String spinnercost = String.valueOf(db.countSum(spinnerArgs).getMoney());
                textView1.setText(spinnercost);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        textView2 = getActivity().findViewById(R.id.monthlyincome);
        String[] IncomeArgs = {"收入", noyear + "-" + nomonth + "-" + "%"};
        String totalincome = String.valueOf(db.countSum(IncomeArgs).getMoney());
        textView2.setText(totalincome);

        textView3 = getActivity().findViewById(R.id.monthlyavailability);
        //从SharePreferences里取数据
        SharedPreferences sp = getActivity().getSharedPreferences("Budget", Context.MODE_PRIVATE);
        String budgetText = sp.getString(budget_SP_KEY, "0.0");
        if (db.countSum(CostArgs).getMoney() == 0f) {
            textView3.setText("0.00");
        }
        String textStr = String.valueOf(Float.valueOf(budgetText) - db.countSum(CostArgs).getMoney());
        textView3.setText(textStr);

        //给预算设置监听，点击后可以通过对话框设置预算，可以设置以及重置
        budget = getActivity().findViewById(R.id.budget);
        budget.setClickable(true);
        budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getActivity());
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);//限制输入类型为数值型，且可以输入一位小数
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入预算:")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (editText.getText() != null) {
                                    float a = Float.valueOf(editText.getText().toString());
                                    String textStr = String.valueOf(a - db.countSum(CostArgs).getMoney());
                                    textView3.setText(textStr);
                                    //把这个预算存起来
                                    SharedPreferences sp = getActivity().getSharedPreferences("Budget", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString(budget_SP_KEY, String.valueOf(a));
                                    editor.apply();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setNeutralButton("重置预算", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp = getActivity().getSharedPreferences("Budget", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(budget_SP_KEY, "0.0");
                                editor.apply();
                                textView3.setText("0.0");
                            }

                        });
                builder.create().show();
            }
        });

        //处理今天部分
        dt = getActivity().findViewById(R.id.latest);
        //dt.setText("最近一笔 " + db.listLatest().getType() + " : " + db.listLatest().getRemarks() + " " + db.listLatest().getMoney());
        dt.setText(noyear+"-"+nomonth+"-"+noday);
        dt1 = getActivity().findViewById(R.id.tdcost);
        String[] dt1Args = {"支出", noyear + "-" + nomonth + "-" + noday + "%"};
        dt1.setText(String.valueOf(db.countSum(dt1Args).getMoney()));
        dt2 = getActivity().findViewById(R.id.tdincome);
        String[] dt2Args = {"收入", noyear + "-" + nomonth + "-" + noday + "%"};
        dt2.setText(String.valueOf(db.countSum(dt2Args).getMoney()));

        //处理本周部分
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        wt = getActivity().findViewById(R.id.week);
        //获得本周日期表
        GetWeeklyInterval gwi = new GetWeeklyInterval();
        List<Date> weekList = gwi.getWeekList();
        //转化成字符串表
        List<String> weekListStr = new ArrayList<String>();
        for (Date i : weekList) {
            weekListStr.add(sdf.format(i));
        }

        wt.setText(weekListStr.get(0) + "~" + weekListStr.get(6));
        wt1 = getActivity().findViewById(R.id.weekcost);
        wt1.setText(String.valueOf(db.weekSum("支出", weekListStr)));
        wt2 = getActivity().findViewById(R.id.weekincome);
        wt2.setText(String.valueOf(db.weekSum("收入", weekListStr)));

        //处理本月部分
        mt = getActivity().findViewById(R.id.month);
        mt.setText(nomonth + " 月");
        mt1 = getActivity().findViewById(R.id.monthcost);
        mt1.setText(String.valueOf(db.countSum(CostArgs).getMoney()));
        mt2 = getActivity().findViewById(R.id.monthincome);
        mt2.setText(String.valueOf(db.countSum(IncomeArgs).getMoney()));

        //处理本年部分
        yt = getActivity().findViewById(R.id.year);
        yt.setText(noyear + " 年");
        yt1 = getActivity().findViewById(R.id.yearcost);
        String[] yt1Args = {"支出", noyear + "-" + "%"};
        yt1.setText(String.valueOf(db.countSum(yt1Args).getMoney()));
        yt2 = getActivity().findViewById(R.id.yearincome);
        String[] yt2Args = {"收入", noyear + "-" + "%"};
        yt2.setText(String.valueOf(db.countSum(yt2Args).getMoney()));
    }
}
