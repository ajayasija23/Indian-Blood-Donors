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
                binding.textViewTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       Intent intent= new Intent(SplashActivity.this, HomeActivity.class);
                        Pair[] pairs=new Pair[3];
                        pairs[0]=new Pair<View,String>(binding.imageLogo,"blood_logo");
                        pairs[1]=new Pair<View,String>(binding.textViewTitle,"logo_text");
                        pairs[2]=new Pair<View,String>(binding.textViewSubTitle,"logo_text");


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                            startActivity(intent,options.toBundle());
                            finish();

                        }

                        finish();

                    }
                },3000);

            }
        },2000);


    }
}
