package com.asijaandroidsolution.bloodbank.fragments.search.presenter;

import com.google.firebase.firestore.QuerySnapshot;

public interface SearchPresenter  {

    void onSuccess(QuerySnapshot result);
    void onError();
    void sendQuery(int type);

    void setQueryParameters(String bloodGroup, String state, String district, String city);
}
