package com.asijaandroidsolution.bloodbank.fragments.register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.asijaandroidsolution.bloodbank.R;
import com.asijaandroidsolution.bloodbank.activity.verify.VerifyActivity;
import com.asijaandroidsolution.bloodbank.databinding.FragmentRegisterBinding;
import com.asijaandroidsolution.bloodbank.fragments.BaseFragment;
import com.asijaandroidsolution.bloodbank.fragments.congrats.CongratsFragment;
import com.asijaandroidsolution.bloodbank.fragments.register.presenter.RegisterPresenterImpl;
import com.asijaandroidsolution.bloodbank.fragments.register.view.RegisterFragmentView;
import com.asijaandroidsolution.bloodbank.utils.StateAndDistrictList;
import com.asijaandroidsolution.bloodbank.utils.Constants;
import com.asijaandroidsolution.bloodbank.utils.FrequentFunction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener, RegisterFragmentView {

    private StateAndDistrictList stateAndDistrictList;
    private FragmentRegisterBinding binding;
    private JSONObject object=null;
    private ArrayAdapter adapter;
    private String json;
    private ArrayList<String> list=new ArrayList<String>();
    private ArrayList<String> districtList=new ArrayList<String>();
    private ArrayList<String> bloodGroupsList=new ArrayList<String>();
    JSONObject jsonObject;
    JSONArray jsonArray;
    private RegisterPresenterImpl presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        presenter=new RegisterPresenterImpl(this);

        stateAndDistrictList=new StateAndDistrictList(getActivity());
        //iniallizing disrict sppiner adapter
        setStatesList();
        setBloodGroupList();
        setListeners();
        return view;
    }

    private void setListeners() {
        binding.dobEndIcon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,RegisterFragment.this, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        binding.sppinerStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String state= adapterView.getItemAtPosition(i).toString();
                districtList.clear();
                binding.spinnerDistrict.setText(null);
                stateAndDistrictList.setDistrictList(state);
                districtList=stateAndDistrictList.getDistrictList();
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, districtList);
                binding.spinnerDistrict.setAdapter(adapter);
            }
        });
        binding.btnRegister.setOnClickListener(this);
        binding.privacypolicy.setOnClickListener(this);
        binding.fullName.setOnFocusChangeListener(this);
        binding.sppinerStates.setOnFocusChangeListener(this);
        binding.spinnerDistrict.setOnFocusChangeListener(this);
        binding.cityName.setOnFocusChangeListener(this);
        binding.mobile.setOnFocusChangeListener(this);
        binding.email.setOnFocusChangeListener(this);
        binding.bloodGroup.setOnFocusChangeListener(this);

    }

    private boolean validateAge() {
        int age= FrequentFunction.calculateAge(binding.dob.getText().toString());
        if (age==-1) {
            binding.dobEndIcon.setError("Invalid age(DD-MM-YYYY)");
            return false;
        }
        else if(age<18) {
            binding.dobEndIcon.setError("Age must be greater than 18");

            return false;
        }
        return true;
    }

    private void setBloodGroupList() {

        bloodGroupsList=new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.blood_groups)));
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,bloodGroupsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.bloodGroup.setAdapter(adapter);

    }

    private void setStatesList() {
        list=stateAndDistrictList.getStateList();
        Log.d("state-1",list.get(0));
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        binding.sppinerStates.setAdapter(adapter);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btnRegister:
                binding.btnRegister.requestFocus();
                registerUser();
                break;
            case R.id.privacypolicy:
                browsePolicy();
                break;
        }

    }

    private void browsePolicy() {
        Uri uri = Uri.parse("https://privacypolicyindianblooddonors.blogspot.com/2020/08/privacy-policy.html"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void registerUser() {
        boolean valid=checkAllFields();
        if(valid)
        {
            Intent intent = new Intent(getActivity(), VerifyActivity.class);
            intent.putExtra("phoneNo","+91"+binding.mobile.getText().toString());
            startActivityForResult(intent, Constants.VERIFY_REQUEST);
        }

    }

    private boolean checkAllFields() {
        boolean valid=true;


        if(binding.fullName.getText().toString().length()==0){
            binding.nameError.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.sppinerStates.getText().toString().length()==0){
            binding.stateError.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.spinnerDistrict.getText().toString().length()==0){
            binding.districtError.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.dob.getText().toString().length()==0){
            binding.dobEndIcon.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.cityName.getText().toString().length()==0){
            binding.cityError.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.email.getText().toString().length()==0){
            binding.emailError.setError("This Field can not be empty");
            valid= false;
        }
        if(binding.bloodGroup.getText().toString().length()==0){
            binding.bloodError.setError("This Field can not be empty");
            valid= false;
        }
        if (binding.mobile.getText().toString().length()<10) {
            if(binding.mobile.getText().toString().length()==0){
                binding.contactError.setError("This Field can not be empty");
            }
            else
                binding.contactError.setError("Enter valid mobile no ");

            valid= false;
        }
        if(!list.contains(binding.sppinerStates.getText().toString())){
            binding.stateError.setError("Please Select valid state");
            valid= false;
        }
        if(!districtList.contains(binding.spinnerDistrict.getText().toString())){
            binding.districtError.setError("Please Select valid district");
            valid= false;
        }
        if(!bloodGroupsList.contains(binding.bloodGroup.getText().toString())){
            binding.bloodError.setError("Please Select valid district");
            valid= false;
        }
        valid=validateAge();

        return valid;

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        binding.dob.setText(i2 + "-" + (i1 + 1) + "-" + i);
        binding.dob.clearFocus();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constants.VERIFY_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                binding.progressBarSaving.setVisibility(View.VISIBLE);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", binding.fullName.getText().toString());
                user.put("DOB", binding.dob.getText().toString());
                user.put("State", binding.sppinerStates.getText().toString());
                user.put("District", binding.spinnerDistrict.getText().toString());
                user.put("City", binding.cityName.getText().toString());
                user.put("Mobile", binding.mobile.getText().toString());
                user.put("Email", binding.email.getText().toString());
                user.put("Blood Group", binding.bloodGroup.getText().toString());
                presenter.registerInDb(user);
            }
        }
    }


    @Override
    public void onFocusChange(View view, boolean b) {

        if(b){
            switch (view.getId())
            {
                case R.id.fullName:
                    binding.nameError.setError(null);
                    break;
                case R.id.bloodGroup:
                                    binding.bloodError.setError(null);
                                    break;
                case R.id.sppinerStates:
                                    binding.stateError.setError(null);
                                    break;
                case R.id.spinnerDistrict:
                                    binding.districtError.setError(null);
                                    break;
                case R.id.cityName:
                                    binding.cityError.setError(null);
                                    break;
                case R.id.mobile:
                                    binding.contactError.setError(null);
                                    break;
                case R.id.email:
                                    binding.emailError.setError(null);
                                    break;
                case R.id.dob:
                                    binding.dobEndIcon.setError(null);
                                    break;
            }
        }
    }

    @Override
    public void onSuccess() {
        FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new CongratsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onFailure(String message) {
        binding.progressBarSaving.setVisibility(View.GONE);
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
}
