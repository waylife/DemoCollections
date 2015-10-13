package com.zanlabs.viewdebughelper;

import android.app.Application;

public class ViewDebugHelperApplication extends Application {

	private static ViewDebugHelperApplication mInstance = null;

	private  String mLastTopActivityName = "";

    private String mCurrentPackName="";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance=this;
        mCurrentPackName=getPackageName();
	}

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static ViewDebugHelperApplication getInstance() {
		return mInstance;
	}


    public String getLastTopActivityName() {
        return mLastTopActivityName;
    }

    public void setLastTopActivityName(String lastTopActivityName) {
        this.mLastTopActivityName = lastTopActivityName;
    }

    public String getCurrentPackName() {
        return mCurrentPackName;
    }
}
