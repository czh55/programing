package com.programing.controller.portal;


import com.google.common.collect.Maps;
import com.programing.common.ServerResponse;
import com.programing.pojo.Code;
import com.programing.pojo.User;
import com.programing.service.ICodeService;
import com.programing.service.IFileService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.PropertiesUtil;
import com.programing.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private ICodeService iCodeService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse codeSave(HttpServletRequest httpServletRequest, Code code) {

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        code.setUserId(user.getId());
        //productId,title,src是从前台传来，其他的userId是服务器端处理出来的,id的存在与否，区分两种情况
        return iCodeService.saveOrUpdateCode(code);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getCodeDetail(HttpServletRequest httpServletRequest, Integer productId, Integer userId) {
        return iCodeService.getCodeDetail(productId,userId);
    }


    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }
}
