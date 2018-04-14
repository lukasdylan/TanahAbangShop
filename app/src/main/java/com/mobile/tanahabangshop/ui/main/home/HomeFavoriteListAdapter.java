package com.mobile.tanahabangshop.ui.main.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobile.tanahabangshop.data.model.DummyProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id}>
 * on 4/7/2018.
 */

public class HomeFavoriteListAdapter extends RecyclerView.Adapter<HomeFavoriteListAdapter.FavoriteViewHolder> {

    private List<DummyProduct> favoriteList;

    HomeFavoriteListAdapter(){
        favoriteList = new ArrayList<>();
    }

    void addFavoriteList(List<DummyProduct> favoriteList){
        this.favoriteList = favoriteList;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(DummyProduct dummyProduct){

        }
    }
}
