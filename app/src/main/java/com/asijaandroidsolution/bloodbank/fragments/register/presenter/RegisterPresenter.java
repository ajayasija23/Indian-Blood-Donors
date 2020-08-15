package com.asijaandroidsolution.bloodbank.fragments.register.presenter;

import java.util.Map;

public interface RegisterPresenter {
    void registerInDb(Map<String, Object> user);
    void onSuccess();
    void onFailure(String message);
}
