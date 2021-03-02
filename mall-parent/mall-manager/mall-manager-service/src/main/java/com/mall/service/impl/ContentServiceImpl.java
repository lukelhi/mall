package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.mapper.ContentMapper;
import com.mall.pojo.Content;
import com.mall.pojo.ContentExample;
import com.mall.pojo.EasyUIDataGridResult;
import com.mall.pojo.MallResult;
import com.mall.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Override
	public MallResult insertContent(Content content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		
		//插入数据
		contentMapper.insert(content);
		
		return MallResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(Integer page, Integer rows,Integer categoryId) {
		//分页
		PageHelper.startPage(page,rows);
		ContentExample example = new ContentExample();
		//设置查询条件
		ContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId.longValue());

		List<Content> contents = contentMapper.selectByExampleWithBLOBs(example);
		//分页信息
		PageInfo<Content> pageInfo = new PageInfo();
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(contents);
		return result;
	}


}