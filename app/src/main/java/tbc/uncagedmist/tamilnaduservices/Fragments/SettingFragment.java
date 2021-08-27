package tbc.uncagedmist.tamilnaduservices.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.impressionData.ImpressionData;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import tbc.uncagedmist.tamilnaduservices.Common.Common;
import tbc.uncagedmist.tamilnaduservices.R;

public class SettingFragment extends Fragment {

    CardView cardAbout, cardPrivacy, cardFeedback, cardRate, cardMore, cardShare, cardExit;
    TextView txtVersion;

    View myFragment;

    ReviewManager manager;
    ReviewInfo reviewInfo;

    Context context;

    String version;

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        IronSource.init(
                (Activity) context,
                context.getString(R.string.IS_APP_KEY),
                IronSource.AD_UNIT.REWARDED_VIDEO
        );

        loadRewarded();

        myFragment = inflater.inflate(R.layout.fragment_setting, container, false);

        manager = ReviewManagerFactory.create((Activity)context);

        cardAbout = myFragment.findViewById(R.id.cardAbout);
        cardPrivacy = myFragment.findViewById(R.id.cardPrivacy);
        cardFeedback = myFragment.findViewById(R.id.cardFeedback);
        cardRate = myFragment.findViewById(R.id.cardRate);
        cardMore = myFragment.findViewById(R.id.cardMore);
        cardShare = myFragment.findViewById(R.id.cardShare);
        cardExit = myFragment.findViewById(R.id.cardExit);

        txtVersion = myFragment.findViewById(R.id.txtVersion);

        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        txtVersion.setText("App Version : "+ version);
        onClickImplementation();

        return myFragment;
    }


    private void onClickImplementation() {
        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    AboutFragment aboutFragment = new AboutFragment();
                    FragmentTransaction transaction =
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame,aboutFragment).commit();
                }
            }
        });

        cardPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IronSource.isRewardedVideoAvailable())  {
                    //show rewarded video
                    IronSource.showRewardedVideo("DefaultRewardedVideo");
                }
                else {
                    PrivacyFragment privacyFragment = new PrivacyFragment();
                    FragmentTransaction transaction =
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame,privacyFragment).commit();
                }
            }
        });

        cardFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback();
            }
        });

        cardRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.biharration")));
            }
        });

        cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:UncagedMist")));
            }
        });

        cardShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.shareApp(context);
            }
        });

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    private void feedback() {
        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful())    {
                reviewInfo = task.getResult();

                Task<Void> flow = manager.launchReviewFlow((Activity)context,reviewInfo);

                flow.addOnSuccessListener(result -> {
                });
            }
            else {
                Toast.makeText(getActivity(), "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exit() {
        new FancyAlertDialog.Builder((Activity)context)
                .setTitle("Good-Bye")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Do You Want to Step Out?")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Support US")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.mobilewallpapers"))))
                .OnNegativeClicked(() -> {
                    getActivity().moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                })
                .build();
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