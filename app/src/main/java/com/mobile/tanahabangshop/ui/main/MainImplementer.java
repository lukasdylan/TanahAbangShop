package com.mobile.tanahabangshop.ui.main;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface MainImplementer {
    interface View {
        void showMenu();
        void setWelcomeText(String welcomeTime, String name);
        void showFavoriteItems();
    }

    interface Presenter {
        void initView(MainImplementer.View viewListener);
        void refreshView();
        void destroyView();
    }

    interface Model {
        String getUserName();
    }

    interface MainAdapter {
        void onSelectedMenu (int position);
    }
}
