package com.zanlabs.viewdebughelper.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;

public class AccessibilityServiceHelper {

	public static void goServiceSettings(Context context) {
		context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
	}

	// To check if service is enabled
	// String servicename = context.getPackageName() + "/" + xxx.class.getName();
	public static boolean isAccessibilitySettingsOn(Context context,String serviceName) {
		int accessibilityEnabled = 0;
		boolean accessibilityFound = false;
		try {
			accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
		} catch (SettingNotFoundException e) {
		}
		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
		if (accessibilityEnabled == 1) {
			String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (settingValue != null) {
				TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
				splitter.setString(settingValue);
				while (splitter.hasNext()) {
					String accessabilityService = splitter.next();
					if (accessabilityService.equalsIgnoreCase(serviceName)) {
						return true;
					}
				}
			}
		} else {
			// Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
		}

		return accessibilityFound;
	}
}
