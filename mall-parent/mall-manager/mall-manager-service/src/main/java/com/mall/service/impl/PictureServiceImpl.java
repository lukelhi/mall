package com.mall.service.impl;

import com.mall.pojo.PictureResult;
import com.mall.service.PictureServcie;
import com.mall.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class PictureServiceImpl implements PictureServcie {

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		// 判断图片是否为空
		if(picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		try {
			//去图片的扩展名
			String originalFileName = picFile.getOriginalFilename();
			//去扩展名,不要“.”
			String extName = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
			
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			
			String url = client.uploadFile(picFile.getBytes(),extName);
			//拼接图片服务器的地址
			url = IMAGE_SERVER_BASE_URL+url; 
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			result.setError(1);
			result.setMessage("图片上传失败");
			e.printStackTrace();
		}
		return result;
	}
}
