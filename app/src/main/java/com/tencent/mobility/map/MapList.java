package com.tencent.mobility.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mobility.BaseActivity;
import com.tencent.mobility.R;
import com.tencent.mobility.util.ToastUtils;

public class MapList extends BaseActivity {

    static final int TYPE_COMPASS = 0;// 定位罗盘by marker
    static final int TYPE_COMPASS_SETTING = 1;// 罗盘by ui setting

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_list_recy);

        initRecy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ToastUtils.INSTANCE().init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ToastUtils.INSTANCE().destory();
    }

    private void initRecy() {

        recyclerView = findViewById(R.id.mobility_recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new MapRecy(new MapRecy.IClickListener() {
            @Override
            public void onClick(int itemPosition) {

                click(itemPosition);
            }
        }));

    }

    private void click(int position) {
        switch (position){
            case TYPE_COMPASS :
                toIntent(MapCompass.class);
                break;
            case TYPE_COMPASS_SETTING:
                toIntent(MapCompassBySetting.class);
                break;
        }
    }

    private void toIntent(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}
