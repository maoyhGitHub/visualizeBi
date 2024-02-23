package io.dataease.controller.panel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.dataease.controller.handler.annotation.I18n;
import io.dataease.controller.request.panel.PanelTemplateRequest;
import io.dataease.dto.panel.PanelImageLibraryDTO;
import io.dataease.dto.panel.PanelTemplateDTO;
import io.dataease.plugins.common.base.domain.PanelTemplateWithBLOBs;
import io.dataease.service.panel.PanelIconCategoryService;
import io.dataease.service.panel.PanelTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Api(tags = "图标库：分类")
@ApiSupport(order = 170)
@RestController
@RequestMapping("iconCategory")
public class PanelIconCategoryController {

    @Resource
    private PanelIconCategoryService panelIconCategoryService;

    @ApiOperation("保存")
    @PostMapping("/save")
    public PanelImageLibraryDTO save(@RequestBody String request) {

        JSONObject result = JSON.parseObject(JSON.parseObject(request) .getString("data"));
        PanelImageLibraryDTO panelImageLibraryDTO = new PanelImageLibraryDTO();
        panelImageLibraryDTO.setNodeType(result.getString("nodeType"));
        panelImageLibraryDTO.setFileCategory(result.getString("fileCategory"));
        panelImageLibraryDTO.setId(result.getString("id"));
        return panelIconCategoryService.save(panelImageLibraryDTO);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        panelIconCategoryService.delete(id);
    }

    @ApiOperation("查询")
    @PostMapping("/findCategory")
    public List<PanelImageLibraryDTO> findCategory() {
        return panelIconCategoryService.findCategory();
    }


}
