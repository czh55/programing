package com.programing.dao;

import com.programing.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    //sponsor 这里要特殊处理admin,他的sponsorId = 0
    List<Product> selectListBySponsorId(@Param("sponsorId") Integer sponsorId);

    List<Product> selectByNameAndProductId(@Param("productName")String productName, @Param("productId") Integer productId);

    //sponsor 这里的sponsorId是必须条件，而name、productId、status是任选一个。
    //这里要特殊处理admin,他的sponsorId = 0
    List<Product> selectByNameAndProductIdAndStatusAndSponsorId(@Param("productName")String productName, @Param("productId") Integer productId, @Param("status") Integer status,@Param("sponsorId") Integer sponsorId);

    List<Product> selectByNameAndCategoryIds(@Param("productName")String productName,@Param("categoryIdList")List<Integer> categoryIdList);

    //这里一定要用Integer，因为int无法为NULL，考虑到很多商品已经删除的情况。
    Integer selectStockByProductId(Integer id);


}