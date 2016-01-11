package com.wander.MyUtils;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */
public class urlUtils {
    //欢迎界面图片推送
    public static final String WEL_PUSH0 = "";
    //目的地（国家）
    public static final String DESTINATIONS = "https://chanyouji.com/api/destinations.json";
    //口袋书(包含多个小地区）
    public static final String POCKET = "https://chanyouji.com/api/destinations/";

    //目的地中id拼接（地区）
    //口袋书 行程
    public static final String PLANS = "https://chanyouji.com/api/destinations/plans/12.json?page=1";
    //口袋书  旅行地
    public static final String PLACE = "https://chanyouji.com/api/destinations/attractions/";
    //旅行地中 地图
    public static final String ATTRACTIONS = "https://chanyouji.com/api/destinations/attractions/";

    //景点详情
    public static final String SIGHT = "https://chanyouji.com/api/attractions/";
    //景点中的图片信息
    public static final String SIGHT_PIC = "https://chanyouji.com/api/attractions/photos/";
    //景点地图
    public static final String SIGHT_MAP = "https://chanyouji.com/api/attractions/nearby_pois/";


    //    http://wander0515.vicp.cc:54121/ZXTXserver/push_image/wel_push.jpeg
    //搭建的服务器
    public static final String server = "http://wander0515.vicp.cc:54121/ZXTXserver/";
    public static final String servlet = "http://wander0515.vicp.cc:54121/ZXTXserver/servlet/";
    //备用地址
    public static final String server0 = "http://192.168.1.106:8088/ZXTXserver/";
    public static final  String server1="";
    //欢迎界面图片推送
    public static final String WEL_PUSH = server + "push_image/wel_push.jpeg";
    //判断是否注册
    public static final String ISREGISTER = server + "servlet/isRegister?phone_num=";
    //注册
    public static final String Register = server + "servlet/Register";
    public static final String Register2 = server + "servlet/RegisterNoImage";

    //登陆
    public static final String LOGIN = server + "servlet/Login";
    //每次开启验证登陆
    public static final String CheckUser = server + "servlet/CheckUser";

    //发布内容--含图片
    public static final String RELEASE = server + "servlet/ReleaseIns";
    //发布内容  不含图片
    public static final String RELEASE_NO = server + "servlet/ReleaseInsNo";

    //获取热点内容
    public static final String HOT_INS = server + "servlet/GetHotJson";
    //获取关注的人的内容
    public static final String ATT_INS = servlet + "GetAttJson";

    //删除ins
    public static final String DELETE_INS = servlet + "UserDelete?ins_id=";

    //关注
    public static final String ATTENTION = servlet + "Attention?user_id=";
    //取消关注
    public static final String CANCEL_ATTENTION = servlet + "CancelAttention?user_id=";

    //点赞
    public static final String GOOD = servlet + "GiveGood?user_id=";
    //取消点赞
    public static final String CANCEL_GOOD = servlet + "CancelGood?user_id=";
    //收藏
    public static final String LIKE = servlet + "Like?user_id=";
    //取消收藏
    public static final String DISLIKE = servlet + "Dislike?user_id=";
    //添加评论
    public static final  String ADD_COMMENT=servlet+"AddComment";
    //显示评论
    public static final  String COMMENTS=servlet+"getComments";
    //获取更新
    public static final String CHECK_UPDATE=servlet+"CheckUpdate?version=";
    //下载更新
//    public static final String DOWNLOAD_UPDATE="http://www.bbyjpc.com/apk/QuestWorld.apk";
    public static final String DOWNLOAD_UPDATE="http://wander0515.vicp.cc:54121/ZXTXserver/file/QuestWorld.apk";

    //获取动态消息
    public static final String INFOR_COUNT=servlet+"getInforCount?user_id=";
    //获取动态
    public static final String  INFOR=servlet+"getInfo?user_id=";

    public static String getInfor(String user_id,String time){
        return INFOR+user_id+"&time="+time;
    }


    public static String getDISLIKE(String ins_id,String user_id) {
        return DISLIKE+user_id+"&ins_id="+ins_id;
    }

    public static String getLIKE(String ins_id,String user_id) {
        return LIKE+user_id+"&ins_id="+ins_id;
    }

    public static String getCancelGood(String ins_id,String user_id) {
        return CANCEL_GOOD+user_id+"&ins_id="+ins_id;
    }

    public static String getGOOD(String ins_id,String user_id) {
        return GOOD+user_id+"&ins_id="+ins_id;
    }

    public static String getCancelAttention(String user_id, String attention) {
        return CANCEL_ATTENTION + user_id + "&" + "attention=" + attention;
    }

    public static String getAttention(String user_id, String attention) {
        return ATTENTION + user_id + "&" + "attention=" + attention;
    }


    public static String getAttPhoto(String id, int pageNum) {
        return SIGHT_PIC + id + ".json" + "?page=" + pageNum;
    }

    public static String getSightNearby(String id) {
        return SIGHT_MAP + id + ".json";

    }

    public static String getSight(String id) {
        return SIGHT + id + ".json";
    }

    public static String getPlace(String id, int pageNum) {
        return PLACE + id + ".json" + "?page=" + pageNum;
    }

    public static String getPocket(String id) {
        return POCKET + id + ".json";
    }

    public static String getAttractions(String category) {
        return ATTRACTIONS + category + ".json?all=true";
    }

}
