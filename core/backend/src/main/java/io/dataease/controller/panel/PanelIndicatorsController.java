package io.dataease.controller.panel;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.dataease.auth.service.impl.ExtAuthServiceImpl;
import io.dataease.dto.authModel.VAuthModelDTO;
import io.dataease.dto.panel.PanelIndicatorsDTO;
import io.dataease.service.panel.PanelIndicatorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Author: wangjiahao
 * Date: 2021-03-05
 * Description:
 */
@Api(tags = "资源库：指标库")
@ApiSupport(order = 150)
@RestController
@RequestMapping("panel/indicators")
public class PanelIndicatorsController {

    @Resource
    private PanelIndicatorsService panelIndicatorsService;
    @Resource
    private ExtAuthServiceImpl authService;

    @ApiOperation("查询树")
    @PostMapping("/tree")
    public List<PanelIndicatorsDTO> tree() {
        return panelIndicatorsService.tree();
    }

    @ApiOperation("查询指标列表")
    @PostMapping("/getTableList")
    public List<PanelIndicatorsDTO> getTableList(@RequestBody Map<String,String> map){
        String pids = map.get("pids");
        String keyWord = map.get("name");
        List<PanelIndicatorsDTO> list =new ArrayList<>();
        if(StringUtils.isNotEmpty(pids)){
            list =  panelIndicatorsService.getTableList(pids,keyWord);
        }

        return list;
    }
    @ApiOperation("保存")
    @PostMapping("/save")
    public PanelIndicatorsDTO save(@RequestBody JSONObject request) throws Exception {
        PanelIndicatorsDTO indicatorsDTO =  new PanelIndicatorsDTO();
        if(null != request.get("id")){
            indicatorsDTO.setId(request.get("id").toString());
        }
        if(StringUtils.isNotEmpty(request.get("level").toString())){
            indicatorsDTO.setLevel(Integer.valueOf(request.get("level").toString()));
        }
        indicatorsDTO.setName(request.get("name").toString());//名称
        if(StringUtils.isNotEmpty(request.get("sort").toString())){ //添加指标项该字段可不填
            indicatorsDTO.setSort(Integer.valueOf(request.get("sort").toString())); //排序
        }else {
            indicatorsDTO.setSort(0);
        }
        indicatorsDTO.setPid(request.get("pid").toString());
        indicatorsDTO.setIndicatorsNo(request.get("indicatorsNo").toString());
        if(null != request.get("indicatorsExplain")){
            indicatorsDTO.setIndicatorsExplain(request.get("indicatorsExplain").toString());
        }
        indicatorsDTO.setNodeType(request.get("nodeType").toString());
        if(null != request.get("datasetTableId")){
            indicatorsDTO.setDatasetTableId(request.get("datasetTableId").toString());
        }
        return panelIndicatorsService.save(indicatorsDTO);
    }

    @ApiOperation("删除")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody Map<String,String> map) {
        String ids= map.get("ids");
        return panelIndicatorsService.deleteByIds(ids);
    }
    /***
     * 数据集模块使用
     *
     * ***/
    @ApiOperation("查询树")
    @PostMapping("/getIndicatorsTree")
    public List<VAuthModelDTO> getIndicatorsTree() {
        return panelIndicatorsService.getIndicatorsTree();
    }

    //指标名称唯一性校验
    @ApiOperation("指标名称唯一性校验")
    @PostMapping("/checkName")
    public  int checkName(@RequestBody Map<String,String> map){
        String name = map.get("name");
        String id = map.get("id");
        String pid = map.get("pid");
        return panelIndicatorsService.checkName(name,id,pid);//指标名称 指标ID 指标父级ID
    }
}
