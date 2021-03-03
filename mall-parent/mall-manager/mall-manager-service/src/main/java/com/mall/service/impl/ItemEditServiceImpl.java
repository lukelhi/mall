package com.mall.service.impl;

import com.mall.mapper.ItemDescMapper;
import com.mall.mapper.ItemMapper;
import com.mall.pojo.*;
import com.mall.service.ItemEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class ItemEditServiceImpl implements ItemEditService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;


    @Override
    public MallResult instockItem(String ids) {
        // 找到对应的商品
        String[] buff = ids.split(",");
        for (String id : buff) {
            // 修改商品信息
            Item selectByPrimaryKey = itemMapper.selectByPrimaryKey(Long.parseLong(id));
            selectByPrimaryKey.setStatus( (byte) 2);
            // 更改商品信息
            itemMapper.updateByPrimaryKey(selectByPrimaryKey);
        }
        return MallResult.ok();
    }

    @Override
    public MallResult reshelfItem(String ids) {
        // 找到对应的商品
        String[] buff = ids.split(",");
        for (String id : buff) {
            // 修改商品信息
            Item selectByPrimaryKey = itemMapper.selectByPrimaryKey(Long.parseLong(id));
            selectByPrimaryKey.setStatus( (byte)1 );
            // 更改商品信息
            itemMapper.updateByPrimaryKey(selectByPrimaryKey);
        }
        return MallResult.ok();
    }

    @Override
    public MallResult deleteItem(String ids) {
        // 找到对应的商品
        String[] buff = ids.split(",");
        for (String id : buff) {
            // 删除商品信息
            Item item = itemMapper.selectByPrimaryKey( Long.parseLong(id) );
            item.setStatus( (byte)3 );
            itemMapper.updateByPrimaryKey( item );
        }
        return MallResult.ok();
    }

    // paramData 信息保存属性
    @Override
    public MallResult ItemParamData(long id) {
        Item selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
        // 保存的信息返回结果
        Map<String, Long> result = new HashMap<String , Long>();
        // 信息封装
        if(selectByPrimaryKey != null) {
            result.put("paramData", selectByPrimaryKey.getPrice() );
        }else
            result.put("paramData", 0l );
        return MallResult.ok(  result );
    }

    // itemDesc 信息保存属性
    @Override
    public MallResult ItemDesc(long id) {
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        // 保存的信息返回结果
        Map<String, String> result = new HashMap<String , String>();
        // 信息封装
        if(itemDesc != null) {
            result.put("itemDesc", itemDesc.getItemDesc() );
        }else
            result.put("itemDesc", "暂无描述信息" );
        return MallResult.ok(  result );
    }

    @Override
    public MallResult UpdateItem(Item tbItem , String desc) {
        // 更新商品信息
        if(itemMapper.selectByPrimaryKey( tbItem.getId() ) == null) {
            throw new IllegalAccessError("商品 不存在");
        }
        // 修改商品最新更新状态
        tbItem.setUpdated(new Date());
        ItemExample tbItemExample = new ItemExample();
        ItemExample.Criteria createCriteria = tbItemExample.createCriteria();
        createCriteria.andIdEqualTo( tbItem.getId() );
        // 通过id属性（主键），将item插入数据库
        itemMapper.updateByExampleSelective( tbItem , tbItemExample );
        // 更新商品描述
        MallResult result = updateTbItemDesc( tbItem.getId() , desc );
        if (result == null ||  result.getStatus() != 200) {
            throw new IllegalAccessError("商品 描述条件不正确");
        }
        //商品参数也可以更改
        // 返回结果
        return MallResult.ok();
    }

    //更新商品详情
    public MallResult updateTbItemDesc(long itemDescId, String itemDesc) {
        // 查询是否存在，如果不存在直接插入数据
        ItemDesc tbItemDesc = new ItemDesc();
        if( itemDescMapper.selectByPrimaryKey( itemDescId ) == null) {
            // 添加信息
            tbItemDesc.setItemId(itemDescId);//插入itemId
            tbItemDesc.setItemDesc(itemDesc);
            tbItemDesc.setCreated(new Date());
            tbItemDesc.setUpdated(new Date());
            // 插入数据
            itemDescMapper.insert( tbItemDesc );
        }else {//如果原来存在，需要更新
            // 更新的数据
            tbItemDesc.setUpdated(new Date());
            tbItemDesc.setItemDesc(itemDesc);
            // 更新的条件
            ItemDescExample tbItemDescExample = new ItemDescExample();
            ItemDescExample.Criteria createCriteria = tbItemDescExample.createCriteria();
            createCriteria.andItemIdEqualTo( itemDescId );
            // 执行sql语句数据，通过主键更新
            itemDescMapper.updateByExampleSelective( tbItemDesc , tbItemDescExample );
        }
        return MallResult.ok();
    }
}
