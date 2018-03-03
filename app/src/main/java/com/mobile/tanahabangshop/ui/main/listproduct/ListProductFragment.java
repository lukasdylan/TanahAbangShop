package com.mobile.tanahabangshop.ui.main.listproduct;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.main.MainImplementer;

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

    private Unbinder binder;
    private MainImplementer.View mainView;
    private Handler handler;
    private Runnable runnable;

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
        runnable = () -> {
            animationView.cancelAnimation();
            loadingLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            productRV.setVisibility(View.VISIBLE);
        };
        handler.postDelayed(runnable, 2500);
        searchProductET.addTextChangedListener(this);
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
