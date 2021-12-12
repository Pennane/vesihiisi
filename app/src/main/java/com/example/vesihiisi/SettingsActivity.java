package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.hideKeyboard;
import static com.example.vesihiisi.Utilities.safeParseInteger;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Activity for displaying and editing user settings.
 *
 * @author Nafisul Nazrul
 * @author Adnan Avni
 * @author Arttu Pennanen
 */
public class SettingsActivity extends NavigationBarActivity {
    private EditText editTextAge, editTextWeight;
    private RadioGroup radioGroupGender;

    private String gender;
    private Integer age;
    private Integer weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextAge = findViewById(R.id.editTextNumberAge);
        editTextWeight = findViewById(R.id.editTextNumberWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        gender = Global.readPreference("gender", "unset");
        age = Global.readPreference("age", 0);
        weight = Global.readPreference("weight", 0);

        // If any of the set values are invalid, hide the navigation bar
        if (!Global.isValidAge(age) || !Global.isValidGender(gender) || !Global.isValidWeight(weight)) {
            hideNavigationBar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        gender = Global.readPreference("gender", "unset");
        age = Global.readPreference("age", 0);
        weight = Global.readPreference("weight", 0);

        editTextAge.setText(Integer.toString(age));
        editTextWeight.setText(Integer.toString(weight));

        if (gender.equalsIgnoreCase("male")) {
            ((RadioButton) radioGroupGender.getChildAt(0)).setChecked(true);
        } else if (gender.equalsIgnoreCase("female")) {
            ((RadioButton) radioGroupGender.getChildAt(1)).setChecked(true);
        } else if (gender.equalsIgnoreCase("other")) {
            ((RadioButton) radioGroupGender.getChildAt(2)).setChecked(true);
        }
    }


    /**
     * Checks if preferences stored in the object instance are valid.
     * If any of the preferences are invalid, spawns a toast.
     *
     * @return boolean
     */
    private boolean isValidInput() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        if (!Global.isValidAge(this.age)) {
            toast = Toast.makeText(context, R.string.invalid_age, duration);
            toast.show();
            return false;
        } else if (!Global.isValidWeight(this.weight)) {
            toast = Toast.makeText(context, R.string.invalid_weight, duration);
            toast.show();
            return false;
        } else if (!Global.isValidGender(this.gender)) {
            toast = Toast.makeText(context, R.string.invalid_gender, duration);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * Reads input from the activity and stores those into the activity instance
     */
    private void storeInput() {
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButtonGender = findViewById(selectedId);

        if (radioButtonGender == findViewById(R.id.radioButtonMale)) {
            gender = "male";
        } else if (radioButtonGender == findViewById(R.id.radioButtonFemale)) {
            gender = "female";
        } else if (radioButtonGender == findViewById(R.id.radioButtonOtherGender)) {
            gender = "other";
        }

        age = safeParseInteger(editTextAge.getText().toString());
        weight = safeParseInteger(editTextWeight.getText().toString());
    }

    /**
     * Validates input and saves them into the global shared preferences.
     * <p>
     * Summons toasts to show status
     *
     * @param view of clicked save settings button
     */
    public void saveSettings(View view) {

        hideKeyboard(this);

        storeInput();

        if (!isValidInput()) {
            return;
        }

        // Apply changes to the global shared preferences
        Global.writePreference("gender", gender);
        Global.writePreference("age", age);
        Global.writePreference("weight", weight);

        // Show a toast as a confirmation message
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        toast = Toast.makeText(context, R.string.settings_stored, duration);
        toast.show();

        // Return the navigation bar
        showNavigationBar();
    }
}