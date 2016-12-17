package com.ximai.savingsmore.save.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.save.common.BaseActivity;

/**
 * Created by caojian on 16/11/17.
 */
public class OneStepRegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText number;
    private TextView nextStep;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_register_activity);
        setLeftBackMenuVisibility(OneStepRegisterActivity.this,"");
        setCenterTitle("注册");
        number= (EditText) findViewById(R.id.input_number);
        nextStep= (TextView) findViewById(R.id.next_step);
        nextStep.setOnClickListener(this);
        Intent intent=getIntent();
        if(null!=intent) {
            type = getIntent().getStringExtra("register_type");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.next_step){
            Intent intent=new Intent(OneStepRegisterActivity.this,TwoStepRegisterActivity.class);
            if(!TextUtils.isEmpty(number.getText())&&number.getText().toString().length()==11){
                intent.putExtra("number",number.getText().toString());
                intent.putExtra("type",type);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(OneStepRegisterActivity.this,"请输入11的手机号",Toast.LENGTH_LONG).show();
            }
        }
    }
}
