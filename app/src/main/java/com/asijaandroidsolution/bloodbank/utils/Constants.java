package com.asijaandroidsolution.bloodbank.utils;

import com.asijaandroidsolution.bloodbank.fragments.search.SearchFragment;
import com.google.firebase.firestore.QuerySnapshot;

public class Constants {
    public static final int VERIFY_REQUEST = 101;
    public static final int QUERY_BLOOD_GROUP = 0;
    public static final int QUERY_BLOOD_STATE = 1;
    public static final int QUERY_BLOOD_DISTRICT = 2;
    public static final int QUERY_BLOOD_CITY = 3;
    public static final int QUERY_BLOOD_STATE_DISTRICT = 4;
    public static final int QUERY_BLOOD_DISTRICT_CITY = 5;
    public static final int QUERY_BLOOD_STATE_CITY = 6;
    public static final int QUERY_All=7;
    public static String NAV_REGISTER="Register Blood Donor";
    public static String NAV_SEARCH="Search Blood Donors";
    public static String NAV_ABOUT="About";
    public static String NAV_CONTACT="Contact Us";
    public static QuerySnapshot result=null;

}