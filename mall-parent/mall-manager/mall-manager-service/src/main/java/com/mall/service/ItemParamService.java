package com.mall.service;

import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;

public interface ItemParamService {
	MallResult getItemByCid(Long cid);//根据id查询参数模板
	MallResult insertItemParam(long cid, String paramData);
	EasyUIDataGridResult getItemParamList(Integer page, Integer rows);
	MallResult deleteItemParamById(String ids);
}
