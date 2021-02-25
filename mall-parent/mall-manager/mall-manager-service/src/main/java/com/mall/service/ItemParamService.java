package com.mall.service;

import com.mall.pojo.MallResult;

public interface ItemParamService {
	MallResult getItemByCid(long cid);
	MallResult insertItemParam(long cid, String paramData);
}
