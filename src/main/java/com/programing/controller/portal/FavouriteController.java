package com.programing.controller.portal;

import com.programing.common.Const;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.pojo.User;
import com.programing.service.IFavouriteService;
import com.programing.util.CookieUtil;
import com.programing.util.JsonUtil;
import com.programing.util.RedisShardedPoolUtil;
import com.programing.vo.FavouriteVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/favourite/")
public class FavouriteController {

    @Autowired
    private IFavouriteService iFavouriteService;



    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> list(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.list(user.getId());
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> add(HttpServletRequest httpServletRequest, Integer competitionId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.add(user.getId(),competitionId);
    }


    @RequestMapping("delete_competition.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> deleteCompetition(HttpServletRequest httpServletRequest, String competitionIds){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.deleteCompetition(user.getId(),competitionIds);
    }


    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> selectAll(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.selectOrUnSelect(user.getId(),null, Const.Favourite.CHECKED);
    }

    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> unSelectAll(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.selectOrUnSelect(user.getId(),null,Const.Favourite.UN_CHECKED);
    }



    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> select(HttpServletRequest httpServletRequest, Integer competitionId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.selectOrUnSelect(user.getId(),competitionId,Const.Favourite.CHECKED);
    }

    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<FavouriteVo> unSelect(HttpServletRequest httpServletRequest, Integer competitionId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iFavouriteService.selectOrUnSelect(user.getId(),competitionId,Const.Favourite.UN_CHECKED);
    }



    @RequestMapping("get_favourite_competition_count.do")
    @ResponseBody
    public ServerResponse<Integer> getFavouriteCompetitionCount(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createBySuccess(0);
        }
        return iFavouriteService.getFavouriteCompetitionCount(user.getId());
    }


    @RequestMapping("judgeSameSponsorId.do")
    @ResponseBody
    public ServerResponse judgeSameSponsorId(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        //userId是从后台之后获取的，不是传过来的
        int userId = user.getId();
        return iFavouriteService.judgeSameSponsorId(userId);
    }
}
