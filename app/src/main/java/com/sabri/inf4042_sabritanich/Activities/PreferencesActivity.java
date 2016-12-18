package com.sabri.inf4042_sabritanich.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.sabri.inf4042_sabritanich.R;


public class PreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static SharedPreferences preferences;
    public static ListPreference languePref;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        final String langue = preferences.getString("langue","Français");
        if(langue.equals("English")) addPreferencesFromResource(R.xml.preferences_en);
        else  addPreferencesFromResource(R.xml.preferences_fr);

        languePref = (ListPreference) findPreference("langue");
        languePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("langue",o.toString());
                Toast.makeText(PreferencesActivity.this,o.toString(),Toast.LENGTH_SHORT).show();
                editor.commit();
                return false;
            }
        });


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("langue")){

            PreferencesActivity.this.finish();
            startActivity(new Intent(PreferencesActivity.this,PreferencesActivity.class));
        }

    }


}
