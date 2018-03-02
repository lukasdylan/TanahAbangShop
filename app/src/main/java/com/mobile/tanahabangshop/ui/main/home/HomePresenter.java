package com.mobile.tanahabangshop.ui.main.home;

import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 3/2/2018.
 */

public class HomePresenter implements HomeImplementer.Presenter {

    private HomeImplementer.View view;
    private HomeImplementer.Model model;

    public HomePresenter(HomeImplementer.View view, HomeImplementer.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void initView() {
        loadWelcomeText();
        view.showMenu();
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
        view.showWelcomeText(welcomeTime, model.getUserName());
    }

    @Override
    public void destroyView() {

    }
}
