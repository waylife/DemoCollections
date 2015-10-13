package com.zanlabs.viewdebughelper.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.zanlabs.viewdebughelper.MyWindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowService extends Service {

    public static void start(Context context){
        Intent intent=new Intent(context,FloatWindowService.class);
        context.startService(intent);
    }

    public static void stop(Context context){
        Intent intent=new Intent(context,FloatWindowService.class);
        context.stopService(intent);
    }

    private static boolean isRunning=false;

    public static boolean isRunning(){
        return isRunning;
    }
    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;

    public FloatWindowService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning=true;
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        MyWindowManager.removeFloatWindow(getApplicationContext());
        isRunning=false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not support");
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            if(!isRunning)
                return;
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            if (!MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!isRunning)
                            return;
                        MyWindowManager.createFloatWindow(getApplicationContext());
                    }
                });
            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateCurrentTopActvity(getApplicationContext());
                    }
                });
            }
        }

    }
}
