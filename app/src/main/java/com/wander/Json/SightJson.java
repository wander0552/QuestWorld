package com.wander.Json;

import com.amap.api.maps2d.model.LatLng;
import com.wander.model.AttractionPhoto;
import com.wander.model.Attraction_content;
import com.wander.model.Attractions;
import com.wander.model.Destination;
import com.wander.model.Destinations;
import com.wander.model.Hotel;
import com.wander.model.Nearby_Attractions;
import com.wander.model.Note;
import com.wander.model.Place;
import com.wander.model.Sight;
import com.wander.model.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */
public class SightJson {

    /*"id": 3327769,
        "image_width": 1600,
        "image_height": 1154,
        "note_id": 3654341,
        "likes_count": 42,
        "image_url": "http://p.chanyouji.cn/93740/1388042805912p18cmsjuajqo44sh1lmdf61ea61q.jpg",
        "description": "清水寺日落前的逆光",
        "trip_id": 93740,
        "trip_name": "迷失东瀛"*/

    public  static List<AttractionPhoto> getPhotos(String json){
        List<AttractionPhoto> list=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(json);
            int length = array.length();
            for (int i = 0; i < length; i++) {
                AttractionPhoto photo=new AttractionPhoto();
                JSONObject data=array.getJSONObject(i);
                photo.setId(data.optString("id"));
                photo.setDescription(data.optString("description"));
                photo.setImage_height(data.optInt("image_height"));
                photo.setImage_url(data.optString("image_url"));
                photo.setImage_width(data.optInt("image_width"));
                photo.setLikes_count(data.optString("likes_count"));
                photo.setNote_id(data.optString("note_id"));
                photo.setTrip_id(data.optString("trip_id"));
                photo.setTrip_name(data.optString("trip_name"));
                list.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //景点详情页
    public static Sight getSight(String json) {
        Sight sight = new Sight();
        try {
            JSONObject object = new JSONObject(json);
            sight.setId(object.getString("id"));
            sight.setName_zh_cn(object.getString("name_zh_cn"));
            sight.setName_en(object.optString("name_en"));
            sight.setDescription(object.optString("description"));
            sight.setTips_html(object.optString("tips_html"));
            sight.setUser_score(object.optString("user_score"));
            sight.setPhotos_count(object.optString("photos_count"));
            sight.setAttraction_trips_count(object.optString("attraction_trips_count"));
            sight.setAttraction_type(object.optString("attraction_type"));
            sight.setAddress(object.optString("address"));
            sight.setCtrip_id(object.getString("ctrip_id"));
            sight.setUpdated_at(object.optString("updated_at"));
            sight.setImage_url(object.optString("image_url"));
            sight.setAttractions_count(object.optString("attractions_count"));
            sight.setActivities_count(object.optString("activities_count"));
            sight.setRestaurants_count(object.optString("restaurants_count"));

            JSONArray nearby = object.getJSONArray("attractions");
            if (nearby != null) {
                List<Nearby_Attractions> nearby_attractionsList=new ArrayList<>();
                int length = nearby.length();
                for (int i = 0; i < length; i++) {
                    JSONObject data = nearby.getJSONObject(i);
                    Nearby_Attractions nearby_attractions = new Nearby_Attractions();
                    nearby_attractions.setId(data.optString("id"));
                    nearby_attractions.setImage_url(data.optString("image_url"));
                    nearby_attractions.setDistance(data.optString("distance"));
                    nearby_attractions.setName_zh_cn(data.optString("name_zh_cn"));
                    nearby_attractions.setUser_score(data.optString("user_score"));
                    nearby_attractionsList.add(nearby_attractions);
                }
                sight.setNearby_attractionsList(nearby_attractionsList);
            }
            JSONArray hotels = object.getJSONArray("hotels");
            if (hotels!=null){
                List<Hotel> list=new ArrayList<>();
                int length = hotels.length();
                for (int i = 0; i < length; i++) {
                    JSONObject data=hotels.getJSONObject(i);
                    Hotel hotel=new Hotel();
                    hotel.setId(data.optString("id"));
                    hotel.setName_zh_cn(data.optString("name_zh_cn"));
                    hotel.setDistance(data.optString("distance"));
                    hotel.setImage_url(data.optString("image_url"));
                    list.add(hotel);
                }
                sight.setHotelList(list);
            }
            JSONArray tags =object.getJSONArray("attraction_trip_tags");
            if (tags!=null) {
                List<Tags> tagsList=new ArrayList<>();
                int length = tags.length();
                for (int i = 0; i < length; i++) {
                    JSONObject data=tags.getJSONObject(i);
                    Tags tag=new Tags();
                    tag.setId(data.optString("id"));
                    tag.setName(data.optString("name"));
                    tag.setDisplay_count(data.optString("display_count"));

                    //获取contents
                    JSONArray contents=data.getJSONArray("attraction_contents");
                    if (contents!=null){
                        List<Attraction_content> list=new ArrayList<>();
                        int length1 = contents.length();
                        for (int j = 0; j < length1; j++) {
                            JSONObject attr=contents.getJSONObject(j);
                            Attraction_content attraction_content=new Attraction_content();
                            attraction_content.setId(attr.optString("id"));
                            attraction_content.setDescription(attr.optString("description"));
                            attraction_content.setNode_comments_count(attr.optString("node_comments_count"));
                            attraction_content.setNode_id(attr.optString("node_id"));
                            attraction_content.setUpdated_at(attr.optString("updated_at"));

                            JSONArray notes=attr.optJSONArray("notes");
                            if (notes!=null){
                                List<Note> noteList=new ArrayList<>();
                                int length2 = notes.length();
                                for (int k = 0; k < length2; k++) {
                                    JSONObject jsonObject=notes.getJSONObject(k);
                                    Note note=new Note();
                                    note.setId(jsonObject.optString("id"));
                                    note.setDescription(jsonObject.optString("description"));
                                    note.setHeight(jsonObject.optString("height"));
                                    note.setPhoto_url(jsonObject.optString("photo_url"));
                                    note.setWidth(jsonObject.optString("width"));
                                    noteList.add(note);
                                }
                                attraction_content.setNotes(noteList);
                            }
                            list.add(attraction_content);
                        }
                        tag.setAttraction_contents(list);
                    }
                    tagsList.add(tag);
                }
                sight.setTagsList(tagsList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sight;
    }

    public static List<Place> getPlace(String json) {
        List<Place> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i < length; i++) {
                JSONObject object = array.getJSONObject(i);
                Place place = new Place();
                place.setAttraction_trips_count(object.getString("attraction_trips_count"));
                place.setId(object.getString("id"));
                place.setImage_url(object.getString("image_url"));
                place.setName(object.getString("name"));
                place.setUser_score(object.getString("user_score"));
                place.setDescription(object.getString("description"));
                place.setName_en(object.getString("name_en"));
                place.setAttraction_type(object.getString("attraction_type"));
                place.setDescription_summary(object.getString("description_summary"));
                list.add(place);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取不同国家的旅游景点口袋书
    public static List<Destination> getPocket(String json) {
        JSONArray array1 = null;
        List<Destination> destinationList = new ArrayList<>();
        try {
            array1 = new JSONArray(json);
            if (array1 != null) {
                int length1 = array1.length();
                for (int j = 0; j < length1; j++) {
                    Destination destination = new Destination();
                    JSONObject object = array1.getJSONObject(j);
                    destination.setName_en(object.getString("name_en"));
                    destination.setId(object.getString("id"));
                    destination.setImage_url(object.getString("image_url"));
                    destination.setName_zh_cn(object.getString("name_zh_cn"));
                    destination.setPoi_count(object.getString("poi_count"));
                    destination.setUpdated_at(object.getString("updated_at"));
                    destinationList.add(destination);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return destinationList;
    }


    //获取每个地区的景点
    public static List<Attractions> getAttractions(String category, String json) {
        List<Attractions> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i < length; i++) {
                Attractions attractions = new Attractions();
                JSONObject object = array.getJSONObject(i);
                attractions.setCategory(category);
                attractions.setAttraction_type(object.optString("attraction_type"));
                attractions.setId(object.optString("id"));
                attractions.setLat(object.optDouble("lat"));
                attractions.setLng(object.optDouble("lng"));
                attractions.setName(object.optString("name"));
                attractions.setName_en(object.optString("name_en"));
                attractions.setUser_score(object.optString("user_score"));
                list.add(attractions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取不同国家的景点展示
    public static List<Destinations> getDestinations(String json) {
        List<Destinations> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i < length; i++) {
                Destinations destinations = new Destinations();
                JSONObject jsonObject = array.getJSONObject(i);
                destinations.setCategory(jsonObject.getString("category"));

                JSONArray array1 = jsonObject.getJSONArray("destinations");
                List<Destination> destinationList = new ArrayList<>();
                if (array1 != null) {
                    int length1 = array1.length();
                    for (int j = 0; j < length1; j++) {
                        Destination destination = new Destination();
                        JSONObject object = array1.getJSONObject(j);
                        destination.setName_en(object.getString("name_en"));
                        destination.setLatLng(new LatLng(object.getDouble("lat"), object.getDouble("lng")));
                        destination.setId(object.getString("id"));
                        destination.setImage_url(object.getString("image_url"));
                        destination.setName_zh_cn(object.getString("name_zh_cn"));
                        destination.setPoi_count(object.getString("poi_count"));
                        destination.setUpdated_at(object.getString("updated_at"));
                        destinationList.add(destination);
                    }
                }
                destinations.setDistinations(destinationList);
                list.add(destinations);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
