package com.ximai.savingsmore.save.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.view.Form_item;
import com.ximai.savingsmore.save.common.BaseActivity;

/**
 * Created by caojian on 16/12/15.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Form_item number;
    private TextView xiugai_password, about_we, falu, toushu, login_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        setCenterTitle("设置");
        setLeftBackMenuVisibility(SettingActivity.this, "");
        number = (Form_item) findViewById(R.id.number);
        xiugai_password = (TextView) findViewById(R.id.xiugai_password);
        about_we = (TextView) findViewById(R.id.about_we);
        falu = (TextView) findViewById(R.id.falu);
        toushu = (TextView) findViewById(R.id.toushu);
        xiugai_password.setOnClickListener(this);
        about_we.setOnClickListener(this);
        falu.setOnClickListener(this);
        toushu.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xiugai_password:
                Intent intent = new Intent(SettingActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("title", "修改密码");
                startActivity(intent);
                break;
            case R.id.about_we:
                Intent intent1 = new Intent(SettingActivity.this, AboutWeActivity.class);
                startActivity(intent1);
                break;
            case R.id.falu:
                Intent intent2 = new Intent(SettingActivity.this, LowStateActivity.class);
                startActivity(intent2);
                break;
            case R.id.toushu:
                Intent intent3 = new Intent(SettingActivity.this, ComplainActivity.class);
                startActivity(intent3);

        }
    }
}
