package com.fabric.warehouse.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fabric.warehouse.Listener.OnLoadMoreListener;
import com.fabric.warehouse.Model.Product;
import com.fabric.warehouse.R;

import java.util.List;

/**
 * Created by 6193 on 2016/4/12.
 */
public class DataAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Product> productList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;



    public DataAdapter(List<Product> products, RecyclerView recyclerView) {
        productList = products;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    System.out.println("[DataAdapter] onScrolled");
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    System.out.println("[DataAdapter] totalItemCount="+totalItemCount);

                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    System.out.println("[DataAdapter] lastVisibleItem="+lastVisibleItem);

                    System.out.println("[DataAdapter] loading="+loading);
                    System.out.println("[DataAdapter] visibleThreshold="+visibleThreshold);

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        System.out.println("[DataAdapter] 1111111111111111");
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            System.out.println("[DataAdapter] 22222222222222222");
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

            vh = new ProductViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {

            Product singleProduct= productList.get(position);

            ((ProductViewHolder) holder).tvName.setText(singleProduct.getName());

            ((ProductViewHolder) holder).tvQuantity.setText(singleProduct.getQuantity());

            ((ProductViewHolder) holder).product= singleProduct;

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvQuantity;
        public Product product;

        public ProductViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),
                            "OnClick :" + product.getName() + " \n "+product.getQuantity(),
                            Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

}
