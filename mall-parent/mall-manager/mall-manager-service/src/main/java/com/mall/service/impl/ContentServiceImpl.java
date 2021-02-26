package com.mall.service.impl;

import com.mall.mapper.ContentMapper;
import com.mall.pojo.Content;
import com.mall.pojo.MallResult;
import com.mall.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
	
}