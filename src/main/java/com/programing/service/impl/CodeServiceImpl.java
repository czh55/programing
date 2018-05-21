package com.programing.service.impl;

import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.CodeMapper;
import com.programing.pojo.Code;
import com.programing.pojo.Result;
import com.programing.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
