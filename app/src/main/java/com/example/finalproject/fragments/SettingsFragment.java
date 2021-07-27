package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.finalproject.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}



