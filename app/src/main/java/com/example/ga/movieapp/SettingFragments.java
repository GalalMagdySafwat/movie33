package com.example.ga.movieapp;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;



public class SettingFragments extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movie_sort_by);
    }
}
