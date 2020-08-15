package com.asijaandroidsolution.bloodbank.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class FrequentFunction {

    public static int calculateAge(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
        Date date1 = null;
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1 == null)
            return -1;
        Calendar birth = Calendar.getInstance();
        birth.setTime(date1);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }

        return yearDifference;
    }

}
