package com.zanlabs.viewdebughelper;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.zanlabs.viewdebughelper.service.ViewDebugHelperService;
import com.zanlabs.viewdebughelper.util.AccessibilityServiceHelper;

public class MyWindowManager {

	private static FloatWindowView mFloatWindow;


	/**
	 * 小悬浮窗View的参数
	 */
	private static LayoutParams mFloatWindowParams;


	/**
	 * 用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager mWindowManager;

	/**
	 * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createFloatWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (mFloatWindow == null) {
			mFloatWindow = new FloatWindowView(context);
			if (mFloatWindowParams == null) {
				mFloatWindowParams = new LayoutParams();
				mFloatWindowParams.type = LayoutParams.TYPE_PHONE;
				mFloatWindowParams.format = PixelFormat.RGBA_8888;
				mFloatWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE |LayoutParams.FLAG_KEEP_SCREEN_ON |LayoutParams.FLAG_FULLSCREEN;
				mFloatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				mFloatWindowParams.width = FloatWindowView.viewWidth;
				mFloatWindowParams.height = FloatWindowView.viewHeight;
				mFloatWindowParams.x = screenWidth;
				mFloatWindowParams.y = screenHeight / 2;
			}
			mFloatWindow.setParams(mFloatWindowParams);
			windowManager.addView(mFloatWindow, mFloatWindowParams);
		}
	}

	/**
	 * 将小悬浮窗从屏幕上移除。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeFloatWindow(Context context) {
		if (mFloatWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mFloatWindow);
			mFloatWindow = null;
		}
	}


	public static void updateCurrentTopActvity(Context context) {
		if (mFloatWindow != null) {
			TextView percentView = (TextView) mFloatWindow.findViewById(R.id.float_textview);
			if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
				if(AccessibilityServiceHelper.isAccessibilitySettingsOn(context,ViewDebugHelperApplication.getInstance().getCurrentPackName()+"/"+ ViewDebugHelperService.class.getName())){
					percentView.setText(ViewDebugHelperApplication.getInstance().getLastTopActivityName());
				}else{
					percentView.setText("服务未开，数据不一定准确\n"+ActivityHelper.getTopActivity(context));
				}
			}else{
				percentView.setText(ActivityHelper.getTopActivity(context));
			}
		}
	}

	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
	 */
	public static boolean isWindowShowing() {
		return mFloatWindow != null;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}


}
