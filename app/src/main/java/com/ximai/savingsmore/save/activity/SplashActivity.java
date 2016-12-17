package com.ximai.savingsmore.save.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.toolbox.PreferencesUtils;
import com.ximai.savingsmore.save.common.BaseActivity;
import com.ximai.savingsmore.save.common.BaseApplication;

/**
 * Created by caojian on 16/11/18.
 */
public class SplashActivity extends Activity{
    public static final String NOT_FIRST_OPEN="not_first_open";
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            if(!PreferencesUtils.getBoolean(SplashActivity.this,NOT_FIRST_OPEN))

            {
                Intent it = new Intent(SplashActivity.this, GuidePageActivity.class);
                startActivity(it);
            }
            else {
//                String account = PreferencesUtils.getString(BaseApplication.getInstance(), "account", null);
//                String pwd = PreferencesUtils.getString(BaseApplication.getInstance(), "pwd", null);
//                boolean isAutoLogin=false;
//
//                if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(pwd)){
//                    isAutoLogin=true;
//                }
                Intent it = new Intent(SplashActivity.this, NoLoginMainactivity.class);
               // it.putExtra("isAutoLogin",isAutoLogin);
                startActivity(it);
            }
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myHandler.sendEmptyMessageDelayed(0, 2000);

    }

    @Override
    public void onBackPressed() {

    }

}
