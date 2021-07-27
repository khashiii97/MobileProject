package com.example.finalproject.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_SCHOOL_WEBSITE_SETTING = "schoolwebsite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}