package com.ximai.savingsmore.save.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.cache.MyImageLoader;
import com.ximai.savingsmore.library.net.MyAsyncHttpResponseHandler;
import com.ximai.savingsmore.library.net.RequestParamsPool;
import com.ximai.savingsmore.library.net.URLText;
import com.ximai.savingsmore.library.net.WebRequestHelper;
import com.ximai.savingsmore.library.toolbox.GsonUtils;
import com.ximai.savingsmore.library.view.RoundImageView;
import com.ximai.savingsmore.save.activity.BusinessMyCenterActivity;
import com.ximai.savingsmore.save.activity.FourStepRegisterActivity;
import com.ximai.savingsmore.save.activity.HotSalesGoods;
import com.ximai.savingsmore.save.activity.IssuGoodActivity;
import com.ximai.savingsmore.save.activity.MessageCenterActivity;
import com.ximai.savingsmore.save.activity.MyCommentCenterActivity;
import com.ximai.savingsmore.save.activity.SearchActivity;
import com.ximai.savingsmore.save.activity.SettingActivity;
import com.ximai.savingsmore.save.modle.IMUser;
import com.ximai.savingsmore.save.modle.IMUserList;
import com.ximai.savingsmore.save.modle.LoginUser;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by caojian on 16/11/25.
 */
public class BusinessFragment extends Fragment implements View.OnClickListener {
    private RoundImageView head;
    private TextView name;
    private RelativeLayout hot_sales;
    private RelativeLayout search;
    private RelativeLayout myCenter;
    private RelativeLayout fabu;
    private RelativeLayout comment_center;
    private ImageView setting;
    private RelativeLayout message_center;
    //private List<IMUser> imUsers=new ArrayList<IMUser>();
    private String result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_side_fragment, null);
        head = (RoundImageView) view.findViewById(R.id.user_head);
        name = (TextView) view.findViewById(R.id.name);
        hot_sales = (RelativeLayout) view.findViewById(R.id.hot_sales);
        hot_sales.setOnClickListener(this);
        search = (RelativeLayout) view.findViewById(R.id.search);
        search.setOnClickListener(this);
        MyImageLoader.displayDefaultImage(URLText.img_url + MyUserInfoUtils.getInstance().myUserInfo.PhotoPath, head);
        name.setText(MyUserInfoUtils.getInstance().myUserInfo.ShowName);
        myCenter = (RelativeLayout) view.findViewById(R.id.my_center);
        comment_center = (RelativeLayout) view.findViewById(R.id.comment_center);
        setting = (ImageView) view.findViewById(R.id.setting);
        message_center= (RelativeLayout) view.findViewById(R.id.message_center);
        message_center.setOnClickListener(this);
        setting.setOnClickListener(this);
        head.setOnClickListener(this);
        comment_center.setOnClickListener(this);
        myCenter.setOnClickListener(this);
        fabu = (RelativeLayout) view.findViewById(R.id.fabu_cuxiao);
        fabu.setOnClickListener(this);
        loadConversationList();
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
                intent.putExtra("title", "热门促销");
                startActivity(intent);
                break;
            case R.id.search:
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                intent1.putExtra("title", "谁在促销");
                startActivity(intent1);
                break;
            case R.id.my_center:
                Intent intent2 = new Intent(getActivity(), BusinessMyCenterActivity.class);
                intent2.putExtra("title", "我的中心");
                startActivity(intent2);
                break;
            case R.id.fabu_cuxiao:
                Intent intent3 = new Intent(getActivity(), IssuGoodActivity.class);
                intent3.putExtra("title", "促销品发布");
                startActivity(intent3);
                break;
            case R.id.comment_center:
                Intent intent4 = new Intent(getActivity(), MyCommentCenterActivity.class);
                intent4.putExtra("title", "评论中心");
                startActivity(intent4);
                break;
            case R.id.user_head:
                Intent intent5 = new Intent(getActivity(), BusinessMyCenterActivity.class);
                intent5.putExtra("title", "我的中心");
                startActivity(intent5);
                break;
            case R.id.message_center:
                Intent intent6 = new Intent(getActivity(), MessageCenterActivity.class);
                if(null!=result&&!TextUtils.isEmpty(result)) {
                    intent6.putExtra("list", result);
                    startActivity(intent6);
                }
                break;
        }
    }
    private void getUserByIM(String userName) {
        WebRequestHelper.json_post(getActivity(), URLText.USERBYIM, RequestParamsPool.getUserByIM(userName), new MyAsyncHttpResponseHandler(getActivity()) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                result = new String(responseBody);
//                IMUserList imUserList = GsonUtils.fromJson(result, IMUserList.class);
//                if (imUserList.IsSuccess.equals("true") && imUserList.MainData.size() > 0) {
//                    imUsers.add(imUserList.MainData.get(0));
//                }
            }
        });
    }

    protected void loadConversationList() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();

        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
         * 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变
         * 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    //if(conversation.getType() != EMConversationType.ChatRoom){
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    getUserByIM(conversation.getUserName());
                    //}
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
           // sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
