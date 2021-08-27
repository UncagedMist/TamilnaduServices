package tbc.uncagedmist.tamilnaduservices.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.impressionData.ImpressionData;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

import java.util.Locale;

import tbc.uncagedmist.tamilnaduservices.Common.Common;
import tbc.uncagedmist.tamilnaduservices.R;

public class VoterFragment extends Fragment {

    View myFragment;

    Button btnApply, btnDownload, btnEdit, btnSearch,btnTrack, btnReprint, btnServices;

    FloatingActionButton fabBack;

    Context context;

    private Placement mPlacement;

    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        IronSource.init(
                (Activity) context,
                context.getString(R.string.IS_APP_KEY),
                IronSource.AD_UNIT.REWARDED_VIDEO
        );

        loadRewarded();
        loadLocale();

        myFragment = inflater.inflate(R.layout.fragment_voter, container, false);

        btnApply = myFragment.findViewById(R.id.btnApply);
        btnDownload = myFragment.findViewById(R.id.btnDownload);
        btnEdit = myFragment.findViewById(R.id.btnEdit);
        btnSearch = myFragment.findViewById(R.id.btnSearch);
        btnTrack = myFragment.findViewById(R.id.btnTrack);
        btnReprint = myFragment.findViewById(R.id.btnReprint);
        btnServices = myFragment.findViewById(R.id.btnOfficial);
        fabBack = myFragment.findViewById(R.id.fabBack);

        onClickImplementation();

        return myFragment;
    }

    private void onClickImplementation() {

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction =
                        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,homeFragment).commit();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.APPLY_VOTER;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.DOWNLOAD_VOTER;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.EDIT_VOTER;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.SEARCH_VOTER;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.TRACK_VOTER;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnReprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.VOTER_REPRINT;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentURL = Common.VOTER_SERVICES;

                    transaction.replace(R.id.main_frame,resultFragment).commit();
                }
            }
        });
    }

    private void loadLocale()   {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(
                config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        //shared prefs
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    private void loadRewarded() {
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
                //Change the in-app 'Traffic Driver' state according to availability.
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                /** here you can reward the user according to the given amount.
                 String rewardName = placement.getRewardName();
                 int rewardAmount = placement.getRewardAmount();
                 */
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError error) {
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement){
            }

            @Override
            public void onRewardedVideoAdStarted(){
            }

            @Override
            public void onRewardedVideoAdEnded(){
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        IronSource.onResume((Activity) context);
    }

    @Override
    public void onPause() {
        super.onPause();
        IronSource.onPause((Activity) context);
    }
}