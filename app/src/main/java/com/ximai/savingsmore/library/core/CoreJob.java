package com.ximai.savingsmore.library.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ximai.savingsmore.library.FileSystem.FileSystem;
import com.ximai.savingsmore.library.cache.ImageCachePool;
import com.ximai.savingsmore.library.net.WebRequestHelper;
import com.ximai.savingsmore.save.utils.ImageTools;

//import org.apache.http.Header;

import java.util.Vector;

/**
 * 核心处理类
 * @author wangguodong
 */
public class CoreJob {

    private static Vector<Activity> activities = new Vector<Activity>();
    public static boolean isExiting = false;
    public static void init(Context context){
        //初始化文件系
        FileSystem.init(context);
        //初始化图片缓存
        ImageCachePool.initImageLoader(context);
        //初始化数据库
    }

    public static synchronized void addToActivityStack(Activity activity) {
        if (activities == null)
            activities = new Vector<Activity>();
        if (!isExiting)
            activities.add(activity);
    }

    public static synchronized void removeFormActivityStack(Activity activity) {
        if (activities != null && !isExiting)
            activities.remove(activity);
    }


    // 退出应用

    /**
     *
     *
     * @param goToLogin 是否前往登录界面
     * @param flag 0/1  区分是否被踢
     */
//    public static void exitApplication(boolean goToLogin,int flag,boolean isSavePwd) {
//
//        isExiting = true;
//        toDoOnExit(goToLogin,flag,isSavePwd);//处理退出前的工作
//        if (activities != null) {
//
//            for (Activity activity : activities) {
//                activity.finish();
//            }
//        }
//
//        if(goToLogin)
//        {
//
//            Intent it =new Intent(BaseApplication.getInstance(), LoginActivity.class);
//            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            it.putExtra("isAutoLogin",!isSavePwd);
//            BaseApplication.getInstance().startActivity(it);
//        }
//
//        isExiting=false;
//
//    }



//    public static void logout(int flag){
//
//        WebRequestHelper.post(URLText.LOGOUT_URL, RequestParamsPool.getKickoffParams(URLText.LOGOUT_URL,flag), new MyAsyncHttpResponseHandler(BaseApplication.getInstance()) {
//            @Override
//            public void onResponse(int statusCode, Header[] headers, byte[] responseBody) {
//
//                LogUtils.instance.d(new String(responseBody));
//            }
//
//
//            @Override
//            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//                super.onFailure(arg0, arg1, arg2, arg3);
//            }
//        });
//
//    }
    
    
    //退出app要处理的事情
//    private static void toDoOnExit(boolean goToLogin,int flag,boolean isSavePwd) {
//
//
//        //logout(flag);
//
//        WebRequestHelper.setCookies(null);
//
//        if(isSavePwd)
//           // LoginUser.getInstance().saveLoginUserPwd("");
//
//        //清除loginuser
//       // LoginUser.clearInstance();
//        //退出聊天
//        //BaseApplication.LogoutEasaChat();
//    }


}
