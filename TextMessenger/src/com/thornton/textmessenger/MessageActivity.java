package com.thornton.textmessenger;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thornton.textmessenger.conversation.Contact;
import com.thornton.textmessenger.conversation.ContactSelectorFragment;
import com.thornton.textmessenger.conversation.ConversationFragment;
import com.thornton.textmessenger.conversation.ConversationObserver;
import com.thornton.textmessenger.conversation.ConversationsFragment;

public class MessageActivity extends FragmentActivity implements ConversationObserver {

	private SlidingPaneLayout drawer;
	private ActionBar mActionBar;
	private ConversationFragment conversation;
	private ConversationsFragment conversations;
	private ContactSelectorFragment contacts;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initFragments();

		getSupportFragmentManager().beginTransaction()
		.replace(R.id.content_frame, contacts).commit();

		setUpActionBar();
	}

	private void initFragments(){
		contacts = new ContactSelectorFragment();
		conversations = new ConversationsFragment();
		conversation = new ConversationFragment();
		contacts.setArguments(new Bundle());
		conversations.setArguments(new Bundle());
		conversation.setArguments(new Bundle());
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
		mActionBar = getActionBar();
		drawer = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);

		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
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
	public void contactClicked(final Contact contact) {
		//TODO: Implement Me
		drawer.closePane();
		//makeFragmentVisible(conversation);
		Toast.makeText(this, "Contact clicked: " + contact.getDisplayName(), 3000).show();

	}

	private void makeFragmentVisible(final Fragment fragment){
		getSupportFragmentManager().beginTransaction().
		replace(R.id.content_frame, fragment).addToBackStack(fragment.getClass().getSimpleName())
		.commit();
	}

}