package com.asijaandroidsolution.bloodbank.fragments.congrats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.databinding.FragmentCongratsBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;

public class CongratsFragment extends BaseFragment {
    private FragmentCongratsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCongratsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }
}
