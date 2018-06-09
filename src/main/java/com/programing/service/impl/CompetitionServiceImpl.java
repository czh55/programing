package com.programing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.programing.common.Const;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.CategoryMapper;
import com.programing.dao.CompetitionMapper;
import com.programing.pojo.Category;
import com.programing.pojo.Competition;
import com.programing.service.ICategoryService;
import com.programing.service.ICompetitionService;
import com.programing.util.DateTimeUtil;
import com.programing.util.PropertiesUtil;
import com.programing.vo.CompetitionDetailVo;
import com.programing.vo.CompetitionListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iCompetitionService")
public class CompetitionServiceImpl implements ICompetitionService {


    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateCompetition(Competition competition){
        if(competition != null)
        {
            if(StringUtils.isNotBlank(competition.getSubImages())){
                String[] subImageArray = competition.getSubImages().split(",");
                if(subImageArray.length > 0){
                    competition.setMainImage(subImageArray[0]);
                }
            }

            if(competition.getId() != null){
                int rowCount = competitionMapper.updateByPrimaryKey(competition);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createBySuccess("更新产品失败");
            }else{
                int rowCount = competitionMapper.insert(competition);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createBySuccess("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }


    public ServerResponse<String> setSaleStatus(Integer competitionId,Integer status){
        if(competitionId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setStatus(status);
        int rowCount = competitionMapper.updateByPrimaryKeySelective(competition);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }


    public ServerResponse<CompetitionDetailVo> manageCompetitionDetail(Integer competitionId){
        if(competitionId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Competition competition = competitionMapper.selectByPrimaryKey(competitionId);
        if(competition == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        CompetitionDetailVo competitionDetailVo = assembleCompetitionDetailVo(competition);
        return ServerResponse.createBySuccess(competitionDetailVo);
    }

    private CompetitionDetailVo assembleCompetitionDetailVo(Competition competition){
        CompetitionDetailVo competitionDetailVo = new CompetitionDetailVo();
        competitionDetailVo.setId(competition.getId());
        competitionDetailVo.setSubtitle(competition.getSubtitle());
        competitionDetailVo.setPrice(competition.getPrice());
        competitionDetailVo.setMainImage(competition.getMainImage());
        competitionDetailVo.setSubImages(competition.getSubImages());
        competitionDetailVo.setCategoryId(competition.getCategoryId());
        competitionDetailVo.setDetail(competition.getDetail());
        competitionDetailVo.setName(competition.getName());
        competitionDetailVo.setStatus(competition.getStatus());
        competitionDetailVo.setStock(competition.getStock());

        competitionDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(competition.getCategoryId());
        if(category == null){
            competitionDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            competitionDetailVo.setParentCategoryId(category.getParentId());
        }

        competitionDetailVo.setCreateTime(DateTimeUtil.dateToStr(competition.getCreateTime()));
        competitionDetailVo.setUpdateTime(DateTimeUtil.dateToStr(competition.getUpdateTime()));
        return competitionDetailVo;
    }



    public ServerResponse<PageInfo> getCompetitionList(int pageNum,int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Competition> competitionList = competitionMapper.selectList();

        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competitionItem : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competitionItem);
            competitionListVoList.add(competitionListVo);
        }
        PageInfo pageResult = new PageInfo(competitionList);
        pageResult.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    //sponsor
    public ServerResponse<PageInfo> getCompetitionListBySponsorId(Integer sponsorId, int pageNum,int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Competition> competitionList = competitionMapper.selectListBySponsorId(sponsorId);

        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competitionItem : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competitionItem);
            competitionListVoList.add(competitionListVo);
        }
        PageInfo pageResult = new PageInfo(competitionList);
        pageResult.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


    private CompetitionListVo assembleCompetitionListVo(Competition competition){
        CompetitionListVo competitionListVo = new CompetitionListVo();
        competitionListVo.setId(competition.getId());
        competitionListVo.setName(competition.getName());
        competitionListVo.setCategoryId(competition.getCategoryId());
        competitionListVo.setSponsorId(competition.getSponsorId());//sponsor
        competitionListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        competitionListVo.setMainImage(competition.getMainImage());
        competitionListVo.setPrice(competition.getPrice());
        competitionListVo.setSubtitle(competition.getSubtitle());
        competitionListVo.setStatus(competition.getStatus());
        return competitionListVo;
    }



    public ServerResponse<PageInfo> searchCompetition(String competitionName,Integer competitionId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(competitionName)){
            competitionName = new StringBuilder().append("%").append(competitionName).append("%").toString();
        }
        List<Competition> competitionList = competitionMapper.selectByNameAndCompetitionId(competitionName,competitionId);
        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competitionItem : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competitionItem);
            competitionListVoList.add(competitionListVo);
        }
        PageInfo pageResult = new PageInfo(competitionList);
        pageResult.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    //sponsor
    public ServerResponse<PageInfo> searchCompetition(String competitionName,Integer competitionId, Integer status, Integer sponsorId, int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(competitionName)){
            competitionName = new StringBuilder().append("%").append(competitionName).append("%").toString();
        }
        List<Competition> competitionList = competitionMapper.selectByNameAndCompetitionIdAndStatusAndSponsorId(competitionName,competitionId,status,sponsorId);
        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competitionItem : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competitionItem);
            competitionListVoList.add(competitionListVo);
        }
        PageInfo pageResult = new PageInfo(competitionList);
        pageResult.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


    public ServerResponse<CompetitionDetailVo> getCompetitionDetail(Integer competitionId){
        if(competitionId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Competition competition = competitionMapper.selectByPrimaryKey(competitionId);
        if(competition == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if(competition.getStatus() != Const.CompetitionStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        CompetitionDetailVo competitionDetailVo = assembleCompetitionDetailVo(competition);
        return ServerResponse.createBySuccess(competitionDetailVo);
    }


    public ServerResponse<PageInfo> getCompetitionByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();

        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                PageHelper.startPage(pageNum,pageSize);
                List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(competitionListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.CompetitionListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Competition> competitionList = competitionMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);

        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competition : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competition);
            competitionListVoList.add(competitionListVo);
        }

        PageInfo pageInfo = new PageInfo(competitionList);
        pageInfo.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    public ServerResponse<PageInfo> getCompetitionWithResult(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Competition> competitionList = competitionMapper.selectWithResult();

        List<CompetitionListVo> competitionListVoList = Lists.newArrayList();
        for(Competition competition : competitionList){
            CompetitionListVo competitionListVo = assembleCompetitionListVo(competition);
            competitionListVoList.add(competitionListVo);
        }

        PageInfo pageInfo = new PageInfo(competitionList);
        pageInfo.setList(competitionListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<Integer> getSponsorIdByCompetitionId(Integer competitionId) {
        //调用competitionMapper去查询sponsorId
        Competition competition = competitionMapper.selectByPrimaryKey(competitionId);

        int sponsorId = competition.getSponsorId();

        return ServerResponse.createBySuccess(sponsorId);
    }
}
