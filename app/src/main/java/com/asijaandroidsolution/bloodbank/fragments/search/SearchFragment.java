package com.asijaandroidsolution.bloodbank.fragments.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.result.SearchResult;
import com.asijaandroidsolution.bloodbank.databinding.FragmentSearchBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;
import com.asijaandroidsolution.bloodbank.fragments.search.adapter.RecyclerViewAdapter;
import com.asijaandroidsolution.bloodbank.fragments.search.presenter.SearchPresenter;
import com.asijaandroidsolution.bloodbank.fragments.search.presenter.SearchPresenterImpl;
import com.asijaandroidsolution.bloodbank.fragments.search.view.SearchView;
import com.asijaandroidsolution.bloodbank.utils.Constants;
import com.asijaandroidsolution.bloodbank.utils.StateAndDistrictList;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFragment extends BaseFragment implements View.OnClickListener, SearchView, View.OnFocusChangeListener {
    private FragmentSearchBinding binding;
    private StateAndDistrictList stateAndDistrictList;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> districtList;
    private ArrayList<String> bloodGroupList;
    private SearchPresenter presenter;
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentSearchBinding.inflate(inflater,container,false);
        View view=binding.getRoot();

       createFacebookAd();
        presenter=new SearchPresenterImpl(this);
        districtList=new ArrayList<String>();
        stateAndDistrictList=new StateAndDistrictList(getActivity());
        setStatesList();
        stateListListener();
        setBloodGroupList();
        binding.btnSearch.setOnClickListener(this);
        binding.spinnerDistrictSearch.setOnFocusChangeListener(this);
        binding.bloodGroupSelect.setOnFocusChangeListener(this);
        binding.sppinerStatesSearch.setOnFocusChangeListener(this);
        return view;
    }

    private void createFacebookAd() {
        adView = new AdView(getActivity(),"304025074220898_304032264220179",AdSize.BANNER_HEIGHT_50);


        // Add the ad view to your activity layout
        binding.bannerContainer.addView(adView);

        // Request an ad
        adView.loadAd();
    }


    private void setStatesList() {
        list=stateAndDistrictList.getStateList();
        Log.d("state-1",list.get(0));
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        binding.sppinerStatesSearch.setAdapter(adapter);
    }

    private void stateListListener() {
        binding.sppinerStatesSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String state= adapterView.getItemAtPosition(i).toString();
                districtList.clear();
                binding.spinnerDistrictSearch.setText(null);
                stateAndDistrictList.setDistrictList(state);
                districtList=stateAndDistrictList.getDistrictList();
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, districtList);
                binding.spinnerDistrictSearch.setAdapter(adapter);
            }
        });

    }
    private void setBloodGroupList() {
        bloodGroupList=new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.blood_groups)));
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,bloodGroupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.bloodGroupSelect.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        boolean valid=true;
        if(view.getId()==R.id.btnSearch)
        {
            String bloodGroup=binding.bloodGroupSelect.getText().toString();
            String state=binding.sppinerStatesSearch.getText().toString();
            String district=binding.spinnerDistrictSearch.getText().toString();
            String city=binding.cityNameSearch.getText().toString();
            if(bloodGroup.isEmpty())
            {
                binding.bloodGroupSearch.setError("Blood Group can not be empty");
                valid=false;
            }
            if(!bloodGroupList.contains(binding.bloodGroupSelect.getText().toString())){
                binding.bloodGroupSearch.setError("please select valid blood group");
                valid=false;
            }
            if(!list.contains(binding.sppinerStatesSearch.getText().toString())&&
                    !binding.sppinerStatesSearch.getText().toString().isEmpty()){
                binding.stateError.setError("please select valid State");
                valid=false;
            }
            if(!districtList.contains(binding.spinnerDistrictSearch.getText().toString())&&
                    !binding.spinnerDistrictSearch.getText().toString().isEmpty()){
                binding.districtSearchError.setError("please select valid District");
                valid=false;
            }
            if(valid) {
                presenter.setQueryParameters(bloodGroup, state, district, city);
                if (state.isEmpty() && district.isEmpty() && city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_GROUP);

                else if (!state.isEmpty() && district.isEmpty() && city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_STATE);

                else if (state.isEmpty() && !district.isEmpty() && city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_DISTRICT);

                else if (state.isEmpty() && district.isEmpty() && !city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_CITY);

                else if (!state.isEmpty() && !district.isEmpty() && city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_STATE_DISTRICT);

                else if (state.isEmpty() && !district.isEmpty() && !city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_DISTRICT_CITY);

                else if (!state.isEmpty() && district.isEmpty() && !city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_BLOOD_STATE_CITY);

                else if (!state.isEmpty() && !district.isEmpty() && !city.isEmpty())
                    presenter.sendQuery(Constants.QUERY_All);
            }
        }
    }

    @Override
    public void onSuccess(QuerySnapshot result)
    {
        Constants.result=result;
        Intent intent =new Intent(getActivity(),SearchResult.class);
        startActivity(intent);
    }



    @Override
    public void onError() {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            binding.bloodGroupSearch.setError(null);
            binding.stateError.setError(null);
            binding.districtSearchError.setError(null);
        }
    }
}
