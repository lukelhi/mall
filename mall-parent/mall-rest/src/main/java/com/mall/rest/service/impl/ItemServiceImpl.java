package com.mall.rest.service.impl;

import com.mall.mapper.ItemDescMapper;
import com.mall.mapper.ItemMapper;
import com.mall.mapper.ItemParamItemMapper;
import com.mall.pojo.Item;
import com.mall.pojo.ItemDesc;
import com.mall.pojo.ItemParamItem;
import com.mall.pojo.ItemParamItemExample;
import com.mall.rest.component.JedisClient;
import com.mall.rest.service.ItemService;
import com.mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;

    @Override
    public Item getItemById(Long itemId) {

        // 查询缓存，如果有缓存，直接返回
        try {

            System.out.println("查询到商品基本信息缓存");

            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASE_INFO_KEY);
            if (StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToPojo(json, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 查询数据库
        Item item = itemMapper.selectByPrimaryKey(itemId);

        // 向redis中添加缓存，有效期为1天
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASE_INFO_KEY, JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASE_INFO_KEY, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public ItemDesc getItemDescById(Long itemId) {
        // 查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY);
            if (StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToPojo(json, ItemDesc.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 查询数据库
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        // 向redis中添加缓存，有效期为1天
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY, JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemDesc;
    }


    @Override
    public ItemParamItem getItemParamById(Long itemId) {
        //添加缓存逻辑
        //查询缓存
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成java对象
                ItemParamItem itemParamitem = JsonUtils.jsonToPojo(json, ItemParamItem.class);
                return itemParamitem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据商品id查询规格参数
        ItemParamItemExample example = new ItemParamItemExample();

        //
        ItemParamItemExample.Criteria criteria = example.createCriteria();

        criteria.andItemIdEqualTo(itemId);
        List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);

        //取规格参数
        if (list != null && list.size() > 0) {
            ItemParamItem itemParamItem = list.get(0);
            //添加缓存
            try {
                //向redis中添加缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY
                        , JsonUtils.objectToJson(itemParamItem));
                //设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY, ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }
}
