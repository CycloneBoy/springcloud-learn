-- 热门游记列表
CREATE TABLE `t_hot_travel_note_list`
(
    `id`                 int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `country_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地国家名称',
    `country_url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地国家链接',
    `city_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地城市名称',
    `city_url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地城市链接',
    `city_index`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地城市的编码',
    `total_page`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记总页数',
    `total_number`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT ' 游记总数',
    `image_total_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记图片总数',
    `crawl_status`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否已经爬取',
    `crawl_time`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '爬取时间',
    `create_datetime`    datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `city_name_en`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '热门目的地城市英文名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1503
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


-- 热门游记
CREATE TABLE `t_travel_hot_destination_note`
(
    `id`                         int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `resource_type`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记爬取来源',
    `travel_url`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记链接',
    `travel_name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记名称',
    `travel_type`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记分类 ： 宝藏 、 星级',
    `travel_summary`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '游记摘要',
    `travel_destination`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记目的地',
    `travel_destination_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记目的地国家',
    `travel_image_url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记封面图片链接',
    `author_id`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记作者ID',
    `author_url`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记作者首页链接',
    `author_name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记作者名称',
    `author_image_url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记作者图像链接',
    `travel_view_count`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记浏览总数',
    `travel_comment_count`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记评论总数',
    `travel_up_count`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记顶的总数',
    `travel_father_id`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记父亲ID',
    `travel_id`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '游记ID',
    `crawl_status`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否已经爬取',
    `crawl_time`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '爬取时间',
    `create_datetime`            datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3660523
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
