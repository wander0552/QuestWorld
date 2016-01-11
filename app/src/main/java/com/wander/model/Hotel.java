package com.wander.model;

/**
 * Created by wander on 2015/5/23.
 * time 22:30
 * Email 18955260352@163.com
 */

/*            "id": 110371,
            "name_zh_cn": "Richmond hotel Asakusa",
            "distance": 0.226890316730001,
            "image_url": "http://m.chanyouji.cn/hotels/110371.jpg"
*/
public class Hotel {
    private String id;
    private String name_zh_cn;
    private String distance;
    private String image_url;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
