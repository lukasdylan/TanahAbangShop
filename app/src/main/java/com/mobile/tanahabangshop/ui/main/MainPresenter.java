package com.mobile.tanahabangshop.ui.main;

import com.mobile.tanahabangshop.ui.main.home.HomeFragment;
import com.mobile.tanahabangshop.ui.profile.UserProfileFragment;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MainPresenter implements MainImplementer.Presenter {

    private MainImplementer.Model model;
    private MainImplementer.View view;

    public MainPresenter(MainImplementer.Model model, MainImplementer.View view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void initView() {
        HomeFragment homeFragment = new HomeFragment();
        view.showFragment(homeFragment);
    }

    @Override
    public void destroyView() {
        view = null;
        model = null;
    }

    @Override
    public void showProfile() {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        view.showFragment(userProfileFragment);
    }
}
