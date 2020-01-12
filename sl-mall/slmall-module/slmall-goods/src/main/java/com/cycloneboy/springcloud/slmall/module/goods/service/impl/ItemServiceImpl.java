package com.cycloneboy.springcloud.slmall.module.goods.service.impl;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudDao;
import com.cycloneboy.springcloud.slmall.common.constant.CommonConstant;
import com.cycloneboy.springcloud.slmall.common.dto.ItemDto;
import com.cycloneboy.springcloud.slmall.common.exception.SlMallException;
import com.cycloneboy.springcloud.slmall.common.exception.SlMallExceptionEnum;
import com.cycloneboy.springcloud.slmall.common.redis.JedisClient;
import com.cycloneboy.springcloud.slmall.common.utils.IDUtil;
import com.cycloneboy.springcloud.slmall.module.goods.dao.ItemCatDao;
import com.cycloneboy.springcloud.slmall.module.goods.dao.ItemDao;
import com.cycloneboy.springcloud.slmall.module.goods.dao.ItemDescDao;
import com.cycloneboy.springcloud.slmall.module.goods.entity.Item;
import com.cycloneboy.springcloud.slmall.module.goods.entity.ItemDesc;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemCatService;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemDescService;
import com.cycloneboy.springcloud.slmall.module.goods.service.ItemService;
import com.cycloneboy.springcloud.slmall.module.goods.utils.ItemUtils;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by  sl on 2020-01-12 10:45
 */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

  @Autowired
  private ItemDao itemDao;

  @Autowired
  private ItemCatDao itemCatDao;

  @Autowired
  private ItemDescDao itemDescDao;

  @Autowired
  private ItemCatService itemCatService;

  @Autowired
  private ItemDescService itemDescService;

  @Autowired
  private JedisClient jedisClient;

  @Override
  public BaseXCloudDao<Item, Long> getRepository() {
    return itemDao;
  }

  @Override
  public ItemDto getItemById(Long itemId) {

    Item item = getItem(itemId);
    ItemDto itemDto = ItemUtils.item2ItemDto(item);

    itemCatService.get(itemDto.getCid()).ifPresent(itemCat -> itemDto.setCname(itemCat.getName()));
    itemDescService.get(itemId).ifPresent(itemDesc -> itemDto.setDetail(itemDesc.getItemDesc()));

    return itemDto;
  }

  @NotNull
  private Item getItem(Long itemId) {
    Item item = get(itemId).orElse(null);
    if (item == null) {
      throw new SlMallException(SlMallExceptionEnum.PARAMETER_ERROR);
    }
    return item;
  }

  @Override
  public Item addItem(ItemDto itemDto) {
    long id = IDUtil.getRandomId();
    Item item = ItemUtils.ItemDto2TbItem(itemDto);
    item.setId(id);
    item.setStatus(CommonConstant.STATUS_NORMAL.byteValue());
    item.setCreated(new Date());
    item.setUpdated(item.getCreated());
    if (item.getImage().isEmpty()) {
      item.setImage(CommonConstant.USER_DEFAULT_AVATAR);
    }
    item = save(item);

    // 保存商品描述
    ItemDesc itemDesc = new ItemDesc();
    itemDesc.setItemId(item.getId());
    itemDesc.setItemDesc(itemDto.getDetail());
    itemDesc.setCreated(new Date());
    itemDesc.setUpdated(itemDesc.getCreated());

    itemDescService.save(itemDesc);

    return item;
  }

  @Override
  public Item alertItemState(Long id, Integer state) {
    Item item = getItem(id);
    item.setStatus(state.byteValue());
    item.setUpdated(new Date());

    return save(item);
  }

  @Override
  @Transactional
  public int deleteItem(Long id) {
    delete(id);
    itemDescService.delete(id);
    return 0;
  }
}
