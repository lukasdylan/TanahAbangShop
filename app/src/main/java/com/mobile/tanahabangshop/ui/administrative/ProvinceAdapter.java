package com.mobile.tanahabangshop.ui.administrative;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.ProvinceResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {

    private List<ProvinceResult> provinceResultList;
    PublishSubject<ProvinceResult> provinceResultSubject = PublishSubject.create();

    ProvinceAdapter(List<ProvinceResult> provinceResultList){
        this.provinceResultList = provinceResultList;
    }

    @Override
    public ProvinceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProvinceViewHolder holder, int position) {
        ProvinceResult provinceResult = provinceResultList.get(position);
        holder.provinceNameTV.setText(String.format("Provinsi %s", provinceResult.getProvince()));
        holder.itemView.setOnClickListener(v -> provinceResultSubject.onNext(provinceResult));
    }

    @Override
    public int getItemCount() {
        return provinceResultList.size();
    }

    static class ProvinceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.provinceNameTV)
        TextView provinceNameTV;

        ProvinceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
