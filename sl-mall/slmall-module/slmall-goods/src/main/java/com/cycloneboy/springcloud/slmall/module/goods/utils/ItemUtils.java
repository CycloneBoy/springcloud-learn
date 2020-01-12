package com.cycloneboy.springcloud.slmall.module.goods.utils;

import com.cycloneboy.springcloud.slmall.common.dto.ItemDto;
import com.cycloneboy.springcloud.slmall.module.goods.entity.Item;

/**
 * Create by  sl on 2020-01-12 12:07
 */
public class ItemUtils {

  public static ItemDto item2ItemDto(Item item) {

    ItemDto itemDto = new ItemDto();

    itemDto.setTitle(item.getTitle());
    itemDto.setPrice(item.getPrice());
    itemDto.setCid(item.getCid());
    itemDto.setImage(item.getImage());
    itemDto.setSellPoint(item.getSellPoint());
    itemDto.setNum(item.getNum());

    if (item.getLimitNum() == null) {
      itemDto.setLimitNum(item.getNum());
    } else if (item.getLimitNum() < 0 && item.getNum() < 0) {
      itemDto.setLimitNum(10);
    } else {
      itemDto.setLimitNum(item.getLimitNum());
    }

    return itemDto;
  }

  public static Item ItemDto2TbItem(ItemDto itemDto) {

    Item item = new Item();

    item.setTitle(itemDto.getTitle());
    item.setPrice(itemDto.getPrice());
    item.setCid(itemDto.getCid());
    item.setImage(itemDto.getImage());
    item.setSellPoint(itemDto.getSellPoint());
    item.setNum(itemDto.getNum());
    if (itemDto.getLimitNum() == null || itemDto.getLimitNum() < 0) {
      item.setLimitNum(10);
    } else {
      item.setLimitNum(itemDto.getLimitNum());
    }

    return item;
  }
}
