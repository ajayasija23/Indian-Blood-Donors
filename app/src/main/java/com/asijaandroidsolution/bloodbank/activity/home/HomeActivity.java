package com.asijaandroidsolution.bloodbank.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.databinding.ActivityHomeBinding;
import com.asijaandroidsolution.bloodbank.databinding.ActivitySplashBinding;
import com.asijaandroidsolution.bloodbank.fragments.about.AboutFragment;
import com.asijaandroidsolution.bloodbank.fragments.contact.ContactFragment;
import com.asijaandroidsolution.bloodbank.fragments.register.RegisterFragment;
import com.asijaandroidsolution.bloodbank.fragments.search.SearchFragment;
import com.asijaandroidsolution.bloodbank.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private String mDrawerTitles="Register Blood Donor";
    private int mNavIndex=0;
    private Fragment fragment=new RegisterFragment();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mHandler=new Handler();
        setSupportActionBar(binding.includeToolBar.toolbar);
        settingUpNavView();
        loadFragment();
    }

    private void settingUpNavView() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_register:
                        mNavIndex=0;
                        mDrawerTitles= Constants.NAV_REGISTER;
                        fragment=new RegisterFragment();

                        break;
                    case R.id.nav_search:
                        mNavIndex=1;
                        mDrawerTitles= Constants.NAV_SEARCH;
                        fragment=new SearchFragment();

                        break;
                    case R.id.nav_about:
                        mNavIndex=2;
                        mDrawerTitles= Constants.NAV_ABOUT;
                        fragment=new AboutFragment();

                        break;
                    case R.id.nav_contact:
                        mNavIndex=3;
                        mDrawerTitles= Constants.NAV_CONTACT;
                        fragment=new ContactFragment();
                        break;


                }
                loadFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle=
                new ActionBarDrawerToggle(this,binding.drawerLayout,binding.includeToolBar.toolbar,
                        R.string.open_drawer,R.string.close_drawer){
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };

            binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();


    }

    private void loadFragment() {
        getSupportActionBar().setTitle(mDrawerTitles);
         binding.navView.getMenu().getItem(mNavIndex).setChecked(true);
       Runnable runnable= new Runnable(){
            @Override
            public void run() {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment);
                transaction.commitAllowingStateLoss();
            }
        };
        mHandler.post(runnable);
        binding.drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }

}