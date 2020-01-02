package com.cycloneboy.springcloud.searchhouse.model.search;

import lombok.Data;


@Data
class HouseSuggest {

    private String input;
    private int weight = 10; // 默认权重
}
