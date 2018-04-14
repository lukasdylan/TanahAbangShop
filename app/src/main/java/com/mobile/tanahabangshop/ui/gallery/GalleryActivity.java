package com.mobile.tanahabangshop.ui.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;
import com.mobile.tanahabangshop.GlideApp;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.base.BaseActivity;
import com.mobile.tanahabangshop.utility.UiUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.productImageIV)
    PhotoView productImageIV;

    @BindColor(android.R.color.holo_green_light)
    int greenColor;
    @BindColor(android.R.color.holo_red_light)
    int redColor;
    @BindColor(android.R.color.white)
    int whiteColor;

    private SaveImageAsyncTask saveImageAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Foto 1");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imageUrl = bundle.getString("image_url");
            GlideApp.with(this)
                    .load(imageUrl)
                    .withDetailOptions()
                    .into(productImageIV);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (saveImageAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            saveImageAsyncTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.saveImageBtn)
    void saveImage() {
        saveImageAsyncTask = new SaveImageAsyncTask(productImageIV);
        saveImageAsyncTask.getResultSubject()
                .filter(stringBooleanPair -> stringBooleanPair.second != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringBooleanPair -> {
                    String message = stringBooleanPair.first;
                    boolean result = stringBooleanPair.second;
                    if (result) {
                        UiUtils.showSnackBar(coordinatorLayout, message, greenColor, whiteColor);
                    } else {
                        UiUtils.showSnackBar(coordinatorLayout, message, redColor, whiteColor);
                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveImageAsyncTask.execute();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            saveImageAsyncTask.execute();
        }
    }

    private static class SaveImageAsyncTask extends AsyncTask<Void, Integer, Void> {

        private final WeakReference<PhotoView> photoViewWeakReference;
        private final PublishSubject<Pair<String, Boolean>> resultSubject = PublishSubject.create();
        private boolean isSuccess = false;
        private String message = "";

        SaveImageAsyncTask(PhotoView photoView) {
            this.photoViewWeakReference = new WeakReference<>(photoView);
        }

        PublishSubject<Pair<String, Boolean>> getResultSubject() {
            return resultSubject;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Timber.d("%s%", values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BitmapDrawable draw = (BitmapDrawable) photoViewWeakReference.get().getDrawable();
            Bitmap bitmap = draw.getBitmap();
            File directoryFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TanahAbangShop");
            if (!directoryFile.exists()) {
                directoryFile.mkdirs();
            }
            String fileName = String.format(Locale.getDefault(), "%d.jpg", System.currentTimeMillis());
            File outFile = new File(directoryFile, fileName);
            try {
                FileOutputStream outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                isSuccess = true;
                message = "Foto berhasil disimpan";
            } catch (IOException e) {
                Timber.e(e);
                isSuccess = false;
                message = e.getLocalizedMessage();
            }
            bitmap.recycle();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultSubject.onNext(new Pair<>(message, isSuccess));
        }
    }
}
