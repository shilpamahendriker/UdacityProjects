package com.example.avni.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;


public class ResetToDefaultPreference extends DialogPreference {
    public ResetToDefaultPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {

            //Get this application SharedPreferences editor
            SharedPreferences.Editor preferencesEditor = PreferenceManager.getDefaultSharedPreferences(this.getContext()).edit();
            //Clear all the saved preference values.
            preferencesEditor.clear();
            //Read the default values and set them as the current values.
            PreferenceManager.setDefaultValues(getContext(), R.xml.settings_main, true);
            //Commit all changes.
            preferencesEditor.commit();

            Intent intent = new Intent(getContext(),MainActivity.class);
            getContext().startActivity(intent);

        }
    }
}
