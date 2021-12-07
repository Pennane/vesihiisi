package com.example.vesihiisi;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


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
}
