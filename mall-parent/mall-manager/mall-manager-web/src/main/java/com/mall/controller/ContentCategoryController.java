package com.mall.controller;

import com.mall.pojo.EasyUITreeNode;
import com.mall.pojo.MallResult;
import com.mall.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {//给默认的值
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public MallResult createNode(Long parentId, String name) {
	MallResult result = contentCategoryService.insertCategory(parentId, name);
	return result;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public MallResult deleteContentCategory(Long id){
		MallResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody
	public MallResult update(Long id, String name) {
		MallResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
}
