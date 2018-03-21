package com.mobile.tanahabangshop.ui.administrative;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.CityResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class CityRegionAdapter extends RecyclerView.Adapter<CityRegionAdapter.CityRegionViewHolder> {

    private List<CityResult> cityResultList;
    PublishSubject<CityResult> cityResultSubject = PublishSubject.create();

    CityRegionAdapter(List<CityResult> cityResultList){
        this.cityResultList = cityResultList;
    }

    @Override
    public CityRegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_region, parent, false);
        return new CityRegionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityRegionViewHolder holder, int position) {
        CityResult cityResult = cityResultList.get(position);
        holder.cityRegionNameTV.setText(String.format("%s %s",cityResult.getType(), cityResult.getCityName()));
        holder.postalCodeTV.setText(String.format("Kode Pos: %s", cityResult.getPostalCode()));
        holder.itemView.setOnClickListener(v -> cityResultSubject.onNext(cityResult));
    }

    @Override
    public int getItemCount() {
        return cityResultList.size();
    }

    static class CityRegionViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cityRegionNameTV)
        TextView cityRegionNameTV;
        @BindView(R.id.postalCodeTV)
        TextView postalCodeTV;

        CityRegionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
