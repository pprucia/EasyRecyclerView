package com.jude.dome.horizontal;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jude.dome.DataProvider;
import com.jude.dome.R;
import com.jude.dome.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

/**
 * Created by zhuchenxi on 16/9/19.
 */

public class HorizontalActivity extends AppCompatActivity {
    private EasyRecyclerView recyclerView;
    private NarrowImageAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter = new NarrowImageAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new SpaceDecoration((int) Utils.convertDpToPixel(8,this)));
        adapter.setMore(R.layout.view_more_horizontal, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(DataProvider.getNarrowImage(0));
                    }
                },1000);
            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.addAll(DataProvider.getNarrowImage(0));
            }
        });
        adapter.addAll(DataProvider.getNarrowImage(0));
    }

}
