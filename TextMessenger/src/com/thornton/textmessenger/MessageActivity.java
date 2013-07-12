package com.thornton.textmessenger;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.thornton.textmessenger.conversation.ConversationFragment;
import com.thornton.textmessenger.conversation.existing.ConversationsFragment;
import com.thornton.textmessenger.conversation.select.ContactSelectorFragment;
import com.thornton.textmessenger.database.StorageHelper;
import com.thornton.textmessenger.database.bo.Conversation;
import com.thornton.textmessenger.database.bo.Message;
import com.thornton.textmessenger.interfaces.ConversationObserver;
import com.thornton.textmessenger.interfaces.MessageObserver;
import com.thornton.textmessenger.receiver.BaseReceiver;
import com.thornton.textmessenger.receiver.DeliveryReceiver;
import com.thornton.textmessenger.receiver.IntentReceiver;
import com.thornton.textmessenger.receiver.SendReceiver;


public class MessageActivity extends FragmentActivity implements ConversationObserver, MessageObserver {


	private PendingIntent sendPendingIntent, deliveredPendingIntent;

	private IntentFilter filter;

	private BaseReceiver intentReceiver, sentReceiver, deliveredReceiver;

	private SlidingPaneLayout drawer;
	private ActionBar actionBar;
	private ConversationFragment conversationFragment;
	private ConversationsFragment conversationsFragment;
	private ContactSelectorFragment newConversationFragment;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initFragments();

		getSupportFragmentManager().beginTransaction()
		.replace(R.id.content_frame, newConversationFragment).commit();

		getSupportFragmentManager().beginTransaction()
		.replace(R.id.list_pane, conversationsFragment).commit();

		setUpActionBar();

		sendPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(BaseReceiver.SENT), 0);
		deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(BaseReceiver.DELIVERED), 0);

		filter = new IntentFilter();
		filter.addAction(BaseReceiver.SMS_RECEIVED_ACTION);

		intentReceiver = new IntentReceiver(this);
		registerReceiver(intentReceiver, filter);
	}

	@Override
	public void onResume(){
		super.onResume();

		sentReceiver = new SendReceiver();
		deliveredReceiver = new DeliveryReceiver();

		registerReceiver(sentReceiver, new IntentFilter(BaseReceiver.SENT));
		registerReceiver(deliveredReceiver, new IntentFilter(BaseReceiver.DELIVERED));
	}

	@Override
	public void onPause(){
		super.onPause();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();

		unregisterReceiver(sentReceiver);
		unregisterReceiver(deliveredReceiver);
		unregisterReceiver(intentReceiver);

		newConversationFragment.setObserver(null);
		conversationsFragment.setObserver(null);
		conversationFragment.setMessageObserver(null);
	}

	private void initFragments(){
		newConversationFragment = new ContactSelectorFragment();
		conversationsFragment = new ConversationsFragment();
		conversationFragment = new ConversationFragment();
		newConversationFragment.setArguments(new Bundle());
		conversationsFragment.setArguments(new Bundle());
		conversationFragment.setArguments(new Bundle());
		newConversationFragment.setObserver(this);
		conversationsFragment.setObserver(this);
		conversationFragment.setMessageObserver(this);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		/*
		 * The action bar up action should open the slider if it is currently
		 * closed, as the left pane contains content one level up in the
		 * navigation hierarchy.
		 */
		if (item.getItemId() == android.R.id.home) {
			toggleDrawer();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Toggle the sliding drawer. If the drawer is open, close it or vice versa
	 */
	protected void toggleDrawer() {
		if (drawer.isOpen()) {
			drawer.closePane();
		} else {
			drawer.openPane();
		}
	}

	@SuppressLint("NewApi")
	private void setUpActionBar() {
		actionBar = getActionBar();
		drawer = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);

		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		drawer.openPane();
		drawer.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelOpened(final View panel) {
				if (drawer.isSlideable()) {
					getSupportFragmentManager().findFragmentById(
							R.id.content_frame).setHasOptionsMenu(false);
					getSupportFragmentManager()
					.findFragmentById(R.id.list_pane)
					.setHasOptionsMenu(true);
				} else {
					getSupportFragmentManager().findFragmentById(
							R.id.content_frame).setHasOptionsMenu(true);
					getSupportFragmentManager()
					.findFragmentById(R.id.list_pane)
					.setHasOptionsMenu(false);
				}
			}

			@Override
			public void onPanelClosed(final View panel) {
				getSupportFragmentManager()
				.findFragmentById(R.id.content_frame)
				.setHasOptionsMenu(true);
				getSupportFragmentManager().findFragmentById(R.id.list_pane)
				.setHasOptionsMenu(false);
			}

			@Override
			public void onPanelSlide(final View view, final float v) {
			}

		});
	}

	@Override
	public void conversationStarted(final Conversation conversation) {
		drawer.closePane();
		conversationFragment.setConversation(conversation);
		makeFragmentVisible(conversationFragment);
		actionBar.setTitle(conversation.getContact().getDisplayName());
	}

	private void makeFragmentVisible(final Fragment fragment){
		getSupportFragmentManager().beginTransaction().
		replace(R.id.content_frame, fragment).addToBackStack(fragment.getClass().getSimpleName())
		.commit();
	}

	@Override
	public void sendMessage(final Message message){
		final SmsManager manager = SmsManager.getDefault();

		if(TextUtils.isEmpty(message.getText())){return;}
		manager.sendTextMessage(message.getPhoneNumber(), null, message.getText().toString(), sendPendingIntent, deliveredPendingIntent);
		storeMessage(message);
	}

	@Override
	public void storeMessage(final Message message){
		StorageHelper.insertMessage(this, message);
	}

	@Override
	public void receiveNewMessage(final Message message) {
		if(null != conversationFragment) {conversationFragment.receiveNewMessage(message);}
	}

}