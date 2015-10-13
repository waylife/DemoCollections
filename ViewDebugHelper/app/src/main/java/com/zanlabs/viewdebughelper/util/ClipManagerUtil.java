package com.zanlabs.viewdebughelper.util;

import android.content.Context;
import android.text.ClipboardManager;

/**
 * 剪切板类<br/>
 * 
 * @author rxread
 */
public class ClipManagerUtil {
	/**
	 * copy plain text to clip board<br/>
	 * 复制文本到剪切板<br/>
	 */
	public static void copyToClipBoard(Context context, CharSequence text) {
		ClipboardManager clipboardManager = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.setText(text);
	}
}
