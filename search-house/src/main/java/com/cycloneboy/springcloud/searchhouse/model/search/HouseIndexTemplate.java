package com.cycloneboy.springcloud.searchhouse.model.search;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 索引结构模板
 */
@Data
@Document(indexName = "xunwu")
public class HouseIndexTemplate {

    private Long houseId;
    private String title;
    private int price;
    private int area;
    private Date createTime;
    private Date lastUpdateTime;
    private String cityEnName;
    private String regionEnName;
    private int direction;
    private int distanceToSubway;
    private String subwayLineName;
    private String subwayStationName;
    private String street;
    private String district;
    private String description;
    private String layoutDesc;
    private String traffic;
    private String roundService;
    private int rentWay;
    private List<String> tags;
    private List<HouseSuggest> suggest;
    private BaiduMapLocation location;
}
