package com.programing.controller.backend;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.User;
import com.programing.service.IOrderService;
import com.programing.service.IUserService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.RedisShardedPoolUtil;
import com.programing.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by geely
 */

@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();

        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageList(pageNum,pageSize,sponsorId);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest httpServletRequest, Long orderNo){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageDetail(orderNo,sponsorId);
    }



    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest httpServletRequest, Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageSearch(orderNo,sponsorId,pageNum,pageSize);
    }



    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest httpServletRequest, Long orderNo){

        //由于使用了拦截器，所以我们必须重新获得一次，不同的是这次不用验证了。
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        Integer sponsorId = user.getId();
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageSendGoods(orderNo,sponsorId);
    }








}
