package com.example.taxishare;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MatchesActivity extends Activity {

	private static final int FIND_MATCH_TIMEOUT = 3000;
	TextView titleTextView;
	SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matches);
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean showLoad = sharedPrefs.getBoolean("showLoad", true);
		final Context context = this;
		titleTextView = (TextView) findViewById(R.id.matchesTextView);
		titleTextView.setText("");
		final ListView matchesList;
		matchesList = (ListView) findViewById(R.id.matchesListView);
		matchesList.setVisibility(View.INVISIBLE);
		
        final Fragment mFragment = new FindMatchFragment();
		final FragmentManager fManager = getFragmentManager();

        fManager
		.beginTransaction()
		.replace(R.id.findMatchContainer,
				mFragment).commit();
        if (showLoad) {
	        new Handler().postDelayed(new Runnable() {
				
				public void run() {
			        fManager.beginTransaction().remove(mFragment).commit();
			        titleTextView.setText(R.string.matchesTextView);
			        matchesList.setVisibility(View.VISIBLE);
				}
			}, FIND_MATCH_TIMEOUT);
        } else {
	        new Handler().postDelayed(new Runnable() {
				
				public void run() {
			        fManager.beginTransaction().remove(mFragment).commit();
			        titleTextView.setText(R.string.matchesTextView);
			        matchesList.setVisibility(View.VISIBLE);
				}
			}, 0);
        }
        Editor editor = sharedPrefs.edit();
        editor.putBoolean("showLoad", false);
        editor.commit();
        
        //Set itemclicklistener to listview
        matchesList.setOnItemClickListener(new OnItemClickListener() {
        	
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(context, ChatActivity.class);
				String name = (matchesList.getItemAtPosition(position)).toString();
				intent.putExtra("matchName", name);
				startActivity(intent);
			}
        });


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			openSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openSettings() {
		Intent intent = new Intent(this, UserPreferences.class);
		startActivity(intent);
	}
	
}
