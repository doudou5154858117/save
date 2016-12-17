package com.ximai.savingsmore.library.net;

import android.text.TextUtils;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.ximai.savingsmore.library.toolbox.GsonUtils;
import com.ximai.savingsmore.library.toolbox.LogUtils;
import com.ximai.savingsmore.save.activity.SettingActivity;
import com.ximai.savingsmore.save.modle.BusinessScopes;
import com.ximai.savingsmore.save.modle.Images;
import com.ximai.savingsmore.save.modle.LoginUser;
import com.ximai.savingsmore.save.modle.MyProduct;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;
import com.ximai.savingsmore.save.modle.UserParameter;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wangguodong
 */
public class RequestParamsPool {


//    //统一加密处理
//    public static String encryptData(String url, String str) {
//
//        String result = "";
//
//        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(str)) {
//            LogUtils.instance.d("您请求加密的数据错误####");
//            return result;
//        }
//
//        String urlStr = url.substring(url.lastIndexOf("?")+1, url.length());
//
//        try {
//            result = DesUtils.encode(str,urlStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }

    //登录参数
    public static RequestParams getLoginParams(String phone, String password, String pusuId, int type) {
        RequestParams params = new RequestParams();
        params.put("UserName", phone);
        params.put("Password", password);
        params.put("UserType", type);
        return params;
    }

    // 发送验证码
    public static RequestParams getCodeParams(String UserName, int CodeType) {
        RequestParams params = new RequestParams();
        params.put("UserName", UserName);
        params.put("CodeType", CodeType);
        return params;
    }

    //注册
    public static RequestParams getRegristes(String phone, String password, String confimPassword, String code, int type) {
        RequestParams params = new RequestParams();
        params.put("PhoneNumber", phone);
        params.put("Password", password);
        params.put("ConfirmPassword", confimPassword);
        params.put("Code", code);
        params.put("UserType", type);
        return params;
    }

    //找回密码
    public static RequestParams getResetPassword(String phone, String password, String Code) {
        RequestParams params = new RequestParams();
        params.put("PhoneNumber", phone);
        params.put("NewPassword", password);
        params.put("ConfirmPassword", password);
        params.put("Code", Code);
        return params;
    }

    //得到所有商品
    public static StringEntity getAllGoods(int PageNo, int PageSize, Boolean IsRebateDesc, Boolean IsPriceDesc, Boolean IsStartTimeDesc, Boolean IsDistanceDesc, String Keyword,
                                           String isBag, String isState, String class1, String class2, String brand, String type) {
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("PageNo", PageNo);
            object.put("PageSize", PageSize);
            object1.put("PageParameter", object);
            if (IsRebateDesc) {
                object1.put("IsRebateDesc", IsRebateDesc);
            }
            if (null != class1 && !TextUtils.isEmpty(class1)) {
                object1.put("FirstClassId", class1);
            }
            if (null != class2 && !TextUtils.isEmpty(class2)) {
                object1.put("SecondClassId", class2);
            }
            if (null != brand && !TextUtils.isEmpty(brand)) {
                object1.put("BrandId", brand);
            }
            if (null != type && !TextUtils.isEmpty(type)) {
                object1.put("PromotionType", type);
            }
            if (null != isBag && !TextUtils.isEmpty(isBag)) {
                object1.put("IsBag", isBag);
            }
            if (null != isState && !TextUtils.isEmpty(isState)) {
                object1.put("IsPromotion", isState);
            }
            if (IsPriceDesc) {
                object1.put("IsPriceDesc", IsPriceDesc);
            }
            if (IsStartTimeDesc) {
                object1.put("IsStartTimeDesc", IsStartTimeDesc);
            }
            if (IsDistanceDesc) {
                object1.put("IsDistanceDesc", IsDistanceDesc);
            }

            if (!TextUtils.isEmpty(Keyword)) {
                object1.put("Keyword", Keyword);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity stringEntity = null;
        try {
            // stringEntity  = new StringEntity("{\"Keyword\":\"你\",\"PageParameter\":{\"PageSize\":10,\"PageNo\":1}}");
            stringEntity = new StringEntity(object1.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;

    }

    //得到商品详情
    public static StringEntity getGoodDetial(String id) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object1.put("SecurityStampParameter", object);
            object1.put("Id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object1.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;

    }

    //得到用户信息
    public static StringEntity getUserInfo() {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        try {
            object.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    // 得到促销商品
    public static StringEntity getSalesGoods(String SellerId) {
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("PageNo", 1);
            object.put("PageSize", 1000);
            object1.put("PageParameter", object);
            object1.put("SellerId", SellerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity stringEntity = null;
        try {
            // stringEntity  = new StringEntity("{\"Keyword\":\"你\",\"PageParameter\":{\"PageSize\":10,\"PageNo\":1}}");
            stringEntity = new StringEntity(object1.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //得到热门商品
    public static StringEntity getHotGoods(Boolean isBag) {
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("PageNo", 1);
            object.put("PageSize", 1000);
            object1.put("PageParameter", object);
            // object1.put("IsTop",true);
            object1.put("IsBag", isBag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(object1.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    public static StringEntity getMySalesGoods(boolean isEnd, boolean isComment) {
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        try {
            object.put("PageNo", 1);
            object.put("PageSize", 1000);
            object2.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object3.put("IsEnd", isEnd);
            if (isComment) {
                object3.put("IsCommented", isComment);
            }
            object1.put("SearchParameter", object3);
            object1.put("SecurityStampParameter", object2);
            object1.put("PageParameter", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(object1.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }


    // 添加收藏商品
    public static StringEntity addColect(String id) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("Id", id);
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //取消收藏
    public static StringEntity cancelColect(String id, List<String> list) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONArray array = new JSONArray();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                array.put(list.get(i));
            }
        }
        try {
            if (null != list && list.size() != 0) {
                object.put("IdList", array);
            }
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            if (null != id && !TextUtils.isEmpty(id)) {
                object.put("IdListJson", id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //收藏店铺
    public static StringEntity focusBusiness(String id) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object.put("Id", id);
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //移除收藏店铺
    public static StringEntity cancelBusiness(String id, List<String> list) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONArray array = new JSONArray();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                array.put(list.get(i));
            }
        }
        try {
            if (null != list && list.size() != 0) {
                object.put("IdList", array);
            }
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            if (null != id && !TextUtils.isEmpty(id)) {
                object.put("IdListJson", id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //得到收藏的商品列表
    public static StringEntity collectGoods() {
        StringEntity stringEntity = null;
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object2.put("PageNo", 1);
            object2.put("PageSize", 1000);
            object.put("PageParameter", object2);
            object.put("SecurityStampParameter", object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //收藏的店铺的烈表
    public static StringEntity collectBusiness() {
        StringEntity stringEntity = null;
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object2.put("PageNo", 1);
            object2.put("PageSize", 1000);
            object.put("PageParameter", object2);
            object.put("SecurityStampParameter", object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //店铺信息
    public static StringEntity getBusinessMessage(String id) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            object.put("Id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return stringEntity;
    }

    //得到评论
    public static StringEntity getGoodsComment(String id) {
        StringEntity stringEntity = null;
        JSONObject object2 = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object2.put("PageNo", 1);
            object2.put("PageSize", 1000);
            object.put("PageParameter", object2);
            object.put("Id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //提交评论
    public static StringEntity submitComment(String Id, String ProductScore, String SellerScore, String SellerScore2, String SellerScore3, String Content
            , List<Images> list, boolean IsAnonymous) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("Id", Id);
            object.put("ProductScore", ProductScore);
            object.put("SellerScore", SellerScore);
            object.put("SellerScore2", SellerScore2);
            object.put("SellerScore3", SellerScore3);
            object.put("Content", Content);
            object.put("SecurityStampParameter", object1);
            JSONArray array = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                JSONObject object2 = new JSONObject();
                object2.put("Id", list.get(i).ImageId);
                object2.put("FilePath", list.get(i).ImagePath);
                object2.put("SortNo", i);
                array.put(object2);
            }
            object.put("Images", array);
            object.put("IsAnonymous", IsAnonymous);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }


    //得到我的信息

    public static StringEntity getMyUserInfo() {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        try {
            object.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //上传图片
    public static StringEntity upLoadImage(File file, String type) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        try {
            object.put("file", file);
            object.put("FileType", type);
            object.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("Password", "15047754A79842C784E56355FC691E6A");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    public static RequestParams upLoad(File file, String type) {
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("file", file);
            requestParams.put("FileType", type);
            requestParams.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            requestParams.put("Password", "15047754A79842C784E56355FC691E6A");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return requestParams;
    }


    //保存用户信息(商户)
    public static StringEntity saveMessage(UserParameter userParameter) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        try {
            object2.put("UserDisplayName", userParameter.UserDisplayName);
            object2.put("PhotoId", userParameter.PhotoId);
            object2.put("PhotoPath", userParameter.PhotoPath);
            object2.put("Sex", userParameter.Sex);
            object2.put("Domicile", userParameter.Domicile);
            object2.put("WeChat", userParameter.WeChat);
            object2.put("Post", userParameter.Post);
            object2.put("ProvinceId", userParameter.ProvinceId);
            object2.put("CityId", userParameter.CityId);
            object2.put("AreaId", userParameter.AreaId);
            object3.put("FoundingDateName", userParameter.UserExtInfo.FoundingDateName);
            object3.put("StoreName", userParameter.UserExtInfo.StoreName);
            object3.put("OfficePhone", userParameter.UserExtInfo.OfficePhone);
            object3.put("WebSite", userParameter.UserExtInfo.WebSite);
            object3.put("FoundingDate", userParameter.UserExtInfo.FoundingDate);
            object3.put("BusinessLicenseId", userParameter.UserExtInfo.BusinessLicenseId);
            object3.put("BusinessLicensePath", userParameter.UserExtInfo.BusinessLicensePath);
            object3.put("BusinessLicenseNumber", userParameter.UserExtInfo.BusinessLicenseNumber);
            object3.put("LicenseKeyId", userParameter.UserExtInfo.LicenseKeyId);
            object3.put("LicenseKeyPath", userParameter.UserExtInfo.LicenseKeyPath);
            object3.put("LicenseKeyNumber", userParameter.UserExtInfo.LicenseKeyNumber);
            object3.put("EndHours", userParameter.UserExtInfo.EndHours);
            object3.put("StartHours", userParameter.UserExtInfo.StartHours);
            JSONArray jsonArray = new JSONArray();
            JSONObject object4 = new JSONObject();
            JSONObject object5 = new JSONObject();
            if (null != userParameter.BusinessScopes) {
                if (null != userParameter.BusinessScopes.get(0) && userParameter.BusinessScopes.size() > 0) {
//                object5.put("Name", userParameter.BusinessScopes.get(0).DictNode.Name);
//                object5.put("SortNo", userParameter.BusinessScopes.get(0).DictNode.SortNo);
//                object5.put("Id", userParameter.BusinessScopes.get(0).DictNode.Id);
                    object4.put("BusinessScopeType", 1);
                    object4.put("DictNodeId", userParameter.BusinessScopes.get(0).DictNodeId);
                }
            }
            jsonArray.put(object4);
            JSONArray jsonArray1 = new JSONArray();
            if (null != userParameter.UserExtInfo.Images) {
                for (int i = 0; i < userParameter.UserExtInfo.Images.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ImageId", userParameter.UserExtInfo.Images.get(i).ImageId);
                    jsonObject.put("ImagePath", userParameter.UserExtInfo.Images.get(i).ImagePath);
                    jsonArray1.put(jsonObject);
                }
            }
            object3.put("Images", jsonArray1);
            object2.put("UserExtInfo", object3);
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object2.put("BusinessScopes", jsonArray);
            object.put("SecurityStampParameter", object1);
            object.put("UserParameter", object2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // String newStr = new String(object.toString().getBytes(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET).trim();
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringEntity;
    }

    //保存用户信息(个人)
    public static StringEntity savePersonMessage(UserParameter userParameter) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        try {
            object2.put("UserDisplayName", userParameter.UserDisplayName);
            object2.put("PhotoId", userParameter.PhotoId);
            object2.put("PhotoPath", userParameter.PhotoPath);
            object2.put("Sex", userParameter.Sex);
            object2.put("Domicile", userParameter.Domicile);
            object2.put("Post", userParameter.Post);
            object2.put("ProvinceId", userParameter.ProvinceId);
            object2.put("CityId", userParameter.CityId);
            object2.put("AreaId", userParameter.AreaId);
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            object.put("UserParameter", object2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // String newStr = new String(object.toString().getBytes(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET).trim();
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //保存商品信息
    public static StringEntity getMyProduct(MyProduct myProduct) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            JSONArray jsonArray = new JSONArray();
            if (myProduct.Images.size() > 0) {
                for (int i = 0; i < myProduct.Images.size(); i++) {
                    JSONObject images = new JSONObject();
                    images.put("ImageId", myProduct.Images.get(i).ImageId);
                    images.put("ImagePath", myProduct.Images.get(i).ImagePath);
                    images.put("SortNo", myProduct.Images.get(i).SortNo);
                    jsonArray.put(images);
                }
            }
            if (null != myProduct.Id) {
                object2.put("Id", myProduct.Id);
            }
            object2.put("Images", jsonArray);
            object2.put("Name", myProduct.Name);
            object2.put("Number", myProduct.Number);
            object2.put("FirstClassId", myProduct.FirstClassId);
            object2.put("SecondClassId", myProduct.SecondClassId);
            object2.put("ProvinceId", myProduct.ProvinceId);
            object2.put("CityId", myProduct.CityId);
            object2.put("AreaId", myProduct.AreaId);
            object2.put("Address", myProduct.Address);
            object2.put("BrandId", myProduct.BrandId);
            object2.put("StartTime", myProduct.StartTime);
            object2.put("EndTime", myProduct.EndTime);
            object2.put("OriginalPrice", myProduct.OriginalPrice);
            object2.put("PromotionCause", "aaaaaaa");
            object2.put("Price", myProduct.Price);
            object2.put("IsProcurementService", true);
            object2.put("CurrencyId", myProduct.CurrencyId);
            object2.put("UnitId", myProduct.UnitId);
            object2.put("PromotionType", myProduct.PromotionType);
            object2.put("InvoiceId", myProduct.InvoiceId);
            object2.put("Introduction", myProduct.Introduction);
            object2.put("Description", myProduct.Description);
            object.put("ProductParameter", object2);
            object.put("SecurityStampParameter", object1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    public static StringEntity apply_classity(String name, String ParentId) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            object.put("Name", name);
            object.put("ParentId", ParentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    public static StringEntity save_brand(String name) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            object.put("Name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //商家列表
    public static StringEntity businessList(String keyWord) {
        StringEntity stringEntity = null;
        JSONObject object2 = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object2.put("PageNo", 1);
            object2.put("PageSize", 1000);
            object.put("PageParameter", object2);
            object.put("Keyword", keyWord);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString(), MyAsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    //根据环信的到用户信息
    public static StringEntity getUserByIM(List<String> userName) {
        StringEntity stringEntity = null;
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < userName.size(); i++) {
                array.put(userName.get(i));
            }
            object1.put("SecurityStamp", LoginUser.getInstance().userInfo.SecurityStamp);
            object.put("SecurityStampParameter", object1);
            object.put("IMUserNameList", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            stringEntity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }


}
