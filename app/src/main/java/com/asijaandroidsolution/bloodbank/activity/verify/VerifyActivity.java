package com.asijaandroidsolution.bloodbank.activity.verify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.TaskExecutor;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.BaseActivity;
import com.asijaandroidsolution.bloodbank.databinding.ActivityVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends BaseActivity implements View.OnClickListener {
    private ActivityVerifyBinding binding;
    private String phoneNo;
    private String codeSent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnVerify.setOnClickListener(this);
        phoneNo= getIntent().getStringExtra("phoneNo");
        sendVerificationCodetoUser();
    }

    private void sendVerificationCodetoUser() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code= phoneAuthCredential.getSmsCode();
            Toast.makeText(getApplicationContext(),"in OnCompleted",Toast.LENGTH_LONG);
            if(code!=null)
            {

                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getApplicationContext(),"in failed",Toast.LENGTH_LONG);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }

    };

    private void verifyCode(String code) {
        binding.progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codeSent,code);
        signInByCredential(credential);
    }

    private void signInByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent replyIntent=new Intent();
                            setResult(RESULT_OK,replyIntent);
                            finish();

                        } else {
                            Toast.makeText(VerifyActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.btnVerify) {
            String code = binding.Otp.getText().toString();
            verifyCode(code);
        }
    }
}
