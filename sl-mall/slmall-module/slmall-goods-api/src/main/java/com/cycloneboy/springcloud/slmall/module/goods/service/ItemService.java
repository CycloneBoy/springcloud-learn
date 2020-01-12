package com.cycloneboy.springcloud.slmall.module.goods.service;


import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudService;
import com.cycloneboy.springcloud.slmall.common.dto.ItemDto;
import com.cycloneboy.springcloud.slmall.module.goods.entity.Item;

/**
 * @author Exrick
 * @date 2017/7/29
 */
public interface ItemService extends BaseXCloudService<Item, Long> {

  /**
   * 通过ID获取商品包含详情
   *
   * @param itemId
   * @return
   */
  ItemDto getItemById(Long itemId);

  /**
   * 添加商品
   *
   * @param itemDto
   * @return
   */
  Item addItem(ItemDto itemDto);

  /**
   * 修改商品状态
   *
   * @param id
   * @param state
   * @return
   */
  Item alertItemState(Long id, Integer state);

  /**
   * 彻底删除商品
   *
   * @param id
   * @return
   */
  int deleteItem(Long id);

}
