package com.mall.service;

import com.mall.pojo.Item;
import com.mall.pojo.MallResult;

public interface ItemEditService {
    // 商品下架
    public MallResult instockItem(String ids);

    // 商品上架
    public MallResult reshelfItem(String ids);

    // 商品删除
    public MallResult deleteItem(String ids);

    // 商品规格
    public MallResult ItemParamData(long id);

    // 商品描述
    public MallResult ItemDesc(long id);

    // 更新商品
    public MallResult UpdateItem(Item item, String desc);

}
