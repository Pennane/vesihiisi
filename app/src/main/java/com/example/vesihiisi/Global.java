package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.isSameDay;

import android.annotation.SuppressLint;
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
 * A global singleton for shared preferences
 * and activity day data (which is also stored in shared preferences but as serialized JSON )
 *
 * @author arttupennanen
 * @see <a href="https://stackoverflow.com/a/40347393/11212780">inspiration for shared preferences singleton</a>
 */
@SuppressWarnings("ALL")
public class Global {
    private static final String PREF_KEY = "GLOBAL_PREFERENCES";
    private static SharedPreferences preferences;
    private static Gson gson;

    private Global() {
    }

    /**
     * Initialize json and shared preferences through static call.
     * HAS to be used before other methods of the singleton are to be used.
     *
     * @param context of MainActivity
     */
    public static void initialize(Context context) {
        if (gson == null) {
            gson = new Gson();
        }

        if (preferences == null) {
            preferences = context.getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
        }

    }

    /**
     * Reads stored day data JSON from shared preferences.
     * If there is no stored data, returns an empty ArrayList.
     *
     * @return complete ArrayList of stored DayDatas
     */
    public static ArrayList<DayData> readDayDataList() {
        String json = readPreference("dayDataHistory", "");

        // If nothing is stored, return an empty ArrayList
        if (json.equals("")) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<ArrayList<DayData>>() {
        }.getType();

        ArrayList<DayData> dayDataList = gson.fromJson(json, listType);
        return dayDataList;
    }

    /**
     * Overwrites stored JSON DayData ArrayList.
     *
     * @param dayDataList complete ArrayList of dayDatas to store
     */
    public static void writeDayDataList(ArrayList<DayData> dayDataList) {
        String json = gson.toJson(dayDataList);
        writePreference("dayDataHistory", json);
    }

    /**
     * Returns a day data object that has the requested date associated with it.
     * If there is nothing stored for that date, creates an empty dayData object for it.
     * <p>
     * Also validates and updates the preferences affecting water consumption values
     *
     * @param date date object for the dayData to search for
     * @return DayData that shares the received date
     */
    public static DayData readSpecificDayData(Date date) {
        ArrayList<DayData> savedDayDataList = readDayDataList();
        Optional<DayData> possibleDayData = savedDayDataList
                .stream()
                .filter(d -> isSameDay(d.getDate(), date))
                .findAny();

        if (!possibleDayData.isPresent()) {
            return new DayData(
                    Global.readPreference("age", 30),
                    Global.readPreference("weight", 70),
                    Global.readPreference("gender", "invalid")
            );
        }

        DayData savedDayData = possibleDayData.get();
        Date now = new Date();

        // Keep the preferences up to date
        if (isSameDay(savedDayData.getDate(), now)) {
            int newAge = Global.readPreference("age", savedDayData.getAge());
            int newWeight = Global.readPreference("weight", savedDayData.getWeight());
            String newGender = Global.readPreference("gender", savedDayData.getGender());
            int newTargetConsumption = DayData.calculateTargetConsumption(newAge, newWeight, newGender);
            savedDayData.setAge(newAge);
            savedDayData.setWeight(newWeight);
            savedDayData.setGender(newGender);
            savedDayData.setTargetConsumption(newTargetConsumption);
        }

        return savedDayData;
    }

    /**
     * Overwrites dayData for a specific date.
     *
     * @param dayData object to add or replace from the DayData storage
     * @param date    date to overwrite the dayData at
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

    /**
     * Read stored string value
     *
     * @param key      that the value is stored behind
     * @param defValue default String value to fallback to
     * @return String
     */
    public static String readPreference(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    /**
     * Write string value to shared preferenes
     *
     * @param key   that the value should be stored behind
     * @param value String value to write
     */
    @SuppressLint("ApplySharedPref")
    public static void writePreference(String key, String value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    /**
     * Read stored integer value
     *
     * @param key      that the value is stored behind
     * @param defValue default integer value to fallback to
     * @return int
     */
    public static int readPreference(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    /**
     * Write integer value to shared preferenes
     *
     * @param key   that the value should be stored behind
     * @param value integer value to write
     */
    @SuppressLint("ApplySharedPref")
    public static void writePreference(String key, int value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }


    /**
     * Checks if given integer is a valid age
     *
     * @param age in years
     * @return boolean
     */
    public static boolean isValidAge(Integer age) {
        if (age == null) {
            return false;
        }
        return age >= 1 && age <= 120;
    }

    /**
     * Checks if given string is a valid gender
     *
     * @param gender as "male", "female" or "other"
     * @return boolean
     */
    public static boolean isValidGender(String gender) {
        return (gender.equals("male") || gender.equals("female") || gender.equals("other"));
    }

    /**
     * Checks if given integer is a valid weight
     *
     * @param weight in kilos
     * @return boolean
     */
    public static boolean isValidWeight(Integer weight) {
        if (weight == null) {
            return false;
        }
        return (weight > 0 && weight < 635);
    }
}
