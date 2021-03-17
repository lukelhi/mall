package com.mall.controller;

import com.mall.pojo.PictureResult;
import com.mall.service.PictureServcie;
import com.mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController{
	
	@Autowired
	private PictureServcie pictureServcie;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		PictureResult result = pictureServcie.uploadPic(uploadFile);
		String json = JsonUtils.objectToJson(result);
		return json;
	}
	
}
