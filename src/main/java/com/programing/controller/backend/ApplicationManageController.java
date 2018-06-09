package com.programing.controller.backend;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.User;
import com.programing.service.IApplicationService;
import com.programing.service.IUserService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.RedisShardedPoolUtil;
import com.programing.vo.ApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manage/application")
public class ApplicationManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IApplicationService iApplicationService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> applicationList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();

        //全部通过拦截器验证是否登录以及权限
        return iApplicationService.manageList(pageNum,pageSize,sponsorId);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ApplicationVo> applicationDetail(HttpServletRequest httpServletRequest, Long applicationNo){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();
        //全部通过拦截器验证是否登录以及权限
        return iApplicationService.manageDetail(applicationNo,sponsorId);
    }



    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> applicationSearch(HttpServletRequest httpServletRequest, Long applicationNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();
        //全部通过拦截器验证是否登录以及权限
        return iApplicationService.manageSearch(applicationNo,sponsorId,pageNum,pageSize);
    }



}
