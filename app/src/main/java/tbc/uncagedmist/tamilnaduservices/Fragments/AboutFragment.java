package tbc.uncagedmist.tamilnaduservices.Fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

import tbc.uncagedmist.tamilnaduservices.R;

public class AboutFragment extends Fragment {

    View myFragment;

    FancyAboutPage aboutPage;
    String version;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_about, container, false);

        aboutPage = myFragment.findViewById(R.id.aboutPage);
        aboutPage.setCover(R.drawable.coverimg);
        aboutPage.setName("Kundan Kumar");
        aboutPage.setDescription("Android Developer | Android App, Game and Software Developer.");
        aboutPage.setAppIcon(R.mipmap.ic_launcher);

        aboutPage.setAppName(getString(R.string.app_name));
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        aboutPage.setVersionNameAsAppSubTitle(version);
        aboutPage.setAppDescription("" +
                "Bhulekh Khesra is an Android app Designed to Help People .\n\n" +
                "This app Provides Information on Land Records offered present at Government Website with beautiful UI. " +
                "People can opt for any state's Land Records and Get benefited." +
                "It also offers to apply for available Services and keep the track of your Applied application.\n\n"+
                "A fresh new take on Material layouts. " +
                "It offers a beautiful UI and daily basis reminder notification to never miss to get any Government Updates.");

        aboutPage.addEmailLink("Kundan_kk52@outlook.com");
        aboutPage.addFacebookLink("https://www.facebook.com/TechByteCare/");
        aboutPage.addTwitterLink("https://twitter.com/uncagedmist");
        aboutPage.addLinkedinLink("https://www.linkedin.com/in/uncagedmist/");
        aboutPage.addGitHubLink("https://github.com/UncagedMist");

        return myFragment;
    }
}