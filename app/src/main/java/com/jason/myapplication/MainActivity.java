package com.jason.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list_view;
    String[] address = new String[]{
            "http://img0.imgtn.bdimg.com/it/u=403792805,263717657&fm=214&gp=0.jpg",
            "http://img5.duitang.com/uploads/item/201411/06/20141106104720_WHEe2.jpeg",
            "http://i1.hdslb.com/bfs/archive/763293ce06bf1e684ef0ea3da43ae5008d8564b8.jpg",
            "http://image1.92bizhi.com/art_vector-widescreen--01_06-2560x1600.jpg",
            "http://image1.92bizhi.com/art_green-widescreen_01-2560x1600.jpg",
            "http://p0.ifengimg.com/pmop/2018/0211/34BB8479C0B68ADA393B11E0FFA12AFA73C41CC4_size330_w423_h236.gif",
            "http://img.zcool.cn/community/01c7415930cb03a8012193a313edff.gif"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_view = findViewById(R.id.list_view);
        ListAdapter adapter = new ListAdapter(this, address);
        list_view.setAdapter(adapter);


    }
}
