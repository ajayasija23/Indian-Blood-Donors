package com.asijaandroidsolution.bloodbank.fragments.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.BaseActivity;
import com.asijaandroidsolution.bloodbank.activity.result.SearchResult;
import com.asijaandroidsolution.bloodbank.fragments.contact.ContactFragment;
import com.google.firebase.firestore.QuerySnapshot;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private QuerySnapshot result;
    public SearchResult searchResult;
    public RecyclerViewAdapter(QuerySnapshot result, SearchResult searchResult) {
        this.result=result;
        this.searchResult=searchResult;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.search_list_adapter,parent,false);
        return new MyViewHolder(view,searchResult);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        if(result.size()==0) {
            holder.noRecord.setVisibility(View.VISIBLE);
            holder.container.setVisibility(View.GONE);
        }
        else {
            holder.donorName.setText(result.getDocuments().get(position).get("Name").toString());
            holder.donorBloodGroup.setText(result.getDocuments().get(position).get("Blood Group").toString());
            holder.donorCity.setText(result.getDocuments().get(position).get("City").toString());
            holder.donorPhoneNo.setText(result.getDocuments().get(position).get("Mobile").toString());
            holder.donorEmail.setText(result.getDocuments().get(position).get("Email").toString());
        }
    }

    @Override
    public int getItemCount() {
        if (result.size()==0)
            return 1;
        else
            return result.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView donorName,donorCity,donorBloodGroup,donorPhoneNo,donorEmail,noRecord;
        View container;
        ImageButton composeMail,dialPhone;
        SearchResult searchResult;
        private BaseActivity baseActivity=new BaseActivity();
        ContactFragment contactFragment=new ContactFragment();
        public MyViewHolder(View view, SearchResult searchResult) {
            super(view);
            this.searchResult=searchResult;
            donorName=view.findViewById(R.id.donor_name);
            donorCity=view.findViewById(R.id.donor_city_name);
            donorBloodGroup=view.findViewById(R.id.donor_blood_group);
            donorPhoneNo=view.findViewById(R.id.donor_contact_no);
            donorEmail=view.findViewById(R.id.donor_email);
            noRecord=view.findViewById(R.id.no_record_found);
           // container=view.findViewById(R.id.record_container);
            composeMail=view.findViewById(R.id.composeMail);
            dialPhone=view.findViewById(R.id.dialPhone);
            container=view.findViewById(R.id.searchDataContainer);

            composeMail.setOnClickListener(this);
            dialPhone.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.composeMail:
                    openEmail();
                    break;
                case R.id.dialPhone:
                    openPhone();
                    break;
            }
        }
        public void openPhone()
        {
            Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + donorPhoneNo.getText()));
            searchResult.startActivity(intent1);
        }
        public void openEmail() {
            Intent intent = new Intent (Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{donorEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            intent.setPackage("com.google.android.gm");
            if (intent.resolveActivity(searchResult.getPackageManager())!=null)
                searchResult.startActivity(intent);
            else
                Toast.makeText(searchResult,"Gmail App is not installed",Toast.LENGTH_SHORT).show();
        }
    }


}
