package com.thornton.textmessenger;

import android.util.Log;

public class Logger {

	public static final String APP_TAG = "MessageActivity";

	public static void log(final String string){
		Log.d(APP_TAG, string);
	}
}
