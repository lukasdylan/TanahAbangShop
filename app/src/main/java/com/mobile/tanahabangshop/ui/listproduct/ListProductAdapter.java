package com.mobile.tanahabangshop.ui.listproduct;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.utility.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

import static com.mobile.tanahabangshop.ui.listproduct.ListProductFragment.LIST_MODE;

/**
 * Created by Lukas Dylan Adisurya on 3/7/2018.
 */

public class ListProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DummyProduct> dummyProductList;
    private int layoutMode;
    PublishSubject<Pair<DummyProduct, ImageView>> publishSubject = PublishSubject.create();

    ListProductAdapter() {
        dummyProductList = new ArrayList<>();
        layoutMode = LIST_MODE;
    }

    void setLayoutMode(int layoutMode) {
        this.layoutMode = layoutMode;
//        notifyDataSetChanged();
        notifyItemRangeChanged(0, dummyProductList.size());
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
            holder.itemView.setOnClickListener(
                    v -> publishSubject.onNext(
                            new Pair<>(dummyProduct, ((ListModeViewHolder) holder).productImageIV)));
        } else {
            ((GridModeViewHolder) holder).bind(dummyProduct);
            holder.itemView.setOnClickListener(
                    v -> publishSubject.onNext(
                            new Pair<>(dummyProduct, ((GridModeViewHolder) holder).productImageIV)));
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
        @BindView(R.id.productImageIV)
        ImageView productImageIV;

        private final DisplayMetrics displaymetrics;
        private final RequestManager requestManager;
        private final RequestOptions requestOptions;

        ListModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            displaymetrics = new DisplayMetrics();
            ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            productImageIV.getLayoutParams().width = (displaymetrics.widthPixels * 10) / 35;
            productImageIV.getLayoutParams().height = (displaymetrics.widthPixels * 10) / 35;
            requestManager = Glide.with(itemView.getContext());
            requestOptions = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .dontAnimate()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .placeholder(R.color.lightGray)
                    .error(android.R.color.holo_red_light);
        }

        private void bind(DummyProduct dummyProduct) {
            productCodeTV.setText(dummyProduct.getProductCode());
            productNameTV.setText(dummyProduct.getProductName());
            productPriceTV.setText(String.format(Locale.getDefault(), "Rp %s", StringUtils.formatNumber(dummyProduct.getProductPrice())));
            productStockTV.setText(String.format(Locale.getDefault(), "Sisa stock: %d", dummyProduct.getProductStock()));
            requestManager
                    .load(dummyProduct.getProductImageUrl())
                    .apply(requestOptions)
                    .into(productImageIV);
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
        @BindView(R.id.productImageIV)
        ImageView productImageIV;

        private final RequestManager requestManager;
        private final RequestOptions requestOptions;

        GridModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            requestManager = Glide.with(itemView.getContext());
            requestOptions = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .dontAnimate()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .placeholder(R.color.lightGray)
                    .error(android.R.color.holo_red_light);
        }

        private void bind(DummyProduct dummyProduct) {
            productCodeTV.setText(dummyProduct.getProductCode());
            productNameTV.setText(dummyProduct.getProductName());
            productPriceTV.setText(String.format(Locale.getDefault(), "Rp %s", StringUtils.formatNumber(dummyProduct.getProductPrice())));
            productStockTV.setText(String.format(Locale.getDefault(), "Sisa stock: %d", dummyProduct.getProductStock()));
            requestManager
                    .load(dummyProduct.getProductImageUrl())
                    .apply(requestOptions)
                    .into(productImageIV);
        }
    }
}
