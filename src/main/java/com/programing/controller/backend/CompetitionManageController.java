package com.programing.controller.backend;

import com.google.common.collect.Maps;
import com.programing.common.ServerResponse;
import com.programing.pojo.Competition;
import com.programing.pojo.User;
import com.programing.service.ICompetitionService;
import com.programing.service.IFileService;
import com.programing.service.IUserService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.PropertiesUtil;
import com.programing.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping("/manage/competition")
public class CompetitionManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICompetitionService iCompetitionService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse competitionSave(HttpServletRequest httpServletRequest, Competition competition){
        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        competition.setSponsorId(user.getId());
        return iCompetitionService.saveOrUpdateCompetition(competition);
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest httpServletRequest, Integer competitionId,Integer status){
        return iCompetitionService.setSaleStatus(competitionId,status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest httpServletRequest, Integer competitionId){
        return iCompetitionService.manageCompetitionDetail(competitionId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();

        return iCompetitionService.getCompetitionListBySponsorId(sponsorId, pageNum,pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse competitionSearch(HttpServletRequest httpServletRequest,String competitionName,Integer competitionId,Integer status,  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();

        return iCompetitionService.searchCompetition(competitionName,competitionId,status,sponsorId,pageNum,pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest,@RequestParam(value = "upload_file",required = false) MultipartFile file,HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }


    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        if(StringUtils.isBlank(targetFileName)){
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }

        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }





























}
