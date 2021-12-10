package com.example.vesihiisi;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Utility class for miscellaneous methods
 */
public class Utilities {

    /**
     * A static utility method that hides the keyboard, provided that it is called from an Activity
     *
     * @param activity
     * @author Reto Meier
     * @see <a href="https://stackoverflow.com/a/17789187/11212780">How do you close/hide the Android soft keyboard programmatically?</a>
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Shows if two given dates exist on the same day
     *
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        boolean sameDay = dateFormatter.format(date1).equals(dateFormatter.format(date2));
        return sameDay;
    }

    /**
     * Get a random item from an ArrayList<></>
     *
     * @param arrayList ArrayList of any type
     * @param <T>       the item type of the ArrayList
     * @return an item of the received ArrayList
     */
    public static <T> T randomFromArrayList(ArrayList<T> arrayList) {
        int index = (int) (Math.random() * arrayList.size());
        return arrayList.get(index);
    }

    /**
     * Convert date to short finnish locale
     * eq. 1.12.2021
     *
     * @param date
     * @return string in dd.mm.yyyy
     */
    public static String dateToFinnishLocaleString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }
}
