package com.programing.controller.portal;


import com.google.common.collect.Lists;
import com.programing.common.ServerResponse;
import com.programing.pojo.Category;
import com.programing.service.ICategoryService;
import com.programing.vo.AllCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;


    @RequestMapping("get_category_for_index.do")
    @ResponseBody
    //这个方法是用于index.html 所有访问者都可以访问，不用判断权限
    public ServerResponse getALLCategory(HttpServletRequest httpServletRequest){
        //得到顶级的List<Category> parentId = 0
        List<Category> categoryList_0 = iCategoryService.getChildrenParallelCategory(0).getData();

        List<Category> categoryList_temp = Lists.newArrayList();

        AllCategoryVo allCategoryVo = new AllCategoryVo();

        categoryList_temp = iCategoryService.getChildrenParallelCategory(categoryList_0.get(0).getId()).getData();
        allCategoryVo.setCategoryList_1(categoryList_temp);

        categoryList_temp = iCategoryService.getChildrenParallelCategory(categoryList_0.get(1).getId()).getData();
        allCategoryVo.setCategoryList_2(categoryList_temp);

        categoryList_temp = iCategoryService.getChildrenParallelCategory(categoryList_0.get(2).getId()).getData();
        allCategoryVo.setCategoryList_3(categoryList_temp);

        categoryList_temp = iCategoryService.getChildrenParallelCategory(categoryList_0.get(3).getId()).getData();
        allCategoryVo.setCategoryList_4(categoryList_temp);

        categoryList_temp = iCategoryService.getChildrenParallelCategory(categoryList_0.get(4).getId()).getData();
        allCategoryVo.setCategoryList_5(categoryList_temp);


        return ServerResponse.createBySuccess(allCategoryVo);
    }
}
