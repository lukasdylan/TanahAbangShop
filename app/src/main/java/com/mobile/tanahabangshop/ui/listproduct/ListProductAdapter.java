package com.mobile.tanahabangshop.ui.listproduct;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.utility.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobile.tanahabangshop.ui.listproduct.ListProductFragment.LIST_MODE;

/**
 * Created by Lukas Dylan Adisurya on 3/7/2018.
 */

public class ListProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DummyProduct> dummyProductList;
    private int layoutMode;

    ListProductAdapter() {
        dummyProductList = new ArrayList<>();
        layoutMode = LIST_MODE;
    }

    void setLayoutMode(int layoutMode) {
        this.layoutMode = layoutMode;
        notifyDataSetChanged();
    }

    void addList(List<DummyProduct> dummyProducts) {
        int startSize = dummyProductList.size();
        dummyProductList.addAll(dummyProducts);
        notifyItemRangeInserted(startSize, this.dummyProductList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return layoutMode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LIST_MODE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
            return new ListModeViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grid, parent, false);
            return new GridModeViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DummyProduct dummyProduct = dummyProductList.get(position);
        if (holder instanceof ListModeViewHolder) {
            ((ListModeViewHolder) holder).bind(dummyProduct);
        } else {
            ((GridModeViewHolder) holder).bind(dummyProduct);
        }
    }

    @Override
    public int getItemCount() {
        return dummyProductList.size();
    }

    static class ListModeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productCodeTV)
        TextView productCodeTV;
        @BindView(R.id.productNameTV)
        TextView productNameTV;
        @BindView(R.id.productPriceTV)
        TextView productPriceTV;
        @BindView(R.id.productStockTV)
        TextView productStockTV;

        ListModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(DummyProduct dummyProduct) {
            productCodeTV.setText(dummyProduct.getProductCode());
            productNameTV.setText(dummyProduct.getProductName());
            productPriceTV.setText(String.format(Locale.getDefault(), "Rp %s", StringUtils.formatNumber(dummyProduct.getProductPrice())));
            productStockTV.setText(String.format(Locale.getDefault(), "Sisa stock: %d", dummyProduct.getProductStock()));
        }
    }

    static class GridModeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productCodeTV)
        TextView productCodeTV;
        @BindView(R.id.productNameTV)
        TextView productNameTV;
        @BindView(R.id.productPriceTV)
        TextView productPriceTV;
        @BindView(R.id.productStockTV)
        TextView productStockTV;

        GridModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(DummyProduct dummyProduct) {
            productCodeTV.setText(dummyProduct.getProductCode());
            productNameTV.setText(dummyProduct.getProductName());
            productPriceTV.setText(String.format(Locale.getDefault(), "Rp %s", StringUtils.formatNumber(dummyProduct.getProductPrice())));
            productStockTV.setText(String.format(Locale.getDefault(), "Sisa stock: %d", dummyProduct.getProductStock()));
        }
    }
}
