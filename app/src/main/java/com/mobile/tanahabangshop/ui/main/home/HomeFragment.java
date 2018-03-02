package com.mobile.tanahabangshop.ui.main.home;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.MainMenu;
import com.mobile.tanahabangshop.ui.main.MainImplementer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MainImplementer.MainAdapter, HomeImplementer.View{

    @BindView(R.id.welcomeUserTV)
    TextView welcomeUserTV;
    @BindView(R.id.mainMenuRV)
    RecyclerView mainMenuRV;

    @BindDrawable(R.drawable.icon_main_product)
    Drawable iconMainProduct;
    @BindDrawable(R.drawable.icon_invoice)
    Drawable iconInvoice;
    @BindDrawable(R.drawable.icon_shipping_cost)
    Drawable iconShippingCost;
    @BindDrawable(R.drawable.icon_store)
    Drawable iconStore;

    @Inject
    HomeImplementer.Presenter presenter;

    private Unbinder binder;
    private MainImplementer.View mainView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if(context instanceof MainImplementer.View){
            try {
                mainView = (MainImplementer.View) context;
            } catch (ClassCastException ex){
                throw new ClassCastException(context.toString() + "must implement MainImplementer.View");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this, view);
        presenter.initView();
        mainView.isShowMenuItem(true);
        mainView.setupToolbar(false, getString(R.string.app_name));
    }

    @Override
    public void onDestroyView() {
        presenter.destroyView();
        binder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onSelectedMenu(int position) {

    }

    @Override
    public void showMenu() {
        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(new MainMenu("Lihat Produk Toko", iconMainProduct));
        mainMenuList.add(new MainMenu("Cek Pesanan Saya", iconInvoice));
        mainMenuList.add(new MainMenu("Cek Biaya Pengiriman", iconShippingCost));
        mainMenuList.add(new MainMenu("Cek Informasi Toko", iconStore));

        HomeMenuAdapter homeMenuAdapter = new HomeMenuAdapter(mainMenuList, this);
        mainMenuRV.setHasFixedSize(true);
        mainMenuRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mainMenuRV.setAdapter(homeMenuAdapter);
        mainMenuRV.setNestedScrollingEnabled(false);
    }

    @Override
    public void showWelcomeText(String welcomeTime, String name) {
        welcomeUserTV.setText(String.format("%s, %s", welcomeTime, name));
    }
}
