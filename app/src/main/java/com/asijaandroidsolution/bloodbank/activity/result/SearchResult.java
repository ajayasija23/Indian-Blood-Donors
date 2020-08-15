package com.asijaandroidsolution.bloodbank.activity.result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.databinding.ActivityHomeBinding;
import com.asijaandroidsolution.bloodbank.databinding.ActivitySearchResultBinding;
import com.asijaandroidsolution.bloodbank.fragments.search.adapter.RecyclerViewAdapter;
import com.asijaandroidsolution.bloodbank.utils.Constants;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class SearchResult extends AppCompatActivity {

    private ActivitySearchResultBinding binding;
    private RecyclerViewAdapter recyclerViewAdapter;
    private QuerySnapshot result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewDonorsList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        binding.recyclerViewDonorsList.addItemDecoration(dividerItemDecoration);
        binding.recyclerViewDonorsList.setHasFixedSize(true);
        recyclerViewAdapter=new RecyclerViewAdapter(Constants.result,this);
        binding.recyclerViewDonorsList.setAdapter(recyclerViewAdapter);
        getSupportActionBar().setTitle("Search Result");
    }
}