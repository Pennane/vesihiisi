package com.example.vesihiisi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * A global singleton for user preferences, activity data and trophies
 *
 * Inspiration https://stackoverflow.com/questions/19612993/writing-singleton-class-to-manage-android-sharedpreferences
 */
public class Global {
    private static final String PREF_KEY = "GLOBAL_PREFERENCES";
    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        if(preferences == null) {
            preferences = context.getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
        }
    }

    private Global() {

    }

    public static String read(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value).commit();
    }


    /**
     * Checks if integer is a valid age
     * @param age
     * @return Boolean
     */
    public static Boolean isValidAge(int age) {
        return age > 0 && age < 120;
    }

    /**
     * Checks if given string is a valid gender
     * @param gender
     * @return Boolean
     */
    public static Boolean isValidGender(String gender) {
        return  gender.equals("male") || gender.equals("female") || gender.equals("other");
    }

    /**
     * Checks if given integer is a valid weight
     * @param weight
     * @return Boolean
     */
    public static Boolean isValidWeight(int weight) {
        return weight > 0 && weight < 635;
    }



}
