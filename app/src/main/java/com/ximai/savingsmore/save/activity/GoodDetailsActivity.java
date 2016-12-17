package com.ximai.savingsmore.save.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.cache.MyImageLoader;
import com.ximai.savingsmore.library.net.MyAsyncHttpResponseHandler;
import com.ximai.savingsmore.library.net.RequestParamsPool;
import com.ximai.savingsmore.library.net.URLText;
import com.ximai.savingsmore.library.net.WebRequestHelper;
import com.ximai.savingsmore.library.toolbox.GsonUtils;
import com.ximai.savingsmore.library.toolbox.LogUtils;
import com.ximai.savingsmore.library.view.CircleFlowIndicator;
import com.ximai.savingsmore.library.view.FlowView;
import com.ximai.savingsmore.save.common.BaseActivity;
import com.ximai.savingsmore.save.modle.GoodDetial;
import com.ximai.savingsmore.save.modle.Images;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caojian on 16/11/22.
 */
// 商品详情页
public class GoodDetailsActivity extends Activity implements View.OnClickListener {
    private String id;
    private ScrollView scrollView;
    private LinearLayout business_message;
    GoodDetial goodDetial;
    private FlowView mTopAdsView;
    private ImageView back, share, collect, big_imae, message, phone, comment1, comment2, comment3, comment4, comment5;
    private TextView name, price, high_price, dazhe_style, start_time, end_time, comment_number,
            store_name, location, distance, pingpai, danwei, style, reson, bizhong, explain, descript, flow_me, service, score;
    private Boolean isFavourite;
    private RelativeLayout comment;
    private LinearLayout comment_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_details_activity);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        collect = (ImageView) findViewById(R.id.shouchang);
        mTopAdsView = (FlowView) findViewById(R.id.big_image);
        message = (ImageView) findViewById(R.id.send_message);
        phone = (ImageView) findViewById(R.id.phone);
        phone.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        price = (TextView) findViewById(R.id.price);
        high_price = (TextView) findViewById(R.id.high_price);
        dazhe_style = (TextView) findViewById(R.id.dazhe_style);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        comment_number = (TextView) findViewById(R.id.comment_number);
        store_name = (TextView) findViewById(R.id.store_name);
        location = (TextView) findViewById(R.id.location);
        distance = (TextView) findViewById(R.id.distance);
        pingpai = (TextView) findViewById(R.id.pingpai);
        danwei = (TextView) findViewById(R.id.danwei);
        bizhong = (TextView) findViewById(R.id.bizhong);
        style = (TextView) findViewById(R.id.style);
        reson = (TextView) findViewById(R.id.resonse);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        explain = (TextView) findViewById(R.id.explain);
        descript = (TextView) findViewById(R.id.describe);
        flow_me = (TextView) findViewById(R.id.flow_me);
        service = (TextView) findViewById(R.id.servise);
        score = (TextView) findViewById(R.id.score);
        comment = (RelativeLayout) findViewById(R.id.comment);
        comment_score = (LinearLayout) findViewById(R.id.comment_score);
        comment_score.setOnClickListener(this);
        comment.setOnClickListener(this);
        business_message = (LinearLayout) findViewById(R.id.business_message);
        business_message.setOnClickListener(this);
        flow_me.setOnClickListener(this);
        collect.setOnClickListener(this);
        high_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        scrollView.setVerticalScrollBarEnabled(false);
        back.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        getgood_detial(id);
        startAutoRun();

    }

    private void getgood_detial(String Id) {
        WebRequestHelper.json_post(GoodDetailsActivity.this, URLText.GET_GOOD_DETIAL, RequestParamsPool.getGoodDetial(Id), new MyAsyncHttpResponseHandler(GoodDetailsActivity.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
                    Boolean isSuccess = object.optBoolean("IsSuccess");
                    String MainData = object.optString("MainData");
                    goodDetial = GsonUtils.fromJson(MainData, GoodDetial.class);
                    if (null != goodDetial) {
                        if (null != goodDetial.Name) {
                            name.setText(goodDetial.Name);
                        }
                        price.setText("￥" + goodDetial.Price);
                        high_price.setText("原价: " + goodDetial.OriginalPrice);
                        dazhe_style.setText(goodDetial.Preferential);
                        comment_number.setText(goodDetial.CommentCount + "人评论");
                        location.setText(goodDetial.Address);
                        pingpai.setText(goodDetial.Name);
                        danwei.setText(goodDetial.Unit.Name);
                        bizhong.setText(goodDetial.Currency.Name);
                        style.setText(goodDetial.PromotionTypeName);
                        reson.setText(goodDetial.PromotionCause);
                        explain.setText(goodDetial.Introduction);
                        descript.setText(goodDetial.Description);
                        start_time.setText(goodDetial.StartTimeName);
                        end_time.setText(goodDetial.EndTimeName);
                        store_name.setText(goodDetial.User.ShowName);
                        distance.setText(goodDetial.Distance);
                        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                                50, 50);
                        layout.setMargins(5, 0, 5, 0);
                        if(goodDetial.CommentCount.equals("0")){
                            score.setText(" 0 分");
                            for (int i = 0; i <5; i++) {
                                ImageView imageView = new ImageView(GoodDetailsActivity.this);
                                imageView.setLayoutParams(layout);
                                imageView.setBackgroundResource(R.mipmap.comment_start_gray);
                                comment_score.addView(imageView);
                            }
                        }
                        else {
                            score.setText(goodDetial.Score + "分");
                            if (goodDetial.Score.length() > 1) {
                                int score1 = Integer.parseInt(goodDetial.Score.substring(0, 1));
                                for (int i = 0; i < score1; i++) {
                                    ImageView imageView = new ImageView(GoodDetailsActivity.this);
                                    imageView.setLayoutParams(layout);
                                    imageView.setBackgroundResource(R.mipmap.comment_star);
                                    comment_score.addView(imageView);
                                }
                                ImageView imageView = new ImageView(GoodDetailsActivity.this);
                                imageView.setLayoutParams(layout);
                                imageView.setBackgroundResource(R.mipmap.start_half);
                                comment_score.addView(imageView);

                                for (int i = 0; i < 5 - score1 - 1; i++) {
                                    ImageView imageView1 = new ImageView(GoodDetailsActivity.this);
                                    imageView1.setLayoutParams(layout);
                                    imageView1.setBackgroundResource(R.mipmap.comment_start_gray);
                                    comment_score.addView(imageView1);
                                }
                            } else {
                                int score1 = Integer.parseInt(goodDetial.Score);
                                for (int i = 0; i < score1; i++) {
                                    ImageView imageView = new ImageView(GoodDetailsActivity.this);
                                    imageView.setLayoutParams(layout);
                                    imageView.setBackgroundResource(R.mipmap.comment_star);
                                    comment_score.addView(imageView);
                                }

                                for (int i = 0; i < 5 - score1; i++) {
                                    ImageView imageView = new ImageView(GoodDetailsActivity.this);
                                    imageView.setLayoutParams(layout);
                                    imageView.setBackgroundResource(R.mipmap.comment_start_gray);
                                    comment_score.addView(imageView);
                                }
                            }
                        }


                        if (goodDetial.IsFavourite) {
                            isFavourite = true;
                            collect.setBackgroundResource(R.mipmap.comment_star);
                        } else {
                            isFavourite = false;
                            collect.setBackgroundResource(R.mipmap.shouchang_white);
                        }

                        reloadMainView(goodDetial.Images);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //轮播图适配器
    private class FlowAdapter extends BaseAdapter {
        private Context mContext;
        private List<Images> mTopAdsArray;

        public FlowAdapter(List<Images> mTopAdsArray) {
            this.mTopAdsArray = mTopAdsArray;
            // this.mContext = context;
        }

        @Override
        public int getCount() {
            return mTopAdsArray == null ? 0 : mTopAdsArray.size();
        }

        @Override
        public Images getItem(int position) {
            return mTopAdsArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                ImageView view = new ImageView(GoodDetailsActivity.this);
                view.setLayoutParams(new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                //view.setOnClickListener(mAdViewClickListener);
                convertView = view;
            }

            String image = mTopAdsArray.get(position).ImagePath;
            //JSONObject topAdsObject = getItem(position);
            Uri imageUri = Uri.parse(image);
            convertView.setTag(R.id.tag_object, image);
            convertView.setTag(imageUri);
            MyImageLoader.displayDefaultImage(URLText.img_url + image, (ImageView) convertView);

            return convertView;
        }
    }

    /**
     * 设置自动滚动
     */
    public final int MESSAGE_AUTO_SNAP_FLOWVIEW = 1;
    private final long INTERVAL_AUTO_SNAP_FLOWVIEW = 2000L;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_AUTO_SNAP_FLOWVIEW) {
                mHandler.sendEmptyMessageDelayed(MESSAGE_AUTO_SNAP_FLOWVIEW, INTERVAL_AUTO_SNAP_FLOWVIEW);
                int count = mTopAdsView.getViewsCount();
                if (count > 1) {
                    int curScreen = mTopAdsView.getSelectedItemPosition();
                    if (curScreen >= (count - 1)) {
                        mTopAdsView.smoothScrollToScreen(0);

                    } else {
                        mTopAdsView.smoothScrollToScreen(curScreen + 1);
                    }
                }
            }
        }
    };

    private void reloadMainView(List<Images> mTopAdsArray) {
        if (mTopAdsArray != null && mTopAdsArray.size() > 0) {
//         添加轮播图
            mTopAdsView.setAdapter(new FlowAdapter(mTopAdsArray));
//
        }
    }

    //开始自动滚动
    public void startAutoRun() {
        mHandler.sendEmptyMessage(MESSAGE_AUTO_SNAP_FLOWVIEW);
    }

    ;

    //停止滚动
    public void stopAutoRun() {
        mHandler.removeMessages(MESSAGE_AUTO_SNAP_FLOWVIEW);
    }


//    // 添加广告指示器
//    private void addTopAdIndicator() {
//        CircleFlowIndicator mTopAdsIndicator = (CircleFlowIndicator) adsFrameLayout
//                .findViewById(R.id.goods_detail_images_indicator);
//        mTopAdsIndicator.setVisibility(View.VISIBLE);
//        mTopAdsView.setFlowIndicator(mTopAdsIndicator);
//        mTopAdsIndicator.setViewFlow(mTopAdsView);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.business_message:
                Intent intent = new Intent(GoodDetailsActivity.this, BusinessMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", goodDetial.User.Id);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.flow_me:
                Intent intent1 = new Intent(GoodDetailsActivity.this, TakeMeActivity.class);
                intent1.putExtra("good",goodDetial);
                startActivity(intent1);
                break;
            case R.id.shouchang:
                if (null != isFavourite && isFavourite) {
                    cancelCollect(goodDetial.Id);
                    isFavourite = false;
                } else if (null != isFavourite && isFavourite == false) {
                    addCollect(goodDetial.Id);
                    isFavourite = true;
                }
                break;
            case R.id.phone:
                Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + goodDetial.User.PhoneNumber));
                startActivity(in);
                break;
            case R.id.comment:
                Intent comment = new Intent(GoodDetailsActivity.this, GoodsCommentActiviyt.class);
                comment.putExtra("id", goodDetial.Id);
                comment.putExtra("score", goodDetial.Score);
                startActivity(comment);
                break;
        }
    }

    // 收藏商品
    private void addCollect(String id) {
        WebRequestHelper.json_post(GoodDetailsActivity.this, URLText.ADD_COLLECT, RequestParamsPool.addColect(id), new MyAsyncHttpResponseHandler(GoodDetailsActivity.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    collect.setBackgroundResource(R.mipmap.comment_star);
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.optString("Message");
                    Toast.makeText(GoodDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    //取消收藏商品
    public void cancelCollect(String id) {
        List<String> list = new ArrayList<String>();
        list.add(id);
        WebRequestHelper.json_post(GoodDetailsActivity.this, URLText.CANCEL_COLLECT, RequestParamsPool.cancelColect(id, null), new MyAsyncHttpResponseHandler(GoodDetailsActivity.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    collect.setBackgroundResource(R.mipmap.shouchang_white);
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.optString("Message");
                    Toast.makeText(GoodDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
