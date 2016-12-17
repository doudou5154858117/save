package com.ximai.savingsmore.save.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.cache.MyImageLoader;
import com.ximai.savingsmore.library.net.MyAsyncHttpResponseHandler;
import com.ximai.savingsmore.library.net.RequestParamsPool;
import com.ximai.savingsmore.library.net.URLText;
import com.ximai.savingsmore.library.net.WebRequestHelper;
import com.ximai.savingsmore.library.toolbox.GsonUtils;
import com.ximai.savingsmore.library.view.MyGridView;
import com.ximai.savingsmore.library.view.RoundImageView;
import com.ximai.savingsmore.save.common.BaseActivity;
import com.ximai.savingsmore.save.modle.Comment;
import com.ximai.savingsmore.save.modle.CommentList;
import com.ximai.savingsmore.save.modle.Images;
import com.ximai.savingsmore.save.modle.LoginUser;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caojian on 16/12/1.
 */
//商品评论页面
public class GoodsCommentActiviyt extends BaseActivity {
    private String id;
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<Images> imagesList = new ArrayList<Images>();
    private ListAdapter listAdapter;
    private ListView listView;
    private String score;
    private LinearLayout linearLayout;
    private TextView score1;
    private LinearLayout all_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_comment_activity);
        setCenterTitle("商品评论");
        setLeftBackMenuVisibility(GoodsCommentActiviyt.this, "");
        if (MyUserInfoUtils.getInstance().myUserInfo.UserType.equals("2")) {
            addRightTextMenu("我要评价", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GoodsCommentActiviyt.this, IssueCommentActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });
        }
        listView = (ListView) findViewById(R.id.listview);
        id = getIntent().getStringExtra("id");
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);
        score = getIntent().getStringExtra("score");
        score1 = (TextView) findViewById(R.id.score);
        linearLayout = (LinearLayout) findViewById(R.id.start_comment);
        all_score = (LinearLayout) findViewById(R.id.all_score);
        getComment(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getComment(id);
    }

    private void getComment(String id) {
        WebRequestHelper.json_post(GoodsCommentActiviyt.this, URLText.GOODS_COMMENT, RequestParamsPool.getGoodsComment(id), new MyAsyncHttpResponseHandler(GoodsCommentActiviyt.this) {
            @Override
            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                CommentList commentList1 = GsonUtils.fromJson(result, CommentList.class);
                commentList = commentList1.MainData;
                if (commentList.size() > 0 && null != score) {
                    linearLayout.removeAllViews();
                    all_score.setVisibility(View.VISIBLE);
                    score1.setText(score + "分");
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                            50, 50);
                    layout.setMargins(5, 0, 5, 0);
                    if (score.length() > 1) {
                        int score1 = Integer.parseInt(score.substring(0, 1));
                        for (int i = 0; i < score1; i++) {
                            ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                            imageView.setLayoutParams(layout);
                            imageView.setBackgroundResource(R.mipmap.comment_star);
                            linearLayout.addView(imageView);
                        }
                        ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                        imageView.setLayoutParams(layout);
                        imageView.setBackgroundResource(R.mipmap.start_half);
                        linearLayout.addView(imageView);

                        for (int i = 0; i < 5 - score1 - 1; i++) {
                            ImageView imageView1 = new ImageView(GoodsCommentActiviyt.this);
                            imageView1.setLayoutParams(layout);
                            imageView1.setBackgroundResource(R.mipmap.comment_start_gray);
                            linearLayout.addView(imageView1);
                        }
                    } else {
                        int score1 = Integer.parseInt(score);
                        for (int i = 0; i < score1; i++) {
                            ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                            imageView.setLayoutParams(layout);
                            imageView.setBackgroundResource(R.mipmap.comment_star);
                            linearLayout.addView(imageView);
                        }

                        for (int i = 0; i < 5 - score1; i++) {
                            ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                            imageView.setLayoutParams(layout);
                            imageView.setBackgroundResource(R.mipmap.comment_start_gray);
                            linearLayout.addView(imageView);
                        }
                    }

                }
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public Object getItem(int position) {
            return commentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ListViewHodel listViewHodel;
            if (convertView == null) {
                listViewHodel = new ListViewHodel();
                convertView = LayoutInflater.from(GoodsCommentActiviyt.this).inflate(R.layout.comment_item, null);
                listViewHodel.head_image = (RoundImageView) convertView.findViewById(R.id.head_iamge);
                listViewHodel.name = (TextView) convertView.findViewById(R.id.name);
                listViewHodel.comment_score = (LinearLayout) convertView.findViewById(R.id.comment_star);
                listViewHodel.date = (TextView) convertView.findViewById(R.id.date);
                listViewHodel.content = (TextView) convertView.findViewById(R.id.content);
                listViewHodel.gridView = (MyGridView) convertView.findViewById(R.id.gridview);
                convertView.setTag(listViewHodel);
            } else {
                listViewHodel = (ListViewHodel) convertView.getTag();
            }
            MyImageLoader.displayDefaultImage(URLText.img_url + commentList.get(position).User.PhotoPath, listViewHodel.head_image);
            listViewHodel.name.setText(commentList.get(position).User.ShowName);
            listViewHodel.date.setText(commentList.get(position).CreateDateName);
            listViewHodel.content.setText(commentList.get(position).Content);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                    50, 50);
            layout.setMargins(5, 0, 5, 0);
            listViewHodel.comment_score.removeAllViews();
            if (commentList.get(position).ProductScore.length() > 1) {
                int score1 = Integer.parseInt(commentList.get(position).ProductScore.substring(0, 1));
                for (int i = 0; i < score1; i++) {
                    ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                    imageView.setLayoutParams(layout);
                    imageView.setBackgroundResource(R.mipmap.comment_star);
                    listViewHodel.comment_score.addView(imageView);
                }
                ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                imageView.setLayoutParams(layout);
                imageView.setBackgroundResource(R.mipmap.start_half);
                listViewHodel.comment_score.addView(imageView);

                for (int i = 0; i < 5 - score1 - 1; i++) {
                    ImageView imageView1 = new ImageView(GoodsCommentActiviyt.this);
                    imageView1.setLayoutParams(layout);
                    imageView1.setBackgroundResource(R.mipmap.comment_start_gray);
                    listViewHodel.comment_score.addView(imageView1);
                }
            } else {
                int score1 = Integer.parseInt(commentList.get(position).ProductScore);
                for (int i = 0; i < score1; i++) {
                    ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                    imageView.setLayoutParams(layout);
                    imageView.setBackgroundResource(R.mipmap.comment_star);
                    listViewHodel.comment_score.addView(imageView);
                }
                for (int i = 0; i < 5 - score1; i++) {
                    ImageView imageView = new ImageView(GoodsCommentActiviyt.this);
                    imageView.setLayoutParams(layout);
                    imageView.setBackgroundResource(R.mipmap.comment_start_gray);
                    listViewHodel.comment_score.addView(imageView);
                }
            }
            imagesList = commentList.get(position).Images;
            listViewHodel.gridView.setAdapter(new GridViewAdapter());
            return convertView;
        }
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imagesList.size();
        }

        @Override
        public Object getItem(int position) {
            return imagesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewViewHodel gridViewViewHodel;
            if (convertView == null) {
                convertView = LayoutInflater.from(GoodsCommentActiviyt.this).inflate(R.layout.commen_gridview_item, null);
                gridViewViewHodel = new GridViewViewHodel();
                gridViewViewHodel.imageView = (ImageView) convertView.findViewById(R.id.iamge);
                convertView.setTag(gridViewViewHodel);
            }
            gridViewViewHodel = (GridViewViewHodel) convertView.getTag();
            MyImageLoader.displayDefaultImage(URLText.img_url + imagesList.get(position).ImagePath, gridViewViewHodel.imageView);
            return convertView;
        }
    }

    class ListViewHodel {
        RoundImageView head_image;
        TextView name;
        LinearLayout comment_score;
        TextView date;
        TextView content;
        MyGridView gridView;
    }

    class GridViewViewHodel {
        ImageView imageView;
    }
}
