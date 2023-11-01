package com.kimliu.additem2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));
        ArrayList<String> datalist = new ArrayList<>();
        datalist.add("摄像机");
        datalist.add("客厅");
        datalist.add("阳台");
        datalist.add("玄关");
        DataAdapter dataAdapter = new DataAdapter(this,datalist);
        mRv.setAdapter(dataAdapter);
    }
}