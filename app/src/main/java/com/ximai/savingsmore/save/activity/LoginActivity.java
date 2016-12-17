package com.ximai.savingsmore.save.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.net.MyAsyncHttpResponseHandler;
import com.ximai.savingsmore.library.net.RequestParamsPool;
import com.ximai.savingsmore.library.net.URLText;
import com.ximai.savingsmore.library.net.WebRequestHelper;
import com.ximai.savingsmore.library.toolbox.GsonUtils;
import com.ximai.savingsmore.library.toolbox.LogUtils;
import com.ximai.savingsmore.save.common.BaseActivity;
import com.ximai.savingsmore.save.common.BaseApplication;
import com.ximai.savingsmore.save.modle.LoginUser;
import com.ximai.savingsmore.save.modle.MyUserInfo;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;
import com.ximai.savingsmore.save.modle.UserInfo;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caojian on 16/11/16.
 */
//个人登录
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText name;
    private EditText password;
    private Button button;
    private TextView register, forgetPassword;
    //用户的类型  2个人 3商家
    private int type = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_login_activity);
        setLeftBackMenuVisibility(LoginActivity.this, "");
        Intent intent = getIntent();
        setCenterTitle("登录");
        styleRightTextMenuLayout.setVisibility(View.VISIBLE);
        rightTextMenuTextView.setText(" 商家入口");
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        if (null != getIntent()) {
            name.setText(intent.getStringExtra("phone"));
        }
        button = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        register.setOnClickListener(this);
        styleRightTextMenuLayout.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!TextUtils.isEmpty(name.getText().toString()) && null != name.getText() && !TextUtils.isEmpty(password.getText().toString()) && null != password.getText()) {
                    login(name.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, OneStepRegisterActivity.class);
                intent.putExtra("register_type", type + "");
                startActivity(intent);
                break;
            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class).putExtra("title", "找回密码"));
                break;
            case R.id.style_right_text_menu:
                if (type == 3) {
                    rightTextMenuTextView.setText("商家入口");
                    type = 2;
                } else {
                    rightTextMenuTextView.setText("个人入口");
                    type = 3;
                }
                break;

        }
    }

    private void login(String name, String password) {
        WebRequestHelper.post(URLText.LOGIN_URL, RequestParamsPool.getLoginParams(name, password, "", type), new MyAsyncHttpResponseHandler(LoginActivity.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    String message = object.optString("Message");
                    Boolean isLogin = object.optBoolean("IsSuccess");
                    if (isLogin) {
                        String MainData = object.optString("MainData");
                        UserInfo userInfo = GsonUtils.fromJson(MainData, UserInfo.class);
                        LoginUser.getInstance().userInfo = userInfo;
                        if (null != userInfo) {
                            BaseApplication.getInstance().Token = userInfo.TokenType + " " + userInfo.AccessToken;
                        }
                        LoginUser.getInstance().setIsLogin(true);
                        getUsereInfo();

                    }
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //得到用户的信息
    private void getUsereInfo() {
        WebRequestHelper.json_post(LoginActivity.this, URLText.GET_USERINFO, RequestParamsPool.getUserInfo(), new MyAsyncHttpResponseHandler(LoginActivity.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String MianData = object.optString("MainData");
                MyUserInfoUtils.getInstance().myUserInfo = GsonUtils.fromJson(MianData, MyUserInfo.class);
                requestLoginEaseChat(MyUserInfoUtils.getInstance().myUserInfo.IMUserName, MyUserInfoUtils.getInstance().myUserInfo.IMPassword);
            }
        });

    }

    public void requestLoginEaseChat(final String accountStr, final String pwd) {
        EMChatManager.getInstance().login(accountStr, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                // EMGroupManager.getInstance().loadAllGroups();//加载群组 木有此功能
                EMChatManager.getInstance().loadAllConversations();

                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                        BaseApplication.currentUserNick.trim());
                if (!updatenick) {
                    LogUtils.instance.d("update current user nick fail");
                }
                //异步获取当前用户的昵称和头像(从自己服务器获取...)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });

    }
}
