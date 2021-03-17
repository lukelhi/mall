package com.mall.service;

import com.mall.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;


public interface PictureServcie {
	PictureResult uploadPic(MultipartFile picFile);
}
