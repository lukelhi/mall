package com.mall.portal.service.impl;

import com.mall.pojo.Item;
import com.mall.pojo.ItemDesc;
import com.mall.pojo.ItemParamItem;
import com.mall.pojo.MallResult;
import com.mall.portal.pojo.PortalItem;
import com.mall.portal.service.ItemService;
import com.mall.utils.HttpClientUtil;
import com.mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	
	
	@Override
	public Item getItemById(Long itemId) {
		// 根据商品id查询商品基本信息
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
		//转换成java对象
		MallResult mallResult = MallResult.formatToPojo(json, PortalItem.class);
		//取商品对象
		Item item = (Item) mallResult.getData();
		return item;
	}

	
	@Override
	public String getItemDescById(Long itemId) {
		//根据商品id调用Mall-rest的服务获得数据
		//http://localhost:8081/rest /item/desc/ 144766336139977
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
		//转换成java对象
		MallResult mallResult = MallResult.formatToPojo(json, ItemDesc.class);
		//没有获取到商品详情
		if(mallResult.getData() == null){
			return null;
		}
		//取商品描述
		ItemDesc itemDesc = (ItemDesc) mallResult.getData();
		String desc = itemDesc.getItemDesc();
		return desc;
	}
	
	
	@Override
	public String getItemParamById(Long itemId) {
		// 根据商品id获得对应的规格参数
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
		// 转换成java对象
		MallResult mallResult = MallResult.formatToPojo(json, ItemParamItem.class);

		//没有获取到商品规格参数
		if(mallResult.getData() == null){
			return null;
		}
		// 取规格参数
		ItemParamItem itemParamItem = (ItemParamItem) mallResult.getData();


		String paramJson = itemParamItem.getParamData();
		// 把规格参数的json数据转换成java对象
		// 转换成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
		// 遍历list生成html
		StringBuffer sb = new StringBuffer();

		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();

	}


}
