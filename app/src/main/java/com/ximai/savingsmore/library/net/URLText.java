package com.ximai.savingsmore.library.net;


import com.ximai.savingsmore.library.constants.AppConstants;

/**
 * @author wangguodong
 */
public interface URLText {
    public static final String img_url = "http://fileupload.savingsmore.com/ServerFiles/";
    public static final String upLoad = "http://fileupload.savingsmore.com/api/";

    public static final String baseUrl = AppConstants.BASEURL;
    //登录接口
    public static final String LOGIN_URL = baseUrl + "Account/Signin";
    //发送验证码
    public static final String SEND_CODE = baseUrl + "SMS/SendCode";
    //注册
    public static final String REGISTER_CODE = baseUrl + "Account/Signup";
    //找回密码
    public static final String RESET_PASSWORD = baseUrl + "Account/ForgetPassword";
    //得到商品
    public static final String GET_GOODS = baseUrl + "Product/QueryProductList";
    //得到商品详情
    public static final String GET_GOOD_DETIAL = baseUrl + "Product/Detail";
    //得到用户的信息
    public static final String GET_USERINFO = baseUrl + "User/QueryMyInfo";
    //    //得打商店促销的商品
    public static final String GET_SALES_GOODS = baseUrl + "Product/QueryMyProduct";
    //添加收藏商品
    public static final String ADD_COLLECT = baseUrl + "Product/Favourite";
    //取消收藏商品
    public static final String CANCEL_COLLECT = baseUrl + "Product/RemoveFavourite";
    // 添加收藏店铺
    public static final String ADD_COLLECT_BUSINESS = baseUrl + "User/Favourite";
    //取消收藏店铺
    public static final String CANCEL_COLLECT_BUSINESS = baseUrl + "User/RemoveFavourite";
    //收藏的商品
    public static final String COLLECT_GOODS = baseUrl + "Product/QueryFavourite";
    //收藏的店铺
    public static final String COLLECT_BUSINESS = baseUrl + "User/QueryFavourite";
    //获取店铺 的信息
    public static final String USER_DETIAL = baseUrl + "User/Detail";
    //商品评论
    public static final String GOODS_COMMENT = baseUrl + "Comment/QueryComment";
    //发表评论
    public static final String SUBMIT_COMMENT = baseUrl + "Comment/SubmitComment";
    //上传图片
    public static final String UP_LOAD = baseUrl + "FileManager/UploadFile";
    //查询基础字典
    public static final String QUERYDICNODE = baseUrl + "DictNode/QueryDictNode";
    //查询基础字典2
    public static final String QUERYDICNODE2 = baseUrl + "DictNode/QueryDictNode3";
    //获取我的资料
    public static final String OUERY_MYINFO = baseUrl + "User/QueryMyInfo";
    //上传图片
    public static final String UPLOAD_IMAGE = upLoad + "FileManager/UploadFile";
    //保存个人信息
    public static final String SAVE_MESSAGE = baseUrl + "User/SaveMyInfo";
    //保存商品信息
    public static final String SAVEMYPRODUCT = baseUrl + "Product/SaveMyProduct";
    //申请分类
    public static final String APPLY_CLASSITY = baseUrl + "Product/SaveProductClass";
    //申请品牌
    public static final String SAVEBRAND = baseUrl + "Product/SaveBrand";
    //商家列表
    public static final String BUSINESS_LIST = baseUrl + "User/QuerySellerList";
    //得到环信
    public static final String USERBYIM = baseUrl + "User/QueryUserByIM";


}
