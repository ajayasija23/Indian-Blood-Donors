package com.asijaandroidsolution.bloodbank.fragments.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.BaseActivity;
import com.asijaandroidsolution.bloodbank.databinding.FragmentContactBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class ContactFragment extends BaseFragment implements View.OnClickListener {
    private FragmentContactBinding binding;
    BaseActivity baseActivity=new BaseActivity();
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentContactBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        binding.emailAddress.setOnClickListener(this);
        binding.contactInfo.setOnClickListener(this);
        binding.alternateNo.setOnClickListener(this);

        adView = new AdView(getActivity(),"304025074220898_304385857518153", AdSize.BANNER_HEIGHT_50);


        // Add the ad view to your activity layout
        binding.bannerContainerContact.addView(adView);

        // Request an ad
        adView.loadAd();
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.contact_info:
                openPhone(binding.contactInfo.getText());
                break;
            case R.id.email_address:
                openEmail();
                break;
            case R.id.alternate_no:
                openPhone(binding.alternateNo.getText());
                break;
        }

    }
    public void openPhone(CharSequence text)
    {
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + text));
        startActivity(intent1);
    }
    public void openEmail() {
        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{binding.emailAddress.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(getActivity().getPackageManager())!=null)
            startActivity(intent);
        else
            Toast.makeText(getActivity(),"Gmail App is not installed",Toast.LENGTH_SHORT).show();
    }

}
