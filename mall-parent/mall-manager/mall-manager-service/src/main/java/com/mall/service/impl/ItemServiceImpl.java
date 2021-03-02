package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.mapper.ItemDescMapper;
import com.mall.mapper.ItemMapper;
import com.mall.mapper.ItemParamItemMapper;
import com.mall.pojo.*;
import com.mall.service.ItemService;
import com.mall.utils.IDUtils;
import com.mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;
	
	@Override
	public Item getItemById(long itemId) {
		
		Item item = itemMapper.selectByPrimaryKey(itemId);
		
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		ItemExample example = new ItemExample();
		List<Item> list = itemMapper.selectByExample(example);
		//取分页的信息
		//Item跟jsp中的field对应
		PageInfo<Item> pageInfo = new PageInfo<>(list);//将list作为参数获取pageInfo对象

		///返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		//将定义的pojo返回
		return result;
	}

	@Override
	public MallResult createItem(Item item, String desc, String itemParam) {
		//根据商品，商品描述，商品参数数据创建商品

		//生成商品id
		long itemId = IDUtils.genItemId();
		//补全IbItem属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		//创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);

		//System.out.println("service:"+item.getCid());
		//System.out.println("service:"+item.getId());

		//插入商品表
		itemMapper.insert(item);//调用dao进行操作

		//商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		//插入商品描述数据
	    itemDescMapper.insert(itemDesc);

	    //添加 商品参数数据
	    ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);

		//插入数据
		itemParamItemMapper.insert(itemParamItem);

		//生成静态页面
		//HttpClientUtil.doGet("http://localhost:8082/gen/item/"+itemId+".html");

		return MallResult.ok();
	}

	@Override
	public String getItemParamHtml(long itemId) {
		// 根据商品id查询规格参数

		ItemParamItemExample example = new ItemParamItemExample();
		ItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);//按条件查询到参数

		if(list == null || list.isEmpty()) {
			return "";//返回空串
		}
		//取规格参数
		ItemParamItem itemParamItem = list.get(0);
		//去规格参数
		String paramData = itemParamItem.getParamData();
		//转换成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData,Map.class);

		//遍历list生成list对象
		//遍历list生成html
				StringBuffer sb = new StringBuffer();
				//拼接html
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
				sb.append("	<ody>\n");
				for (Map map : mapList) {
					sb.append("		<tr>\n");
					sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
					sb.append("		</tr>\n");
					//取规格项
					List<Map>mapList2 = (List<Map>) map.get("params");
					for (Map map2 : mapList2) {
						sb.append("		<tr>\n");
						sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
						sb.append("			<td>"+map2.get("v")+"</td>\n");
						sb.append("		</tr>\n");
					}
				}
				sb.append("	</ody>\n");
				sb.append("</table>");

				return sb.toString();
	}


	@Override
	public MallResult getItemDescById(Long id) {
		ItemDescExample example = new ItemDescExample();
		ItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<ItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);

		//判断是否查询到结果
		if(list != null && list.size() > 0) {
			ItemDesc itemDesc = list.get(0);
			return MallResult.ok(itemDesc);
		}
		return MallResult.ok();
	}

}
