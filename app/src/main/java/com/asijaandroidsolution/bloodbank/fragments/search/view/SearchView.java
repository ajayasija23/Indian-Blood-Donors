package com.asijaandroidsolution.bloodbank.fragments.search.view;

import com.google.firebase.firestore.QuerySnapshot;

public interface SearchView {
    void onSuccess(QuerySnapshot result);
    void onError();
}
