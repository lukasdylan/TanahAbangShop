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

    MainPresenter(MainImplementer.Model model, MainImplementer.View view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void initView() {
        view.showHomeFragment();
    }

    @Override
    public void destroy() {
        view = null;
        model = null;
    }
}
