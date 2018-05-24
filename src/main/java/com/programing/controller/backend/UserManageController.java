package com.programing.controller.backend;

import com.programing.common.Const;
import com.programing.common.ServerResponse;
import com.programing.pojo.User;
import com.programing.service.IUserService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
//                session.setAttribute(Const.CURRENT_USER,user);

                //新增redis共享cookie，session的方式
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return iUserService.selectListByRole(null,pageNum,pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse userSearch(HttpServletRequest httpServletRequest,Integer role, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return iUserService.selectListByRole(role,pageNum,pageSize);
    }

}
