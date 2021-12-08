package com.example.vesihiisi;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Utility class for miscellaneous methods
 */
public class Utilities {

    /**
     * A static utility method that hides the keyboard, provided that it is called from an Activity
     * @param activity
     * @author https://stackoverflow.com/questions/4967418/using-shared-preferences-editor
     * @author https://stackoverflow.com/users/3416196/howard
     * @version 7-12-2021
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
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        Boolean sameDay = dateFormatter.format(date1).equals(dateFormatter.format(date2));
        return sameDay;
    }
}
