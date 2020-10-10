package com.asijaandroidsolution.bloodbank.activity.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.BaseActivity;
import com.asijaandroidsolution.bloodbank.activity.home.HomeActivity;
import com.asijaandroidsolution.bloodbank.databinding.ActivitySplashBinding;
import com.facebook.ads.AudienceNetworkAds;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding binding;
    private Animation logoAnim;
    private Animation titleAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AudienceNetworkAds.initialize(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        titleAnim = AnimationUtils.loadAnimation(this, R.anim.title_anim);

        logoAnim.setDuration(2000);
        titleAnim.setDuration(2000);
        binding.imageLogo.startAnimation(logoAnim);
        binding.textViewTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.textViewTitle.setVisibility(View.VISIBLE);
                binding.textViewSubTitle.setVisibility(View.VISIBLE);
                binding.textViewTitle.startAnimation(titleAnim);
                binding.textViewSubTitle.startAnimation(titleAnim);

            }
        },2000);


    }
}
