package com.mall.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.mapper.ItemMapper;
import com.mall.pojo.Item;
import com.mall.pojo.ItemExample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelper {
	
	private ApplicationContext applicationContext;
	
	@Before
	public void init() {
		//创建一个spring容器
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
	}

	@Test
	public void testPageHelper() throws Exception {
		//从spring容器中获得Mapper的代理对象
		ItemMapper itemMapper = applicationContext.getBean(ItemMapper.class);
		//设置分页信息
		PageHelper.startPage(1, 30);
		//执行查询
		List<Item> list = itemMapper.selectByExample(new ItemExample());
		//取查询结果
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println(total);
		System.out.println(list.size());
		for (Item Item : list) {
			System.out.println(Item.getTitle());
		}
	}
}
