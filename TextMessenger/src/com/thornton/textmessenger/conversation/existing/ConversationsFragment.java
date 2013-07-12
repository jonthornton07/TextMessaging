package com.thornton.textmessenger.conversation.existing;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thornton.textmessenger.Logger;
import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.bo.Conversation;
import com.thornton.textmessenger.interfaces.ConversationObservable;
import com.thornton.textmessenger.interfaces.ConversationObserver;

public class ConversationsFragment extends Fragment implements ConversationObservable, LoaderCallbacks<List<Conversation>>{

	private ConversationObserver observer;

	private CurrentConversationsLoader loader;

	private ConversationsAdapter adapter;

	private ListView list;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.conversations_fragment, null);
		list = (ListView) view.findViewById(R.id.conversations_list);

		return view;
	}

	@Override
	public void notifyObserver(final Conversation conversation) {
		if(null != observer){
			observer.conversationStarted(conversation);
		} else{
			Logger.log("No observer registered");
		}
	}

	@Override
	public void setObserver(final ConversationObserver observer) {
		this.observer = observer;
	}


	@Override
	public void onLoadFinished(final Loader<List<Conversation>> arg0, final List<Conversation> arg1) {
		if(null == adapter){
			adapter = new ConversationsAdapter(getActivity(), R.id.display_name, arg1, this);
		}else{
			adapter.setData(arg1);
		}
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(final Loader<List<Conversation>> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Loader<List<Conversation>> onCreateLoader(final int arg0, final Bundle arg1) {
		loader = new CurrentConversationsLoader(getActivity());
		return loader;
	}
}
