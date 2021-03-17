package com.mall.portal.pojo;


import com.mall.pojo.Item;

public class PortalItem extends Item {

	public String[] getImages() {
		String images = this.getImage();
		if(images != null && !images.equals("")) {
			String[] imgs = images.split(",");
			return imgs;
		}
		return null;
	}
}
