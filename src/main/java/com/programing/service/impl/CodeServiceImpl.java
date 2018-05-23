package com.programing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.CodeMapper;
import com.programing.pojo.Code;
import com.programing.pojo.Product;
import com.programing.pojo.Result;
import com.programing.service.ICodeService;
import com.programing.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service("iCodeServiceImpl")
public class CodeServiceImpl implements ICodeService{
    @Autowired
    private CodeMapper codeMapper;

    @Override
    public ServerResponse saveOrUpdateCode(Code code) {
        if(code != null)
        {

            if(code.getId() != null){
                int rowCount = codeMapper.updateByPrimaryKey(code);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新code成功");
                }
                return ServerResponse.createBySuccess("更新code失败");
            }else{
                int rowCount = codeMapper.insert(code);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增code成功");
                }
                return ServerResponse.createBySuccess("新增code失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新code参数不正确");
    }

    @Override
    public ServerResponse<Code> getCodeDetail(Integer productId, Integer userId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Code code = codeMapper.selectByProductIdAndUserId(productId,userId);
        if(code == null){
            return ServerResponse.createByErrorMessage("没有查询到");
        }
        return ServerResponse.createBySuccess(code);
    }

    @Override
    public ServerResponse<PageInfo> getCodeListByProductId(Integer productId,int pageNum,int pageSize) {
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Code> codeList = codeMapper.selectListByProductId(productId);

        PageInfo pageResult = new PageInfo(codeList);
        pageResult.setList(codeList);
        return ServerResponse.createBySuccess(pageResult);
    }
}
