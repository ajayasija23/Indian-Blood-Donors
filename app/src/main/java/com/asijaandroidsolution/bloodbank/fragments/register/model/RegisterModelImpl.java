package com.asijaandroidsolution.bloodbank.fragments.register.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.asijaandroidsolution.bloodbank.fragments.register.presenter.RegisterPresenterImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterModelImpl implements RegisterModel {
    RegisterPresenterImpl registerPresenter;

    public RegisterModelImpl(RegisterPresenterImpl registerPresenter) {
        this.registerPresenter=registerPresenter;
    }

    @Override
    public void registerUser(final Map<String, Object> user) {
        final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final DocumentReference docIdRef = rootRef.collection("Blood Donors").document(user.get("Mobile").toString());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //document already exist show error to the user
                        Log.d(TAG, "Document exists!");
                        registerError("phone no already exist");
                    }
                    else {
                        Log.d(TAG, "Document does not exist!");
                        //should be added in databas
                        docIdRef.set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //data written successfully
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        registerSuccessfully();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                        registerError(e.getMessage());
                                        //document can not be added
                                    }
                                });
                    }
                }
                else {
                    Log.d(TAG, "Failed with: ", task.getException());
                    registerError(task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public void registerSuccessfully() {
        registerPresenter.onSuccess();
    }

    @Override
    public void registerError(String message) {
        registerPresenter.onFailure(message);
    }
}
