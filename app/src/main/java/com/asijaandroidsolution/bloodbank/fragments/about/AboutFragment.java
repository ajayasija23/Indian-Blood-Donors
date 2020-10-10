package com.asijaandroidsolution.bloodbank.fragments.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.databinding.FragmentAboutBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class AboutFragment extends BaseFragment {
    private FragmentAboutBinding binding;
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentAboutBinding.inflate(inflater,container,false);
        View view=binding.getRoot();

        adView = new AdView(getActivity(),"304025074220898_304380950851977", AdSize.BANNER_HEIGHT_50);


        // Add the ad view to your activity layout
        binding.bannerContainerAbout.addView(adView);

        // Request an ad
        adView.loadAd();

        return view;

    }
}
