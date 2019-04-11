package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        int numOfPreferences = getPreferenceScreen().getPreferenceCount();

        for (int i = 0; i < numOfPreferences; i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                String currentValue = sharedPreferences.getString(preference.getKey(), null);
                setPreferenceSummary(preference, currentValue);
            }
        }

    }

    private void setPreferenceSummary(Preference preference, Object object) {
        preference.setSummary(object.toString());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(key, null);
                setPreferenceSummary(preference, value);
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
