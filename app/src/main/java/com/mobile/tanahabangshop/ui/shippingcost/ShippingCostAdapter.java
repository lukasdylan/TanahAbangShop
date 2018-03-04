package com.mobile.tanahabangshop.ui.shippingcost;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.CostResult;

import java.util.List;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class ShippingCostAdapter extends RecyclerView.Adapter<ShippingCostAdapter.ShippingCostViewHolder> {

    private List<CostResult> costResultList;
    private String courierName;

    ShippingCostAdapter(List<CostResult> costResultList, String courierName) {
        this.costResultList = costResultList;
        this.courierName = courierName;
    }

    @Override
    public ShippingCostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping_cost, parent, false);
        return new ShippingCostViewHolder(view, courierName);
    }

    @Override
    public void onBindViewHolder(ShippingCostViewHolder holder, int position) {
        CostResult costResult = costResultList.get(position);
        if (!costResult.getService().equalsIgnoreCase(costResult.getDescription())) {
            holder.serviceNameTV.setText(String.format("%s(%s)", costResult.getService(), costResult.getDescription()));
        } else {
            holder.serviceNameTV.setText(costResult.getService());
        }
        String estimateTime = costResult.getCost().get(0).getEtd();
        if(estimateTime.contains("HARI")){
            estimateTime = estimateTime.replaceAll("HARI","");
            holder.estimatedTimeTV.setText(String.format("%s hari", estimateTime));
        } else if(estimateTime.contains("JAM")){
            estimateTime = estimateTime.replaceAll("JAM","");
            holder.estimatedTimeTV.setText(String.format("%s jam", estimateTime));
        } else {
            holder.estimatedTimeTV.setText(String.format("%s hari", estimateTime));
        }
        holder.priceTV.setText(String.format(Locale.getDefault(),"Rp %d", costResult.getCost().get(0).getValue()));
    }

    @Override
    public int getItemCount() {
        return costResultList.size();
    }

    static class ShippingCostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.courierLogoIV)
        ImageView courierLogoIV;
        @BindView(R.id.serviceNameTV)
        TextView serviceNameTV;
        @BindView(R.id.estimatedTimeTV)
        TextView estimatedTimeTV;
        @BindView(R.id.priceTV)
        TextView priceTV;

        @BindDrawable(R.drawable.logo_jne)
        Drawable logoJne;
        @BindDrawable(R.drawable.pos_indonesia_logo)
        Drawable logoPos;
        @BindDrawable(R.drawable.tiki_logo)
        Drawable logoTiki;

        ShippingCostViewHolder(View itemView, String courierName) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (courierName.equalsIgnoreCase("JNE")) {
                courierLogoIV.setImageDrawable(logoJne);
            } else if (courierName.equalsIgnoreCase("Pos Indonesia")) {
                courierLogoIV.setImageDrawable(logoPos);
            } else {
                courierLogoIV.setImageDrawable(logoTiki);
            }
        }
    }
}
