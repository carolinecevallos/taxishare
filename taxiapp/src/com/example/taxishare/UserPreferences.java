package com.example.taxishare;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserPreferences extends PreferenceActivity 
{

		@SuppressWarnings("deprecation")
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}	
}
