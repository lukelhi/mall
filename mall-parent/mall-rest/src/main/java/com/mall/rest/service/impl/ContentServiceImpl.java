package com.mall.rest.service.impl;

import com.mall.mapper.ContentMapper;
import com.mall.pojo.Content;
import com.mall.pojo.ContentExample;
import com.mall.pojo.MallResult;
import com.mall.rest.component.JedisClient;
import com.mall.rest.service.ContentService;
import com.mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

//内容查询服务
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;//单机版，集群版，取决于配置
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	@Override
	public List<Content> getContentList(long cid) {
		//添加缓存
		
		//查询数据库之前先查询缓存，如果有直接返回
		try {
			//从redis中取缓存数据
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");

			if(!StringUtils.isBlank(json)) {
				System.out.println("查询到商品内容缓存！");
				//将json转换为list
				List<Content> list = JsonUtils.jsonToList(json,Content.class);
				return list;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		//根据cid查询内容列表
		ContentExample example = new ContentExample();
		ContentExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<Content> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//返回结果之前，向缓存中添加数据

		try {
			//为了规范key可以使用hash
			//定义一个保存内容的key,hash中每个项就是cid
			//value是list,需要把list转换为json数据

			jedisClient.hset(REDIS_CONTENT_KEY,cid+"",JsonUtils.objectToJson(list));//添加缓存

		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public MallResult syncContent(long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return MallResult.ok();//删除缓存
	}
}
