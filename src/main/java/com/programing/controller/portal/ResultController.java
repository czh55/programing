package com.programing.controller.portal;

import com.programing.common.ServerResponse;
import com.programing.pojo.Result;
import com.programing.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/result/")
public class ResultController {

    @Autowired
    private IResultService iResultService;

    //这里的步骤是：根据competitionId去result表中去找结果
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<Result> getDetail(Integer competitionId){

        return iResultService.manageResultDetail(competitionId);
    }

}
