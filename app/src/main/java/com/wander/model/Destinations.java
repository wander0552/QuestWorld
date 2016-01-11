package com.wander.model;

import com.lidroid.xutils.db.annotation.Table;

import java.util.List;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */

@Table(name = "destinations",execAfterTableCreated = "CREATE UNIQUE INDEX DESTINATION_INDEX ON destinations(category)")
public class Destinations extends EntityBase{
    private String category;
    private List<Destination> distinations;
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Destination> getDistinations() {
        return distinations;
    }

    public void setDistinations(List<Destination> distinations) {
        this.distinations = distinations;
    }

    @Override
    public String toString() {
        return "Destinations{" +
                "category='" + category + '\'' +
                ", distinations=" + distinations +
                '}';
    }
}
