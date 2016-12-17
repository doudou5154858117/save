package com.ximai.savingsmore.save.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.save.activity.TakeMeActivity;
import com.ximai.savingsmore.save.modle.BusinessMessage;
import com.ximai.savingsmore.save.modle.GoodDetial;
import com.ximai.savingsmore.save.modle.User;

/**
 * Created by caojian on 16/11/26.
 */
//商家介绍
public class BusinessIntroduceFragment extends Fragment implements View.OnClickListener{
    private TextView phone,url,location,date,type,range;
    //private User user;
    //private GoodDetial good;
    private BusinessMessage businessMessage;
    private TextView flow_me;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.business_introduce,null);
        phone= (TextView) view.findViewById(R.id.phone);
        url= (TextView) view.findViewById(R.id.url);
        location= (TextView) view.findViewById(R.id.adress);
        date= (TextView) view.findViewById(R.id.company_date);
        type= (TextView) view.findViewById(R.id.Goods_type);
        range= (TextView) view.findViewById(R.id.range);
        businessMessage= (BusinessMessage) getArguments().getSerializable("good");
        flow_me= (TextView) view.findViewById(R.id.flow_me);
        flow_me.setOnClickListener(this);
        if(null!=businessMessage){
            phone.setText(businessMessage.PhoneNumber);
            url.setText(businessMessage.UserExtInfo.WebSite);
            location.setText(businessMessage.Domicile);
            date.setText(businessMessage.ApprovalDateName);
            if(businessMessage.UserExtInfo.IsBag){
                type.setText("提袋商品");
            }
            else {
                type.setText("非提袋商品");
            }
            if(null!=businessMessage.BusinessScopes.get(0).DictNode.Name) {
                range.setText(businessMessage.BusinessScopes.get(0).DictNode.Name);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.flow_me){
            Intent intent1=new Intent(getActivity(),TakeMeActivity.class);
            startActivity(intent1);
        }
    }
}
