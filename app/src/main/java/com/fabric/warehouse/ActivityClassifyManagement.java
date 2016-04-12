package com.fabric.warehouse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.os.Handler;
import com.fabric.warehouse.Listener.OnLoadMoreListener;
import com.fabric.warehouse.Model.Product;
import com.fabric.warehouse.adapter.DataAdapter;

import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by 6193 on 2016/4/12.
 */
public class ActivityClassifyManagement extends FabricBaseActivity {

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Product> productList;
    protected Handler handler;
    SearchView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rcv1();
    }


    public void rcv1(){
        setContentView(R.layout.activity_classify_management1);

        ButterKnife.inject(this);//注入父類元件

        //設定toolbar顯示文字
        setTitle(getString(R.string.commodity_management));

        //顯示返回按鈕
        showBackButton();

        //設定搜尋列
        sv = (SearchView) this.findViewById(R.id.my_search_view);
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("查詢");

        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        productList = new ArrayList<>();
        handler = new Handler();

        //載入初始資料
        loadData();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new DataAdapter(productList, mRecyclerView);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
        //	 mAdapter.notifyDataSetChanged();


        if (productList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                productList.add(null);
                mAdapter.notifyItemInserted(productList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        productList.remove(productList.size() - 1);
                        mAdapter.notifyItemRemoved(productList.size());
                        //add items one by one
                        int start = productList.size();
                        int end = start + 20;

                        for (int i = start + 1; i <= end; i++) {
                            productList.add(new Product("Product " + i, String.valueOf(i+(new Random().nextInt(100)+1))));
                            mAdapter.notifyItemInserted(productList.size());
                        }
                        mAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }


    /**
     * 載入初始資料
     */
    private void loadData() {
        for (int i = 1; i <= 20; i++) {
            productList.add(new Product("Product " + i, String.valueOf(i+(new Random().nextInt(100)+1))));
        }
    }

}
