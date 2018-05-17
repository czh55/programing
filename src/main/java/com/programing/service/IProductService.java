package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Product;
import com.programing.vo.ProductDetailVo;

/**
 * Created by geely
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    //sponsor
    ServerResponse<PageInfo> getProductListBySponsorId(Integer sponsorid, int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    //sponsor
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId, Integer status, Integer sponsorId,int pageNum,int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);

    ServerResponse<Integer> getSponsorIdByProductId(Integer productId);

}
