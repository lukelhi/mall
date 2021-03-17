package com.mall.controller;

import com.mall.pojo.Item;
import com.mall.pojo.MallResult;
import com.mall.service.ItemEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rest")
public class ItemRestController {

    @Autowired
    private ItemEditService itemEditService;

    @RequestMapping("/page/item-edit")
    public String ItemEdit() {
        // 编辑单个商品的信息界面
        return "item-edit";
    }

    @RequestMapping(value = "/item/update" , method= RequestMethod.POST)
    @ResponseBody
    public MallResult ItemEdit(Item item , String desc) {
        // 编辑单个商品的信息界面
        MallResult result = null;
        try{
            result = itemEditService.UpdateItem( item , desc );
            return result;
        }catch(Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/item/query/item/desc/{id}")
    @ResponseBody
    public MallResult ItemDesc(@PathVariable long id) {
        // 加载商品描述
        MallResult result = itemEditService.ItemDesc( id );
        return result;
    }

    @RequestMapping(value = "/item/param/item/query/{id}")
    @ResponseBody
    public MallResult ItemParamData(@PathVariable long id) {
        // 加载商品规格
        MallResult result = itemEditService.ItemParamData(id);
        return result;
    }

    @RequestMapping(value = "/item/delete" , method = RequestMethod.POST)
    @ResponseBody
    public MallResult deleteItem(@RequestParam String ids) {
        // 商品删除
        MallResult result = itemEditService.deleteItem( ids );
        return result;
    }

    @RequestMapping(value = "/item/instock" , method = RequestMethod.POST)
    @ResponseBody
    public MallResult instockItem(@RequestParam String ids) {
        // 商品下架
        MallResult result = itemEditService.instockItem( ids );
        return result;
    }

    @RequestMapping(value = "/item/reshelf",method=RequestMethod.POST)
    @ResponseBody
    public MallResult reshelfItem(@RequestParam String ids) {
        // 商品上架
        MallResult result = itemEditService.reshelfItem( ids );
        return result;
    }
}