package com.mobile.tanahabangshop.ui.main.home;

/**
 * Created by Lukas Dylan Adisurya on 3/2/2018.
 */

public interface HomeImplementer {
    interface View {
        void showMenu();
        void showWelcomeText(String welcomeTime, String name);
    }

    interface Model {
        String getUserName();
    }

    interface Presenter {
        void initView();
        void destroyView();
    }
}
