package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.accountbook.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.viewPage2);
        MyPageAdapter pageAdapter = new MyPageAdapter(this);//适配器方法
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        String[] tbname = {"首页","记一笔","明细"};
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tbname[position])
        ).attach();
    }
}