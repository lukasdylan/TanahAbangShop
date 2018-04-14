package com.mobile.tanahabangshop.ui.detailproduct;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobile.tanahabangshop.GlideApp;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.DummyProduct;
import com.mobile.tanahabangshop.ui.base.BaseActivity;
import com.mobile.tanahabangshop.ui.gallery.GalleryActivity;
import com.mobile.tanahabangshop.utility.UiUtils;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class DetailProductActivity extends BaseActivity implements DetailProductImplementer.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.productImageIV)
    ImageView productImageIV;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.detailPhotoBtn)
    TextView detailPhotoBtn;
    @BindView(R.id.titleCardView)
    CardView titleCardView;
    @BindView(R.id.orderNowBtn)
    Button orderNowBtn;
    @BindView(R.id.productNameTV)
    TextView productNameTV;
    @BindView(R.id.buyNavigationCV)
    CardView buyNavigationCV;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindDrawable(R.drawable.vector_favorite_fill)
    Drawable favoriteIcon;
    @BindDrawable(R.drawable.vector_favorite_no_fill)
    Drawable noFavoriteIcon;

    @Inject
    DetailProductImplementer.Presenter presenter;

    private TextView titleTextView;
    private LottieAnimationView lottieAnimationView;
    private ImageView favoriteIconImageView;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            productNameTV.setText(presenter.getDummyProduct().getProductName());
            titleCardView.setVisibility(View.VISIBLE);
            buyNavigationCV.setVisibility(View.VISIBLE);
            detailPhotoBtn.setVisibility(View.VISIBLE);
//            orderNowBtn.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportPostponeEnterTransition();
            productImageIV.setTransitionName("image_transition");
        }
        setupView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            DummyProduct dummyProduct = bundle.getParcelable("dummy_product");
            if (dummyProduct != null) {
                presenter.loadProductData(dummyProduct);
            }
        }

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Detil Produk");
            try {
                Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                titleTextView = (TextView) field.get(toolbar);
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

    @OnClick(R.id.buyNavigationCV)
    void navigateToProductList() {
        nestedScrollView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        nestedScrollView.dispatchNestedPreScroll(0, titleCardView.getBottom(), null, null);
        nestedScrollView.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -titleCardView.getBottom()});
        buyNavigationCV.setVisibility(View.GONE);
    }

    @OnClick({R.id.detailPhotoBtn, R.id.productImageIV})
    void navigateToGallery() {
        Bundle bundle = new Bundle();
        bundle.putString("image_url", presenter.getDummyProduct().getProductImageUrl());
        openActivity(new GalleryActivity(), bundle);
    }

    private void animateOtherLayout() {
        startHandler(runnable, 500);
    }

    private void setTextAlpha(float alpha) {
        if (titleTextView != null) {
            titleTextView.setAlpha(alpha);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_product_menu, menu);
        MenuItem favoriteMenu = menu.findItem(R.id.menu_favorite);
        View actionView = favoriteMenu.getActionView();
        actionView.setOnClickListener(v -> onOptionsItemSelected(favoriteMenu));
        lottieAnimationView = actionView.findViewById(R.id.lottieAnimationView);
        favoriteIconImageView = actionView.findViewById(R.id.favoriteIcon);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                favoriteIconImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                lottieAnimationView.setVisibility(View.GONE);
                favoriteIconImageView.setVisibility(View.VISIBLE);
                favoriteIconImageView.setImageDrawable(favoriteIcon);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_favorite:
                if (lottieAnimationView != null && !presenter.getDummyProduct().isFavorite()) {
                    lottieAnimationView.playAnimation();
                    presenter.getDummyProduct().setFavorite(true);
                } else {
                    favoriteIconImageView.setImageDrawable(noFavoriteIcon);
                    presenter.getDummyProduct().setFavorite(false);
                }
                return true;
            default:
                break;
        }
        return false;
    }

    private void setupView() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        productImageIV.getLayoutParams().height = displaymetrics.heightPixels / 2;
        nestedScrollView.setSmoothScrollingEnabled(true);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> Timber.d("Scrolled"));
    }

    @Override
    protected void onDestroy() {
        stopHandler(runnable);
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void initView(DummyProduct dummyProduct) {
        GlideApp.with(this)
                .load(dummyProduct.getProductImageUrl())
                .withDetailOptions()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            supportStartPostponedEnterTransition();
                        }
                        animateOtherLayout();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            supportStartPostponedEnterTransition();
                        }
                        animateOtherLayout();
                        return false;
                    }
                })
                .into(productImageIV);
    }

    @Override
    public void showFavoriteMessage(boolean isFavorite) {
        if (isFavorite) {
            UiUtils.showSnackBar(coordinatorLayout, "Anda mendaftarkan produk ini sebagai favorit anda");
        } else {
            UiUtils.showSnackBar(coordinatorLayout, "Anda menghapus produk ini dari favorit anda");
        }
    }
}
