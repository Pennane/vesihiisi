package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.isSameDay;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * A global singleton for user preferences and activity day data (which is stored as JSON)
 *
 * Inspiration for shared preferences
 * singleton from https://stackoverflow.com/questions/19612993/writing-singleton-class-to-manage-android-sharedpreferences
 */
public class Global {
    private static final String PREF_KEY = "GLOBAL_PREFERENCES";
    private static SharedPreferences preferences;
    private static Gson gson;

    private Global() {}

    /**
     * Initialize json and shared preferences through static call.
     * @param context
     */
    public static void initialize(Context context) {
       if (gson == null) {
           gson = new Gson();
       }

        if(preferences == null) {
            preferences = context.getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
        }

    }

    /**
     * Reads stored day data JSON from shared preferences.
     * If there is no stored data, returns an empty ArrayList.
     * @return
     */
    public static ArrayList<DayData> readDayDataList() {
        String json = readPreference("dayDataHistory", "");

        // If nothing is stored, return an empty ArrayList
        if (json.equals("")) {
            return new ArrayList<DayData>();
        }

        Type listType = new TypeToken<ArrayList<DayData>>(){}.getType();
        ArrayList<DayData> dayDataList = gson.fromJson(json, listType);
        return dayDataList;
    }

    /**
     * Overwrites stored JSON day data.
     * @param dayDataList
     */
    public static void writeDayDataList(ArrayList<DayData> dayDataList) {
        String json = gson.toJson(dayDataList);
        writePreference("dayDataHistory", json);
    }

    /**
     * Returns a day data object that has the requested date associated with it.
     * If there is nothing stored for that date, creates an empty dayData object for it.
     * @param date
     * @return
     */
    public static DayData readSpecificDayData(Date date) {
        ArrayList<DayData> savedDayDataList = readDayDataList();
        Optional<DayData> savedDayData = savedDayDataList
                .stream()
                .filter(d -> isSameDay(d.getDate(), date))
                .findAny();

        if (!savedDayData.isPresent()) {
            return new DayData(Global.readPreference("age", 30), Global.readPreference("weight", 70));
        }
        return savedDayData.get();
    }

    /**
     * Overwrites dayData for a specific date.
     * @param dayData
     * @param date
     */
    public static void writeSpecificDayData(DayData dayData, Date date) {
        ArrayList<DayData> savedDayDataList = readDayDataList();
        Optional<DayData> alreadyStoredDayData = savedDayDataList.stream().filter(d -> isSameDay(d.getDate(), date)).findAny();

        if (alreadyStoredDayData.isPresent()) {
            ArrayList<DayData> convertedList = new ArrayList<>();
            savedDayDataList.stream().forEach(d -> {
                if (isSameDay(d.getDate(), date)) {
                    convertedList.add(dayData);
                } else {
                    convertedList.add(d);
                }
            });
            writeDayDataList(convertedList);
            return;
        }

        savedDayDataList.add(dayData);
        writeDayDataList(savedDayDataList);
    }

    public static String readPreference(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static void writePreference(String key, String value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static Integer readPreference(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void writePreference(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value).commit();
    }


    /**
     * Checks if given integer is a valid age
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
