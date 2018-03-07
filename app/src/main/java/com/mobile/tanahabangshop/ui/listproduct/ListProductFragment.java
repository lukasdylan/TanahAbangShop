package com.mobile.tanahabangshop.ui.listproduct;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.ui.main.MainImplementer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProductFragment extends Fragment implements TextWatcher {

    @BindView(R.id.loadingLayout)
    LinearLayout loadingLayout;
    @BindView(R.id.searchLayout)
    RelativeLayout searchLayout;
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.productRV)
    RecyclerView productRV;
    @BindView(R.id.searchProductET)
    EditText searchProductET;
    @BindView(R.id.clearBtn)
    ImageView clearBtn;

    @BindDrawable(R.drawable.vector_list_mode)
    Drawable iconList;
    @BindDrawable(R.drawable.vector_grid_mode)
    Drawable iconGrid;

    public static final int LIST_MODE = 0;
    public static final int GRID_MODE = 1;
    private Unbinder binder;
    private MainImplementer.View mainView;
    private Handler handler;
    private Runnable runnable;
    private int currentLayoutMode = LIST_MODE;
    private MenuItem changeLayoutMenuItem;
    private ListProductAdapter listProductAdapter;

    public ListProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainImplementer.View) {
            try {
                mainView = (MainImplementer.View) context;
            } catch (ClassCastException ex) {
                throw new ClassCastException(context.toString() + "must implement MainImplementer.View");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        handler = new Handler();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this, view);
        mainView.isShowMenuItem(false);
        mainView.setupToolbar(true, "Produk Toko");
        productRV.setHasFixedSize(true);
        productRV.setLayoutManager(new LinearLayoutManager(getContext()));
        productRV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listProductAdapter = new ListProductAdapter();
        productRV.setAdapter(listProductAdapter);
        runnable = () -> {
            animationView.cancelAnimation();
            loadingLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            productRV.setVisibility(View.VISIBLE);
            setDummyData();
        };
        handler.postDelayed(runnable, 2500);
        searchProductET.addTextChangedListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.layout_product_menu, menu);
        changeLayoutMenuItem = menu.findItem(R.id.menu_layout);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_layout:
                if (currentLayoutMode == LIST_MODE) {
                    currentLayoutMode = GRID_MODE;
                    productRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    productRV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
                    listProductAdapter.setLayoutMode(GRID_MODE);
                    changeLayoutMenuItem.setIcon(iconList);
                } else {
                    currentLayoutMode = LIST_MODE;
                    productRV.setLayoutManager(new LinearLayoutManager(getContext()));
                    productRV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    listProductAdapter.setLayoutMode(LIST_MODE);
                    changeLayoutMenuItem.setIcon(iconGrid);
                }
                return true;
            default:
                break;
        }
        return false;
    }

    private void setDummyData(){
        List<DummyProduct> dummyProductList = new ArrayList<>();
        dummyProductList.add(new DummyProduct("ABC-123", "Baju Batik Merah", 50000, 30));
        dummyProductList.add(new DummyProduct("ABC-456", "Celana Kain Hitam Celana Kain Hitam Celana Kain Hitam", 150000, 50));
        dummyProductList.add(new DummyProduct("XYZ-001", "Jaket Kulit Coklat Jaket Kulit Coklat Jaket Kulit Coklat", 360000, 5));
        dummyProductList.add(new DummyProduct("XYZ-002", "Jaket Kulit Hitam", 360000, 10));
        dummyProductList.add(new DummyProduct("QWE-111", "Celana Jeans Biru", 250000, 22));
        listProductAdapter.addList(dummyProductList);
    }

    @OnClick(R.id.clearBtn)
    void clearText() {
        searchProductET.setText(null);
    }

    @Override
    public void onDestroyView() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        binder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mainView = null;
        super.onDetach();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            clearBtn.setVisibility(View.GONE);
        } else {
            clearBtn.setVisibility(View.VISIBLE);
        }
    }
}
