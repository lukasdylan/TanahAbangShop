package com.mobile.tanahabangshop.ui.detailproduct;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProductActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.productImageIV)
    ImageView productImageIV;

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
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .placeholder(R.color.lightGray);
            if (dummyProduct != null) {
                Glide.with(this)
                        .load(dummyProduct.getProductImageUrl())
                        .apply(requestOptions)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    supportStartPostponedEnterTransition();
                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    supportStartPostponedEnterTransition();
                                }
                                return false;
                            }
                        })
                        .into(productImageIV);
            }

            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
