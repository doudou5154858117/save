package com.ximai.savingsmore.save.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.cache.MyImageLoader;
import com.ximai.savingsmore.library.net.URLText;
import com.ximai.savingsmore.library.view.RoundImageView;
import com.ximai.savingsmore.save.activity.CollectCenterActivity;
import com.ximai.savingsmore.save.activity.HotSalesGoods;
import com.ximai.savingsmore.save.activity.PersonalMyMessageActivity;
import com.ximai.savingsmore.save.activity.SearchActivity;
import com.ximai.savingsmore.save.activity.SettingActivity;
import com.ximai.savingsmore.save.modle.LoginUser;
import com.ximai.savingsmore.save.modle.MyUserInfo;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;

/**
 * Created by caojian on 16/11/21.
 */
//个人侧栏
public class PersonFragment extends Fragment implements View.OnClickListener {
    private RoundImageView head;
    private RelativeLayout hot_sales;
    private TextView name;
    private ImageView setting;
    private RelativeLayout search, collect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_side_fragment, null);
        head = (RoundImageView) view.findViewById(R.id.user_head);
        name = (TextView) view.findViewById(R.id.name);
        search = (RelativeLayout) view.findViewById(R.id.search);
        collect = (RelativeLayout) view.findViewById(R.id.collect);
        collect.setOnClickListener(this);
        search.setOnClickListener(this);
        hot_sales = (RelativeLayout) view.findViewById(R.id.hot_sales);
        setting= (ImageView) view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        hot_sales.setOnClickListener(this);
        head.setOnClickListener(this);
        if (null != MyUserInfoUtils.getInstance().myUserInfo) {
            MyImageLoader.displayDefaultImage(URLText.img_url + MyUserInfoUtils.getInstance().myUserInfo.PhotoPath, head);
            name.setText(MyUserInfoUtils.getInstance().myUserInfo.ShowName);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != name && null != head) {
            MyImageLoader.displayDefaultImage(URLText.img_url + MyUserInfoUtils.getInstance().myUserInfo.PhotoPath, head);
            name.setText(MyUserInfoUtils.getInstance().myUserInfo.ShowName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                Intent intent0 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent0);
                break;
            case R.id.hot_sales:
                Intent intent = new Intent(getActivity(), HotSalesGoods.class);
                intent.putExtra("title", "最热门促销");
                startActivity(intent);
                break;
            case R.id.search:
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                intent1.putExtra("title", "促销商品搜索");
                startActivity(intent1);
                break;
            case R.id.collect:
                Intent intent2 = new Intent(getActivity(), CollectCenterActivity.class);
                startActivity(intent2);
                break;
            case R.id.user_head:
                Intent intent3 = new Intent(getActivity(), PersonalMyMessageActivity.class);
                startActivity(intent3);
                break;

        }
    }
}
