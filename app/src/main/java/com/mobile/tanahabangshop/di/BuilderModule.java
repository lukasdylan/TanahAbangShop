package com.mobile.tanahabangshop.di;

import com.mobile.tanahabangshop.service.backgroundreceiver.BackgroundReceiver;
import com.mobile.tanahabangshop.service.backgroundreceiver.BackgroundReceiverModule;
import com.mobile.tanahabangshop.ui.administrative.AdministrativeActivity;
import com.mobile.tanahabangshop.ui.administrative.AdministrativeModule;
import com.mobile.tanahabangshop.ui.detailproduct.DetailProductActivity;
import com.mobile.tanahabangshop.ui.detailproduct.DetailProductModule;
import com.mobile.tanahabangshop.ui.login.LoginActivity;
import com.mobile.tanahabangshop.ui.login.LoginModule;
import com.mobile.tanahabangshop.ui.main.MainActivity;
import com.mobile.tanahabangshop.ui.main.MainModule;
import com.mobile.tanahabangshop.ui.main.home.HomeFragment;
import com.mobile.tanahabangshop.ui.main.home.HomeModule;
import com.mobile.tanahabangshop.ui.register.RegisterActivity;
import com.mobile.tanahabangshop.ui.register.RegisterModel;
import com.mobile.tanahabangshop.ui.register.RegisterModule;
import com.mobile.tanahabangshop.ui.shippingcost.ShippingCostFragment;
import com.mobile.tanahabangshop.ui.shippingcost.ShippingCostModule;
import com.mobile.tanahabangshop.ui.splash.SplashActivity;
import com.mobile.tanahabangshop.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
abstract class BuilderModule {
    @ContributesAndroidInjector(modules = {SplashModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = RegisterModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector(modules = AdministrativeModule.class)
    abstract AdministrativeActivity bindAdministrativeActivity();

    @ContributesAndroidInjector(modules = ShippingCostModule.class)
    abstract ShippingCostFragment bindShippingCostFragment();

    @ContributesAndroidInjector(modules = BackgroundReceiverModule.class)
    abstract BackgroundReceiver bindBackgroundReceiver();

    @ContributesAndroidInjector(modules = DetailProductModule.class)
    abstract DetailProductActivity bindDetailProductActivity();
}
