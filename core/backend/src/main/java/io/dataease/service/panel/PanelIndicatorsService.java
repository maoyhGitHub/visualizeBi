package io.dataease.service.panel;


import com.google.gson.Gson;

import io.dataease.commons.constants.*;
import io.dataease.commons.utils.*;

import io.dataease.dto.authModel.VAuthModelDTO;
import io.dataease.dto.panel.PanelIndicatorsDTO;

import io.dataease.ext.*;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import java.util.*;


/**
 * Author: wangjiahao
 * Date: 2021-03-05
 * Description:
 */
@Service
public class PanelIndicatorsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final Gson gson = new Gson();

    private final SysLogConstants.SOURCE_TYPE sourceType = SysLogConstants.SOURCE_TYPE.PANEL;


    @Resource
    private PanelIndicatorsMapper panelIndicatorsMapper;

    //查询树
    public List<PanelIndicatorsDTO> tree() {
        List<PanelIndicatorsDTO> panelIndicatorsDTOList = panelIndicatorsMapper.findIndicatorsList();
        return TreeUtils.mergeTree(panelIndicatorsDTOList, "-1");
    }
    //查询列表
    public List<PanelIndicatorsDTO>  getTableList(String pids,String keyWord){
      return   panelIndicatorsMapper.getTableList(pids,keyWord);
    }
    //保存方法
    @Transactional
    public PanelIndicatorsDTO save(PanelIndicatorsDTO indicatorsDTO) {
        if (StringUtils.isEmpty(indicatorsDTO.getId())) {
            indicatorsDTO.setId(UUID.randomUUID().toString());
            if(StringUtils.isEmpty(indicatorsDTO.getPid())){ //业务域数据
                indicatorsDTO.setPid("-1");
            }
            indicatorsDTO.setCreateTime(new Date().getTime());
            panelIndicatorsMapper.insertIndicators(indicatorsDTO);
        } else {
            panelIndicatorsMapper.updateIndicators(indicatorsDTO);
        }
        return indicatorsDTO;
    }
    //删除
    @Transactional
    public int deleteByIds(String ids){
        int count = 0;
        if(ids.length()>0){
         String arr[] =  ids.split(",");
         for(String id : arr){
             count  = panelIndicatorsMapper.deleteById(id);
         }

        }
    return count;
    }
    //查询指标数
    public List<VAuthModelDTO> getIndicatorsTree() {
        List<VAuthModelDTO> panelIndicatorsDTOList = panelIndicatorsMapper.getIndicatorsList();
        return TreeUtils.mergeTree(panelIndicatorsDTOList, "-1");
    }
    //指标唯一性校验
    public int  checkName(String name ,String id,String pid){
        return panelIndicatorsMapper.checkName(name,id,pid);
    }
}
