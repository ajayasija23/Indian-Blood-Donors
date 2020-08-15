package com.asijaandroidsolution.bloodbank.fragments.search.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.asijaandroidsolution.bloodbank.fragments.search.SearchFragment;
import com.asijaandroidsolution.bloodbank.fragments.search.model.SearchModel;
import com.asijaandroidsolution.bloodbank.fragments.search.model.SearchModelImpl;
import com.asijaandroidsolution.bloodbank.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchPresenterImpl implements SearchPresenter {
    private FirebaseFirestore db;
    private SearchFragment searchFragment;
    private SearchModel searchModel;
    private String bloodGroup,state,district,city;
    private Context mContext;
    CollectionReference reference;
    private Query query;

    public SearchPresenterImpl(SearchFragment searchFragment) {
        this.searchFragment=searchFragment;
        searchModel=new SearchModelImpl(this);
        db = FirebaseFirestore.getInstance();
        reference=db.collection("Blood Donors");
    }

    @Override
    public void sendQuery(int type)
    {
        switch (type){
            case Constants.QUERY_BLOOD_GROUP:
                query=reference.whereEqualTo("Blood Group",bloodGroup);
                break;
            case Constants.QUERY_BLOOD_STATE:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                                .whereEqualTo("State",state);
                break;
            case Constants.QUERY_BLOOD_DISTRICT:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                                .whereEqualTo("District",district);
                break;
            case Constants.QUERY_BLOOD_CITY:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                        .whereEqualTo("City",city);
                break;
            case Constants.QUERY_BLOOD_STATE_DISTRICT:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                        .whereEqualTo("State",state)
                        .whereEqualTo("District",district);
                break;
            case Constants.QUERY_BLOOD_DISTRICT_CITY:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                        .whereEqualTo("District",district)
                        .whereEqualTo("City",city);
                break;
            case Constants.QUERY_BLOOD_STATE_CITY:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                        .whereEqualTo("State",state)
                        .whereEqualTo("City",city);
                break;
                case Constants.QUERY_All:
                query=reference.whereEqualTo("Blood Group",bloodGroup)
                        .whereEqualTo("State",state)
                        .whereEqualTo("District",district)
                        .whereEqualTo("City",city);
                break;

        }
        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        onSuccess(task.getResult());

                    } else {
                        Log.e("error:", "Error getting documents: ", task.getException());
                    }
                }
            });
        
    }

    @Override
    public void onSuccess(QuerySnapshot result) {
        searchFragment.onSuccess(result);
    }

    @Override
    public void onError() {

    }


    @Override
    public void setQueryParameters(String bloodGroup, String state, String district, String city)
    {
        this.bloodGroup=bloodGroup;
        this.state=state;
        this.district=district;
        this.city=city;
    }
}
