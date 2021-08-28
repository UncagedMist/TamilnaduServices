package tbc.uncagedmist.tamilnaduservices.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

import tbc.uncagedmist.tamilnaduservices.R;

public class MyApplicationClass extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;

    MyNetworkReceiver mNetworkReceiver;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        IronSource.setMetaData("Facebook_IS_CacheFlag","IMAGE");

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                mNetworkReceiver = new MyNetworkReceiver();
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
                registerNetworkBroadcastForLollipop();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                mActivity = null;
                unregisterReceiver(mNetworkReceiver);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void registerNetworkBroadcastForLollipop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
}