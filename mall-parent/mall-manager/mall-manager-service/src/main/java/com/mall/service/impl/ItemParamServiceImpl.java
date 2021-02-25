package com.mall.service.impl;

import com.mall.mapper.ItemParamMapper;
import com.mall.pojo.ItemParam;
import com.mall.pojo.ItemParamExample;
import com.mall.pojo.MallResult;
import com.mall.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private ItemParamMapper itemParamMapper;
	
	@Override
	public MallResult getItemByCid(long cid) {
		//根据cid查询参数模板
		ItemParamExample example = new ItemParamExample();
		ItemParamExample.Criteria criteria = example.createCriteria();
		
		criteria.andItemCatIdEqualTo(cid);
		
		//执行查询,使用selectByExampleWithBLOBs()，查询到规格参数param_data
		List<ItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);//
		
		//判断是否查询到结果
		if(list != null && list.size() > 0) {
			ItemParam itemParam = list.get(0);
			return MallResult.ok(itemParam);
		}
		return MallResult.ok();
	}

	@Override
	public MallResult insertItemParam(long cid, String paramData) {
		//创建一个规格参数模板pojo
		ItemParam itemParam = new ItemParam();
		
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		
		//插入记录
		itemParamMapper.insert(itemParam);
		
		return MallResult.ok();
	}
}