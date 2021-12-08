package com.example.vesihiisi;
import static com.example.vesihiisi.Utilities.hideKeyboard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends NavigationBarActivity {
    private EditText editTextAge, editTextWeight;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonGender;

    private String gender;
    private int age;
    private int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextAge = (EditText) findViewById(R.id.editTextNumberAge);
        editTextWeight = (EditText) findViewById(R.id.editTextNumberWeight);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioButtonGender = (RadioButton) findViewById(selectedId);

        gender = Global.readPreference("gender","unset");
        age = Global.readPreference("age",0);
        weight = Global.readPreference("weight",0);

        editTextAge.setText(Integer.toString(age));
        editTextWeight.setText(Integer.toString(weight));

        if (gender.toLowerCase() == "male") {
            ((RadioButton) radioGroupGender.getChildAt(0)).setChecked(true);
        } else if (gender.toLowerCase() == "female") {
            ((RadioButton) radioGroupGender.getChildAt(1)).setChecked(true);
        } else if (gender.toLowerCase() == "other") {
            ((RadioButton) radioGroupGender.getChildAt(2)).setChecked(true);
        }

        // If any of the set values are invalid, hide the navigation bar
        if(!Global.isValidAge(age) ||!Global.isValidGender(gender) || !Global.isValidWeight(weight)) {
            hideNavigationBar();
        }
    }


    /**
     * Checks if preferences stored in the object instance are valid.
     * If any of the preferences are invalid, summons a toast.
     * @return Boolean
     */
    private Boolean isValidInput() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        if (!Global.isValidAge(this.age)) {
            CharSequence text = "Ikä voi olla vain 1-120";
            toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        } else if (!Global.isValidWeight(this.weight)) {
            CharSequence text = "Paino voi olla vain 1-635";
            toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        } else if (!Global.isValidGender(this.gender)) {
            CharSequence text = "Valitse sukupuoli!";
            toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        return true;
    }

    /**
     * Reads input from the activity and stores them into the object instance
     */
    private void readInput() {

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioButtonGender = (RadioButton) findViewById(selectedId);

        if (radioButtonGender == findViewById(R.id.radioButtonMale)) {
            gender = "male";
        } else if (radioButtonGender == findViewById(R.id.radioButtonFemale)) {
            gender = "female";
        } else if (radioButtonGender == findViewById(R.id.radioButtonOtherGender)) {
            gender = "other";
        }

        age = Integer.parseInt(editTextAge.getText().toString());
        weight = Integer.parseInt(editTextWeight.getText().toString());
    }

    /**
     * Validates input and saves them into the shared preferences.
     * @param view
     */
    public void saveSettings(View view) {

        hideKeyboard(this);

        readInput();

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
        CharSequence text = "Asetukset tallennettu!";
        toast = Toast.makeText(context, text, duration);
        toast.show();

        // Return the navigation bar
        showNavigationBar();
    }
}