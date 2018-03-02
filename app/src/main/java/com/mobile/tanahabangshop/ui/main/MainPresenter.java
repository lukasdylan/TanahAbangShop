package com.mobile.tanahabangshop.ui.main;

import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MainPresenter implements MainImplementer.Presenter {

    private final MainModel mainModel;
    private MainImplementer.View viewListener;

    MainPresenter(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void initView(MainImplementer.View viewListener) {
        this.viewListener = viewListener;
        viewListener.showMenu();
    }

    @Override
    public void refreshView() {
        loadWelcomeText();
        loadFavoriteData();
    }

    private void loadWelcomeText() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Timber.d("Current hour %s", hour);
        String welcomeTime;
        if (hour <= 4) {
            welcomeTime = "Selamat malam";
        } else if (hour >= 5 && hour <= 10) {
            welcomeTime = "Selamat pagi";
        } else if (hour >= 11 && hour <= 14) {
            welcomeTime = "Selamat siang";
        } else if (hour >= 15 && hour <= 18) {
            welcomeTime = "Selamat sore";
        } else {
            welcomeTime = "Selamat malam";
        }
        viewListener.setWelcomeText(welcomeTime, mainModel.getUserName());
    }

    private void loadFavoriteData() {

    }

    @Override
    public void destroyView() {
        viewListener = null;
    }
}
