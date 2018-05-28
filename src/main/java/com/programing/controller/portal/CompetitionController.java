package com.programing.controller.portal;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.service.ICompetitionService;
import com.programing.vo.CompetitionDetailVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/competition/")
public class CompetitionController {

    @Autowired
    private ICompetitionService iCompetitionService;



    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<CompetitionDetailVo> detail(Integer competitionId){
        return iCompetitionService.getCompetitionDetail(competitionId);
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iCompetitionService.getCompetitionByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

}
