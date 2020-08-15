package com.asijaandroidsolution.bloodbank.fragments.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.databinding.FragmentAboutBinding;
import com.asijaandroidsolution.bloodbank.databinding.FragmentSearchBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;

public class AboutFragment extends BaseFragment {
    private FragmentAboutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentAboutBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;


    }
}
