package com.fabric.warehouse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.os.Handler;

import com.fabric.warehouse.Api.User;
import com.fabric.warehouse.Listener.OnLoadMoreListener;
import com.fabric.warehouse.Model.Product;
import com.fabric.warehouse.Model.Wechat;
import com.fabric.warehouse.adapter.DataAdapter;
import com.fabric.warehouse.dao.DatabaseDAO;
import com.fabric.warehouse.di.ApiComponent;
import com.fabric.warehouse.di.ApiModule;
import com.fabric.warehouse.di.ApplicationModule;
import com.fabric.warehouse.di.DaggerApiComponent;


import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;

/**
 * Created by 6193 on 2016/4/12.
 */
public class ActivityClassifyManagement extends FabricBaseActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Product> productList;
    protected Handler handler;
    private DatabaseDAO databaseDAO;

    @InjectView(R.id.my_search_view)
    SearchView searchView;

    @Inject
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_management1);
        ButterKnife.inject(this);//注入父類元件

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);

        //testApi();
        rcv2();
    }


    /**
     * 測試微信API
     */
    public void testApi(){
        String grant_type = "client_credential";
        String appid = "wx9c61ca4d88538922";
        String secret = "f3aac1609d3a0ced1ff6d54f5157f740";

        ProgressDialog dialog = showLoadingProgressDialog(this);
        dialog.show();

        AppObservable.bindActivity(this, user.getWechat(grant_type, appid, secret))
                     .subscribeOn(Schedulers.io())
                     .subscribe(new Observer<Wechat>() {
                         @Override
                         public void onCompleted() {
                             runOnUiThread(dialog::dismiss);
                             System.out.println("======onCompleted=====:");
                         }

                         @Override
                         public void onError(Throwable e) {
                             System.out.println("======onError=====:");
                             if (e instanceof RetrofitError) {
                                 RetrofitError re = ((RetrofitError) e);
                                 if (re.getKind() == RetrofitError.Kind.NETWORK) {
//                        runOnUiThread(() -> showNetworkErrorDialog(Activity_SearchResults.this));
                                 }
                             }
                             runOnUiThread(dialog::dismiss);
                         }

                         @Override
                         public void onNext(Wechat wechat) {
//                if (productResponse.hasData()) {
//                    item.addAll(productResponse.getProductPage().getCollections());
//                    onScrollListener.setTotalPages(productResponse.getProductPage().getTotalPages());
//                    adapter.updateAdapter(item);
//                    runOnUiThread(adapter::notifyDataSetChanged);
//                }
                             System.out.println("======onNext=====:");
                             System.out.println("======wechat(Errcode)=====:" + wechat.getErrcode());
                             System.out.println("======wechat(Errmsg)=====:" + wechat.getErrmsg());
                         }
                     });



    }


    public void test(){
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            //當 Observable 被訂閱的時候，OnSubscribe 的 call() 方法會自動被調用
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
    }


    public void rcv1(){
        //設定toolbar顯示文字
        setTitle(getString(R.string.commodity_management));

        //顯示返回按鈕
        showBackButton();

        //設定搜尋列
        searchView.setOnQueryTextListener(this);

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

                System.out.println("==[rcv1]onLoadMore==");

                //add null , so the adapter will check view_type and show progress bar at bottom
                productList.add(null);
                mAdapter.notifyItemInserted(productList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("==[rcv1]run==");
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


    public void rcv2(){
        //設定toolbar顯示文字
        setTitle(getString(R.string.commodity_management));

        //顯示返回按鈕
        showBackButton();

        //設定搜尋列
        searchView.setOnQueryTextListener(this);

        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        productList = new ArrayList<>();
        handler = new Handler();

        // 建立資料庫物件
        databaseDAO = new DatabaseDAO(this);

        //載入初始資料
        loadData2();//databaseDAO.initData()

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

                System.out.println("==[rcv2]onLoadMore==");

                //add null , so the adapter will check view_type and show progress bar at bottom
                productList.add(null);
                mAdapter.notifyItemInserted(productList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("==[rcv2]run==");
                        //   remove progress item
                        productList.remove(productList.size() - 1);
                        mAdapter.notifyItemRemoved(productList.size());
//                        //add items one by one
//                        int start = productList.size();
//                        int end = start + 20;
//
//                        for (int i = start + 1; i <= end; i++) {
//                            productList.add(new Product("Product " + i, String.valueOf(i+(new Random().nextInt(100)+1))));
//                            mAdapter.notifyItemInserted(productList.size());
//                        }

                        mAdapter.notifyItemInserted(productList.size());
                        productList.addAll(databaseDAO.getMoreData());
                        mAdapter.notifyDataSetChanged();

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

    private void loadData2() {
        productList.addAll(databaseDAO.initData());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        if (Strings.emptyToNull(newText) == null) {
//            adapter.updateAdapter(item);
//            adapter.notifyDataSetChanged();
//            return true;
//        }
//        getFilteredCollectList(newText);
//        adapter.updateAdapter(queryCollectList);
//        adapter.notifyDataSetChanged();
        return true;
    }

    private void getFilteredCollectList(String newText) {
//        queryCollectList.clear();
//        for (ProductItem collect : item) {
//            if (collect.productName().contains(newText)) {
//                queryCollectList.add(collect);
//            }
//        }
    }

}
