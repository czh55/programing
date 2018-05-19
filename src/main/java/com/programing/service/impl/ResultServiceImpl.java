package com.programing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.ProductMapper;
import com.programing.dao.ResultMapper;
import com.programing.pojo.Product;
import com.programing.pojo.Result;
import com.programing.service.IResultService;
import com.programing.vo.ProductDetailVo;
import com.programing.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iResultService")
public class ResultServiceImpl implements IResultService {

    @Autowired
    private ResultMapper resultMapper;

    @Override
    public ServerResponse saveOrUpdateResult(Result result) {
        if(result != null)
        {

            if(result.getId() != null){
                int rowCount = resultMapper.updateByPrimaryKey(result);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新结果成功");
                }
                return ServerResponse.createBySuccess("更新结果失败");
            }else{
                int rowCount = resultMapper.insert(result);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增结果成功");
                }
                return ServerResponse.createBySuccess("新增结果失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新结果参数不正确");
    }

    @Override
    public ServerResponse<Result> manageResultDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Result result = resultMapper.selectByProductId(productId);
        if(result == null){
            return ServerResponse.createByErrorMessage("没有查询到");
        }
        return ServerResponse.createBySuccess(result);
    }


}
