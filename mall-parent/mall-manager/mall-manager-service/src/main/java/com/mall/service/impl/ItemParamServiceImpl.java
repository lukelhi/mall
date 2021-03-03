package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.mapper.ItemParamMapper;
import com.mall.pojo.EasyUIDataGridResult;
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
	public MallResult getItemByCid(Long cid) {
		//根据cid查询参数模板
		ItemParamExample example = new ItemParamExample();
		ItemParamExample.Criteria criteria = example.createCriteria();
		
		criteria.andItemCatIdEqualTo(cid);
		
		//执行查询,使用selectByExampleWithBLOBs()，查询到规格参数param_data
		List<ItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		
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

	@Override
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		//分页处理
		PageHelper.startPage(page, rows);

		ItemParamExample itemParamExample = new ItemParamExample();
		List<ItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);//获取data
		//取分页信息
		PageInfo<ItemParam> pageInfo = new PageInfo<>();

		///返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(itemParams);
		return result;
	}

	@Override
	public MallResult deleteItemParamById(String ids) {
		String[] buff = ids.split(",");
		for (String id : buff) {
			itemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		return MallResult.ok();
	}
}