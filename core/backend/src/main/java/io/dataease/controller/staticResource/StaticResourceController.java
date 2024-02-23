package io.dataease.controller.staticResource;

import com.alibaba.fastjson.JSON;
import io.dataease.controller.request.resource.StaticResourceRequest;
import io.dataease.dto.panel.PanelImageLibraryDTO;
import io.dataease.service.staticResource.StaticResourceService;
import io.swagger.annotations.ApiOperation;
import org.pentaho.ui.xul.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: wangjiahao
 * Date: 2022/4/24
 * Description:
 */
@RestController
@RequestMapping("/staticResource")
public class StaticResourceController {

    @Resource
    StaticResourceService staticResourceService;

    @PostMapping("upload/{fileId}/{categoryType}")
    @ApiOperation("上传静态文件")
    public void upload(@PathVariable("fileId") String fileId, @RequestPart("file") MultipartFile file, @PathVariable("categoryType") String fileCategory) {
        staticResourceService.upload(fileId,file, fileCategory);
    }

    @PostMapping("findResourceAsBase64")
    @ApiOperation("查找静态文件并转为Base64")
    public Map<String,String> findResourceAsBase64(@RequestBody StaticResourceRequest resourceRequest){
        return staticResourceService.findResourceAsBase64(resourceRequest);
    }

    @PostMapping("findAllResourceAsBase64")
    @ApiOperation("查找指定目录下的所有静态文件并转为Base64")
    public Map<String,String> findAllResourceAsBase64(){
        return staticResourceService.findAllResourceAsBase64();
    }

    @PostMapping("findAllResourceName")
    @ApiOperation("查找指定目录下的所有静态文件名")
    public String[] findAllResourceName(){
        return staticResourceService.findAllResourceName();
    }
    @PostMapping("findResourceNameByCondition")
    @ApiOperation("根据分类查找指定目录下的文件名")
    public List<PanelImageLibraryDTO> findResourceNameByCondition(@RequestBody String condition){
        String categoryId = (String) JSON.parseObject(condition).get("categoryId");
        String searchName = (String) JSON.parseObject(condition).get("name");
        return staticResourceService.findResourceNameByCondition(categoryId, searchName);
    }

    @PostMapping("deleteImgByName")
    @ApiOperation("删除选中图片")
    public int deleteImgByName(@RequestBody String imgName){
        List<String> imgNames= JSON.parseArray(imgName, String.class);
        return staticResourceService.deleteImgByName(imgNames);
    }
}
