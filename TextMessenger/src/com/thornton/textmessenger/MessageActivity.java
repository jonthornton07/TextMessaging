package com.thornton.textmessenger;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.view.MenuItem;
import android.view.View;

import com.thornton.textmessenger.conversation.ContactSelectorFragment;

public class MessageActivity extends FragmentActivity {

	private SlidingPaneLayout mSlidingLayout;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		final ContactSelectorFragment fragment = new ContactSelectorFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		setUpActionBar();
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
		if (mSlidingLayout.isOpen()) {
			mSlidingLayout.closePane();
		} else {
			mSlidingLayout.openPane();
		}
	}

	@SuppressLint("NewApi")
	private void setUpActionBar() {
		mActionBar = getActionBar();
		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);

		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mSlidingLayout.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelOpened(final View panel) {
				if (mSlidingLayout.isSlideable()) {
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

}