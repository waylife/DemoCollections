package com.zanlabs.viewdebughelper;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by rxread on 2015/1/21.
 */
public class ActivityHelper {

	/**
	 * 慎重使用此函数<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (!tasks.isEmpty()) {
				ComponentName topActivity = tasks.get(0).topActivity;
				if (!topActivity.getPackageName().equals(context.getPackageName())) {
					return true;
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否在前台运行
	 * 慎重使用此函数<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 * @return
	 */
	public static  boolean isAppOnForeground(Context context) {
		return !isApplicationBroughtToBackground(context);
	}

	/**
	 * 慎重使用此函数<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 */
	public static String currentAppPackageNameOnForeground(final Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (!tasks.isEmpty()) {
				ComponentName topActivity = tasks.get(0).topActivity;
				return topActivity.getPackageName();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}


	/**
	 * 慎重使用此函数<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 */
	public static String getTopActivity(final Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (!tasks.isEmpty()) {
				ComponentName topActivity = tasks.get(0).topActivity;
				return topActivity.getClassName();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}

	public static ActivityInfo tryGetActivity(Context context,ComponentName componentName) {
		try {
			return context.getPackageManager().getActivityInfo(componentName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			return null;
		}
	}
}
