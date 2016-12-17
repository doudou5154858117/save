package com.ximai.savingsmore.save.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.util.HashMap;
import java.util.List;
import java.util.zip.CheckedOutputStream;



//public class ShareUtils implements OnClickListener,ShareUtils, PlatformActionListener {
//    private Context context;
//    public PopupWindow shareWindow;
//    private ShareData data;
//
//    private LinearLayout wechat, qzone, wechatMoments;
//
//    public ShareUtils(ShareData data, Context context) {
//        this.data = data;
//        this.context = context;
//
//    }
//
//
//    //设置弹窗的显示
//    public void show(View parentView) {
//        if (shareWindow != null && shareWindow.isShowing()) {
//            shareWindow.dismiss();
//            setAlpath(1f);
//        }
//        View cw = ((Activity) context).getLayoutInflater().inflate(R.layout.item_share_layout, null);
//        wechat = (LinearLayout) cw.findViewById(R.id.wechat);
//        wechat.setOnClickListener(this);
//        qzone = (LinearLayout) cw.findViewById(R.id.qzone);
//        qzone.setOnClickListener(this);
//        wechatMoments = (LinearLayout) cw.findViewById(R.id.wechatmoments);
//        wechatMoments.setOnClickListener(this);
//
//        setAlpath(0.5f);
//        shareWindow = new PopupWindow(cw, LayoutParams.MATCH_PARENT, (int) ScreenUtils.dpToPx(context, 212));
//        shareWindow.setFocusable(true);
//        shareWindow.setTouchable(true);
//        shareWindow.setOutsideTouchable(true);
//
//        shareWindow.showAtLocation(parentView.getRootView(), Gravity.BOTTOM, 0, 0);
//        ((LinearLayout) cw).setFocusable(true);
//        ((LinearLayout) cw).setFocusableInTouchMode(true);
//        //弹窗的点击事件
//        cw.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                dismiss();
//                return false;
//            }
//        });
//
//        cw.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    switch (keyCode) {
//                        case KeyEvent.KEYCODE_BACK:
//                            dismiss();
//                            break;
//                        case KeyEvent.KEYCODE_MENU:
//                            dismiss();
//                            break;
//                    }
//                }
//                return true;
//            }
//        });
//    }
//
//    //关闭弹窗
//    public void dismiss() {
//        if (shareWindow != null && shareWindow.isShowing()) {
//            shareWindow.dismiss();
//            setAlpath(1f);
//        }
//    }
//
//
//    //取消分享
//    @Override
//    public void onCancel(Platform arg0, int arg1) {
//        Toast.makeText(context, R.string.share_cancle_tip, Toast.LENGTH_SHORT).show();
//
//    }
//
//    //分享成功
//    @Override
//    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//        Toast.makeText(context, R.string.share_suc_tip, Toast.LENGTH_SHORT).show();
//    }
//
//    //分享错误
//    @Override
//    public void onError(Platform arg0, int arg1, Throwable arg2) {
//        Toast.makeText(context, R.string.share_error_tip, Toast.LENGTH_SHORT).show();
//    }
//
//    //点击监听事件
//    @Override
//    public void onClick(View v) {
//        SharePlatfrom sharePlatfrom = new SharePlatfrom();
//        if (v.getId() == R.id.wechat) {
//            dismiss();
//            if(isWeixinAvilible(context))
//               sharePlatfrom.platfrom("wechat", data);
//            else
//                Toast.makeText(context,"您的微信还未安装呢！",Toast.LENGTH_SHORT).show();
//
//        }
//        if (v.getId() == R.id.wechatmoments) {
//            dismiss();
//            if(isWeixinAvilible(context))
//                sharePlatfrom.platfrom("wechat_circle", data);
//            else
//                Toast.makeText(context,"您的微信还未安装呢！",Toast.LENGTH_SHORT).show();
//
//
//        }
//        if (v.getId() == R.id.qzone) {
//            dismiss();
//            sharePlatfrom.platfrom("qzone", data);
//        }
//
//
//    }
//
//
//    public static boolean isWeixinAvilible(Context context) {
//        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals("com.tencent.mm")) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    //设置透明度
//    public void setAlpath(float alpath) {
//        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
//        params.alpha = alpath;
//        ((Activity) context).getWindow().setAttributes(params);
//    }
//
//    //实现点击窗体以外的部分能关闭弹窗
//    public void setWindowsBackground() {
//        if (shareWindow != null && shareWindow.isShowing()) {
//            shareWindow.dismiss();
//            setAlpath(1f);
//            shareWindow = null;
//        }
//
//    }
//
//
//    //判断分享类型，设置分享数据
//    public class SharePlatfrom {
//        private final static String PENGYOUQUAN = "wechat_circle";
//        private final static String QQKONGJIAN = "qzone";
//        private final static String WEIXIN = "wechat";
//
//        public void platfrom(String type, ShareData data) {
//            Platform platform = null;
//            String shareImagePath = data.getImagePath();
//            String shareUrl = data.getUrl();
//            String shareText = data.getText();
//            String shareimageUrl = data.getImageUrl();
//            String shareTitle = data.getTitle();
//            String shareSite = data.getSite();
//            String shareSiteUrl = data.getSiteUrl();
//            String shareTitltUrl = data.getTitleurl();
//            if (type == PENGYOUQUAN) {
//          /*     微信分享图文：必须设置title，imageUrl（imagepath，ImageData) text为可选参数
//                微信分享文本：title text都必须有
//                微信网页分享:title text imageUrl	url*/
//
//                platform = ShareSDK.getPlatform(context, WechatMoments.NAME);
//                WechatMoments.ShareParams params = new WechatMoments.ShareParams();
//                if (shareUrl != null) {
//                    params.setShareType(Platform.SHARE_WEBPAGE);
//                    params.setUrl(shareUrl);
//                } else {
//                    if (shareimageUrl != null || shareImagePath != null) {
//                        params.setShareType(Platform.SHARE_IMAGE);
//                    } else {
//                        params.setShareType(Platform.SHARE_TEXT);
//                    }
//                }
//
//                if (shareImagePath != null) {
//                    params.setImagePath(shareImagePath);
//                }
//                if (shareText != null) {
//                    params.setText(shareText);
//                }
//                if (shareimageUrl != null) {
//                    params.setImageUrl(shareimageUrl);
//                }
//                if (shareTitle != null) {
//                    params.setTitle(shareTitle);
//                }
//                platform.setPlatformActionListener(ShareUtils.this);
//                platform.share(params);
//            }
//            if (type == QQKONGJIAN) {
//                //qq空间图文分享必须设置四个参数不能少：setTitle(),setTitleUrl(),setText(),setImageUrl();
//
//                platform = ShareSDK.getPlatform(context, QZone.NAME);
//                QZone.ShareParams params = new QZone.ShareParams();
//                if (shareImagePath != null) {
//                    params.setImagePath(shareImagePath);
//                }
//                if (shareText != null) {
//                    params.setText(shareText);
//                }
//                if (shareimageUrl != null) {
//                    params.setImageUrl(shareimageUrl);
//                }
//                if (shareTitle != null) {
//                    params.setTitle(shareTitle);
//                }
//                if (shareSite != null) {
//                    params.setSite(shareSite);
//                }
//                if (shareSiteUrl != null) {
//                    params.setSiteUrl(null);
//                }
//                if (shareTitltUrl != null) {
//                    params.setTitleUrl(shareTitltUrl);
//                }
//
//                platform.setPlatformActionListener(ShareUtils.this);
//                platform.share(params);
//            }
//            if (type == WEIXIN) {
//                platform = ShareSDK.getPlatform(context, Wechat.NAME);
//                Wechat.ShareParams params = new Wechat.ShareParams();
//
//                if (shareUrl != null) {
//                    params.setShareType(Platform.SHARE_WEBPAGE);
//                    params.setUrl(shareUrl);
//                } else {
//                    if (shareimageUrl != null || shareImagePath != null) {
//                        params.setShareType(Platform.SHARE_IMAGE);
//                    } else {
//                        params.setShareType(Platform.SHARE_TEXT);
//                    }
//                }
//
//                if (shareImagePath != null) {
//                    params.setImagePath(shareImagePath);
//                }
//                if (shareText != null) {
//                    params.setText(shareText);
//                }
//                if (shareimageUrl != null) {
//                    params.setImageUrl(shareimageUrl);
//                }
//                if (shareTitle != null) {
//                    params.setTitle(shareTitle);
//                }
//                platform.setPlatformActionListener(ShareUtils.this);
//                platform.share(params);
//            }
//
//
//        }
//    }
//
//
//}
