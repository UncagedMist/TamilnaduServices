package tbc.uncagedmist.tamilnaduservices;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import tbc.uncagedmist.tamilnaduservices.Common.Common;
import tbc.uncagedmist.tamilnaduservices.Fragments.HomeFragment;
import tbc.uncagedmist.tamilnaduservices.Fragments.SettingFragment;
import tbc.uncagedmist.tamilnaduservices.Utility.CurvedBottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 31;

    CurvedBottomNavigationView curvedBottomNavigationView;
    FloatingActionButton fab, fabShare;

    FrameLayout bannerContainer;
    IronSourceBannerLayout banner;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {
                    Toast.makeText(this, "PERMISSION GRANTED..", Toast.LENGTH_SHORT).show();
                }
                else    {
                    Toast.makeText(this, "PERMISSION DENIED...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        checkAppUpdate();

        IronSource.init(
                this,
                getString(R.string.IS_APP_KEY),
                IronSource.AD_UNIT.BANNER
        );

        setContentView(R.layout.activity_main);

        createAndLoadBanner();

        IronSource.shouldTrackNetworkState(this, true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)   {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        fab = findViewById(R.id.fab);
        fabShare = findViewById(R.id.stateShare);
        curvedBottomNavigationView = findViewById(R.id.customBottomBar);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.main_frame,homeFragment).commit();

        curvedBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Fragment fragment;

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.action_home) {
                            toolbar.setBackgroundColor(getColor(R.color.color1));
                            getSupportActionBar().setTitle(R.string.app_name);
                            fragment = new HomeFragment();
                            fab.setImageResource(R.drawable.ic_baseline_home_24);
                        }
                        else if (item.getItemId() == R.id.action_setting) {
                            toolbar.setBackgroundColor(getColor(R.color.color2));
                            getSupportActionBar().setTitle("Settings");
                            fragment = new SettingFragment();
                            fab.setImageResource(R.drawable.ic_baseline_settings_applications_24);
                        }
                        return loadFragment(fragment);
                    }
                });

        loadFragment(HomeFragment.getInstance());
        fab.setImageResource(R.drawable.ic_baseline_home_24);

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.shareApp(MainActivity.this);
            }
        });
    }


    private void createAndLoadBanner() {
        bannerContainer = findViewById(R.id.bannerContainer);
        banner = IronSource.createBanner(this, ISBannerSize.BANNER);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        bannerContainer.addView(banner, 0, layoutParams);

        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                banner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onBannerAdLoadFailed(IronSourceError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bannerContainer.removeAllViews();
                    }
                });
            }
            @Override
            public void onBannerAdClicked() {

            }
            @Override
            public void onBannerAdScreenPresented() {

            }
            @Override
            public void onBannerAdScreenDismissed() {

            }
            @Override
            public void onBannerAdLeftApplication() {

            }
        });

        IronSource.loadBanner(banner, (String)"DefaultBanner");
    }

    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(MainActivity.this)
                .setTitle("Games Wallpaper App")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Customize your Phone's Look with our new Wallpaper App.Support us by downloading our other apps!")
                .setNegativeBtnText("Don't")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Support US")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(false)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.mobilewallpapers"))))
                .OnNegativeClicked(() -> {
                })
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IronSource.destroyBanner(banner);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void checkAppUpdate() {
        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))    {

                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                result,AppUpdateType.IMMEDIATE,
                                MainActivity.this,
                                PERMISSION_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}