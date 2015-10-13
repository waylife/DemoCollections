package com.zanlabs.viewdebughelper.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.zanlabs.viewdebughelper.ActivityHelper;
import com.zanlabs.viewdebughelper.ViewDebugHelperApplication;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ViewDebugHelperService extends AccessibilityService {

    public static void log(String message) {
        Log.i("ViewDebugHelperService", message);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo accessibilityServiceInfo = getServiceInfo();
        if (accessibilityServiceInfo == null)
            accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        accessibilityServiceInfo.notificationTimeout = 10;
        if (Build.VERSION.SDK_INT >= 16) {
            accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        }
        setServiceInfo(accessibilityServiceInfo);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            ComponentName componentName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());
            ActivityInfo activityInfo = ActivityHelper.tryGetActivity(this, componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity) {
                log("CurrentActivity" + componentName.flattenToString());
                ViewDebugHelperApplication.getInstance().setLastTopActivityName(componentName.flattenToString());
            }
        }
    }


    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
