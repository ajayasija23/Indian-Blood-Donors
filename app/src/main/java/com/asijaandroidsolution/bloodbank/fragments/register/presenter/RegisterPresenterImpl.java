package com.asijaandroidsolution.bloodbank.fragments.register.presenter;

import com.asijaandroidsolution.bloodbank.fragments.register.RegisterFragment;
import com.asijaandroidsolution.bloodbank.fragments.register.model.RegisterModel;
import com.asijaandroidsolution.bloodbank.fragments.register.model.RegisterModelImpl;

import java.util.Map;

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterFragment registerFragment;
    private RegisterModel registerModel;
    public RegisterPresenterImpl(RegisterFragment registerFragment) {
        this.registerFragment=registerFragment;
        registerModel=new RegisterModelImpl(this);
    }

    @Override
    public void registerInDb(Map<String, Object> user) {
        registerModel.registerUser(user);
    }

    @Override
    public void onSuccess() {
        registerFragment.onSuccess();
    }

    @Override
    public void onFailure(String message) {
        registerFragment.onFailure(message);
    }
}
