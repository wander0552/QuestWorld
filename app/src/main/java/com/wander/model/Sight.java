package com.wander.model;

import java.util.List;

/**
 * Created by wander on 2015/5/23.
 * time 21:53
 * Email 18955260352@163.com
 */

/*"id": 34864,
        "name_zh_cn": "浅草寺",
        "name_en": "Sensoji Temple",
        "description": "浅草寺创建于628年，是东京都内最古老的寺院。江户时代将军德川家康把这里指定为幕府的祈愿所，是平安文化的中心地。",
        "tips_html": "<strong>#交通信息#</strong>\n<p>东京地下铁银座线、都营地下铁浅草线、东武伊势崎线，浅草站下车步行5分钟。</p>\n<strong>#夜间参观浅草寺#</strong>\n<p>为纪念江户幕府400年，浅草寺举办“璀璨闪耀的21世纪浅草”主题活动夜间开放到晚上11点。</p>\n<strong>#净手方法#</strong>\n<p>1.右手拿勺子洗左手 <br>\n2.左手拿勺子洗右手 <br>\n3.右手拿勺子，把水倒在左手 然后用左手里的水漱口 <br>\n4.把勺子立起来，用勺子里的水冲握着的部分 <br>\n5.将勺子放回去。</p>\n<strong>#大殿祈愿#</strong>\n<p>1.投香火钱，吉利数额最小的就是5块日元，因为谐音有缘分的意思。<br>\n2.鞠两躬<br>\n3.拍两下手<br>\n4.许愿<br>\n5.再鞠一躬</p>\n<strong>#游玩指南#</strong>\n<p>浅草寺是东京最热门的寺庙，旅行团必去景点，建议早晨早点去，避开人潮。</p>\n",
        "user_score": "4.12",
        "photos_count": 5054,
        "attraction_trips_count": 525,
        "lat": "35.71464",
        "lng": "139.796822",
        "attraction_type": "sight",
        "address": "日本台东区浅草2-3-1",
        "ctrip_id": null,
        "updated_at": 1402031460,
        "image_url": "http://m.chanyouji.cn/attractions/34864.jpg",
        "attractions_count": 658,
        "activities_count": 10,
        "restaurants_count": 18,*/
public class Sight {
    private String id;
    private String name_zh_cn;
    private String name_en;
    private String description;
    private String tips_html;
    private String user_score;
    private String photos_count;
    private String attraction_trips_count;
    private String attraction_type;
    private String address;
    private String ctrip_id;
    private String updated_at;
    private String image_url;
    private String attractions_count;
    private String activities_count;
    private String restaurants_count;
    private List<Tags> tagsList;
    private List<Nearby_Attractions> nearby_attractionsList;
    private List<Hotel> hotelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_zh_cn() {
        return name_zh_cn;
    }

    public void setName_zh_cn(String name_zh_cn) {
        this.name_zh_cn = name_zh_cn;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips_html() {
        return tips_html;
    }

    public void setTips_html(String tips_html) {
        this.tips_html = tips_html;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public String getPhotos_count() {
        return photos_count;
    }

    public void setPhotos_count(String photos_count) {
        this.photos_count = photos_count;
    }

    public String getAttraction_trips_count() {
        return attraction_trips_count;
    }

    public void setAttraction_trips_count(String attraction_trips_count) {
        this.attraction_trips_count = attraction_trips_count;
    }

    public String getAttraction_type() {
        return attraction_type;
    }

    public void setAttraction_type(String attraction_type) {
        this.attraction_type = attraction_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCtrip_id() {
        return ctrip_id;
    }

    public void setCtrip_id(String ctrip_id) {
        this.ctrip_id = ctrip_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAttractions_count() {
        return attractions_count;
    }

    public void setAttractions_count(String attractions_count) {
        this.attractions_count = attractions_count;
    }

    public String getActivities_count() {
        return activities_count;
    }

    public void setActivities_count(String activities_count) {
        this.activities_count = activities_count;
    }

    public String getRestaurants_count() {
        return restaurants_count;
    }

    public void setRestaurants_count(String restaurants_count) {
        this.restaurants_count = restaurants_count;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public List<Nearby_Attractions> getNearby_attractionsList() {
        return nearby_attractionsList;
    }

    public void setNearby_attractionsList(List<Nearby_Attractions> nearby_attractionsList) {
        this.nearby_attractionsList = nearby_attractionsList;
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @Override
    public String toString() {
        return "Sight{" +
                "id='" + id + '\'' +
                ", name_zh_cn='" + name_zh_cn + '\'' +
                ", name_en='" + name_en + '\'' +
                ", description='" + description + '\'' +
                ", tips_html='" + tips_html + '\'' +
                ", user_score='" + user_score + '\'' +
                ", photos_count='" + photos_count + '\'' +
                ", attraction_trips_count='" + attraction_trips_count + '\'' +
                ", attraction_type='" + attraction_type + '\'' +
                ", address='" + address + '\'' +
                ", ctrip_id='" + ctrip_id + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", image_url='" + image_url + '\'' +
                ", attractions_count='" + attractions_count + '\'' +
                ", activities_count='" + activities_count + '\'' +
                ", restaurants_count='" + restaurants_count + '\'' +
                ", tagsList=" + tagsList +
                ", nearby_attractionsList=" + nearby_attractionsList +
                ", hotelList=" + hotelList +
                '}';
    }
}
