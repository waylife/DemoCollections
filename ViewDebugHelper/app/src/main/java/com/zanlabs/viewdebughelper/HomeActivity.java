package com.zanlabs.viewdebughelper;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zanlabs.viewdebughelper.service.FloatWindowService;
import com.zanlabs.viewdebughelper.util.AccessibilityServiceHelper;

public class HomeActivity extends AppCompatActivity {

    Button mActionBtn;
    Button mActiveServiceBtn;
    boolean mIsServiceRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActionBtn= (Button) findViewById(R.id.home_action_btn);
        mActiveServiceBtn= (Button) findViewById(R.id.home_activte_service_btn);
        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setServiceState();
                if(mIsServiceRunning){
                    FloatWindowService.stop(HomeActivity.this);
                }else{
                    FloatWindowService.start(HomeActivity.this);
                }
                Toast.makeText(HomeActivity.this,mIsServiceRunning?"服务已停止":"服务已启动",Toast.LENGTH_SHORT).show();
            }
        });

        mActiveServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessibilityServiceHelper.goServiceSettings(HomeActivity.this);
            }
        });
        checkApiGreaterThanLollipop();
    }

    private void setServiceState(){
        mIsServiceRunning=FloatWindowService.isRunning();
        if(mActionBtn!=null){
            mActionBtn.setText(mIsServiceRunning?"停止服务":"启动服务");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setServiceState();
    }

    public void checkApiGreaterThanLollipop(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            mActiveServiceBtn.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Android 5.0(LOLLIPOP)之后需要开启ViewDebugHelper服务才能获取当前activity信息",Toast.LENGTH_SHORT).show();
        }else{
            mActiveServiceBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
