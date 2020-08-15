package com.asijaandroidsolution.bloodbank.fragments.register.model;

import java.util.Map;

public interface RegisterModel {
     void registerUser(Map<String, Object> user);
    void registerSuccessfully();
    void registerError(String message);
}
