package com.mobile.tanahabangshop.ui.detailproduct;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobile.tanahabangshop.GlideApp;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.ui.base.BaseActivity;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailProductActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.productImageIV)
    ImageView productImageIV;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.detailPhotoBtn)
    LinearLayout detailPhotoBtn;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportPostponeEnterTransition();
            productImageIV.setTransitionName("image_transition");
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            DummyProduct dummyProduct = bundle.getParcelable("dummy_product");
            if (dummyProduct != null) {
                GlideApp.with(this)
                        .load(dummyProduct.getProductImageUrl())
                        .withDetailOptions()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    supportStartPostponedEnterTransition();
                                    animateOtherLayout();
                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    supportStartPostponedEnterTransition();
                                    animateOtherLayout();
                                }
                                return false;
                            }
                        })
                        .into(productImageIV);
            }
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Detil Produk");
            try {
                Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                title = (TextView) field.get(toolbar);
                setTextAlpha(0);
            } catch (Exception e) {
                Timber.e(e);
            }
        }

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float alpha = ((float) Math.abs(verticalOffset) / (appBarLayout.getMeasuredHeight() - toolbar.getMeasuredHeight()));
            if (alpha <= 1.0f) {
                setTextAlpha(alpha);
            } else {
                setTextAlpha(1.0f);
            }
        });

    }

    private void animateOtherLayout(){
        Runnable runnable = () -> detailPhotoBtn.setVisibility(View.VISIBLE);
        startHandler(runnable, 500);
    }

    private void setTextAlpha(float alpha) {
        if (title != null) {
            title.setAlpha(alpha);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_favorite:
                item.setIcon(R.drawable.vector_favorite_fill);
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        stopHandler();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
