package com.example.taxishare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

public class ChatActivity extends Activity {

	TextView matchNameTextView;
	private Pubnub pubnub;
	Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		matchNameTextView = (TextView) findViewById(R.id.matchNameTextView);
		final EditText mEditText = (EditText) findViewById(R.id.chatEditText);
		mButton = (Button) findViewById(R.id.chatSendButton);
		
		Bundle extras = getIntent().getExtras();
		String name = extras.getString("matchName", "No name");
		matchNameTextView.setText("Talking with " + name);
		
		mButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String message = mEditText.getText().toString();
				sendMessage(message);
				mEditText.setText("");
			}
		});
		this.setUpPubnub();
	}

	private void setUpPubnub() {
		pubnub = new Pubnub("pub-c-9ad0ea11-74c5-4919-84fe-3f01fce40e29", 
				"sub-c-63fd307e-364c-11e4-9f47-02ee2ddab7fe");
		
		//Test connection
		Callback callback = new Callback() {
			public void successCallback(String channel, Object response) {
				System.out.println(response.toString());
			}
			public void errorCallback(String channel, PubnubError error) {
			   System.out.println(error.toString());
			 }
		};
		pubnub.time(callback);
		
		// Subscribe to a channel
		try {
			pubnub.subscribe("hophacks_taxi_share", new Callback() {
		 
				@Override
				public void connectCallback(String channel, Object message) {
					System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
							+ " : " + message.getClass() + " : "
		                    + message.toString());
				}
		 
				@Override
				public void disconnectCallback(String channel, Object message) {
					System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
		                      + " : " + message.getClass() + " : "
		                      + message.toString());
				}
		 
				public void reconnectCallback(String channel, Object message) {
		           System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
		                      + " : " + message.getClass() + " : "
		                      + message.toString());
		       }
		 
		       @Override
		       public void successCallback(String channel, Object message) {
		           System.out.println("SUBSCRIBE : " + channel + " : "
		                      + message.getClass() + " : " + message.toString());
		       }
		 
		       @Override
		       public void errorCallback(String channel, PubnubError error) {
		           System.out.println("SUBSCRIBE : ERROR on channel " + channel
		                      + " : " + error.toString());
		       }
		     }
		   );
		 } catch (PubnubException e) {
			 System.out.println(e.toString());
		 }

		pubnub.publish("hophacks_taxi_share", "Hello", callback);
	}
	
	private void sendMessage(String message) {
		Callback callback = new Callback() {
			public void successCallback(String channel, Object response) {
			     Log.d("PUBNUB",response.toString());
			}
			public void errorCallback(String channel, PubnubError error) {
			   Log.d("PUBNUB",error.toString());
			}
		};
		pubnub.publish("hophacks_taxi_share", message , callback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
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
		} else if (id == R.id.action_add_person) {
			addPerson();
			return true;
		} else if (id == R.id.action_call_cab) {
			callCab();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		pubnub.unsubscribe("hophacks_taxi_share");
	}
	
	private void callCab() {
		//do stuff
	}

	private void addPerson() {
		//do stuff
	}

	public void openSettings() {
		Intent intent = new Intent(this, UserPreferences.class);
		startActivity(intent);
	}
	
}
