package com.programing.controller.backend;

import com.programing.common.ServerResponse;
import com.programing.service.ICategoryService;
import com.programing.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.addCategory(categoryName,parentId);
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName){
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }


    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }








}
