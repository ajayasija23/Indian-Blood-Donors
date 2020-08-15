package com.asijaandroidsolution.bloodbank.utils;

import android.app.Activity;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StateAndDistrictList {
    private String json;
    private ArrayList<String> list=new ArrayList<String>();
    private ArrayList<String> districtList=new ArrayList<String>();
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public StateAndDistrictList(Activity activity)
    {
        setStateList(activity);
    }

    public String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("state_city.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void setStateList(Activity activity){
        json= loadJSONFromAsset(activity);
        //fetch JsonArray
        try {
            jsonObject= new JSONObject(json);
            jsonArray=jsonObject.getJSONArray("states");

            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject object= (JSONObject) jsonArray.get(i);
                list.add(object.getString("state"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getStateList(){
        return list;
    }
    public void setDistrictList(String state) {

        int i= list.indexOf(state);

        try {
            JSONObject object= (JSONObject) jsonArray.get(i);
            JSONArray districtArray=object.getJSONArray("districts");
            for (int j = 0; j <districtArray.length() ; j++) {
                districtList.add(districtArray.get(j).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDistrictList() {
        return districtList;
    }
}
