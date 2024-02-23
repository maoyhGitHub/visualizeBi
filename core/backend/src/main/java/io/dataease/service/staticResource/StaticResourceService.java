package io.dataease.service.staticResource;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.collection.CollectionUtil;
import com.google.gson.Gson;
import io.dataease.commons.exception.DEException;
import io.dataease.commons.utils.FileUtils;
import io.dataease.commons.utils.LogUtil;
import io.dataease.commons.utils.StaticResourceUtils;
import io.dataease.controller.request.resource.StaticResourceRequest;
import io.dataease.dto.panel.PanelImageLibraryDTO;
import io.dataease.exception.DataEaseException;
import io.dataease.service.panel.PanelImageLibraryService;
import org.apache.commons.lang3.StringUtils;
import org.pentaho.di.core.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Author: wangjiahao
 * Date: 2022/4/24
 * Description:
 */
@Service
public class StaticResourceService {

    @Resource
    private PanelImageLibraryService panelImageLibraryService;
    private final Path staticDir = Paths.get("/opt/dataease/data/static-resource/");

    public void upload(String fileId,MultipartFile file, String fileCategory) {
        // check if the path is valid (not outside staticDir)
        Assert.notNull(file, "Multipart file must not be null");
        try {
            String originName = file.getOriginalFilename();
            String postfix = originName.substring(originName.lastIndexOf("."));
            if (!isImage(file) && !".svg".equals(postfix)) {
                DEException.throwException("Multipart file must be image");
            }
            String newFileName = fileId + postfix;
            Path uploadPath = Paths.get(staticDir.toString(), newFileName);
            if (".svg".equals(postfix)) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(staticDir.toString(), newFileName);
                Files.write(path, bytes);
            }else {
                // create dir is absent
                FileUtils.createIfAbsent(Paths.get(staticDir.toString()));
                Files.createFile(uploadPath);
                file.transferTo(uploadPath);
            }
            //保存上传图片信息
            PanelImageLibraryDTO panelImageLibraryDTO = new PanelImageLibraryDTO();
            panelImageLibraryDTO.setId(UUIDUtil.getUUIDAsString());
            panelImageLibraryDTO.setFileOriginName(originName);
            panelImageLibraryDTO.setFileName(newFileName);
            panelImageLibraryDTO.setFilePath("/static-resource/" + newFileName);
            panelImageLibraryDTO.setNodeType("image");
            panelImageLibraryDTO.setFileCategory(fileCategory);
            panelImageLibraryService.insertPanelImageLibrary(panelImageLibraryDTO);
        } catch (IOException e) {
            LogUtil.error("文件上传失败", e);
            DataEaseException.throwException("文件上传失败");
        } catch (Exception e) {
            DataEaseException.throwException(e);
        }
    }

    private boolean isImage(MultipartFile file) {
        BufferedImage image = null;
        try (InputStream input = file.getInputStream()) {
            image = ImageIO.read(input);
        } catch (IOException e) {
            LogUtil.error(e.getMessage(), e);
            return false;
        }
        Pattern pattern = Pattern.compile("\\.(png|jpg|jpeg|gif)$");
        Matcher matcher = pattern.matcher(file.getOriginalFilename().toLowerCase());
        return image != null && image.getWidth() > 0 && image.getHeight() > 0 && matcher.find();
    }

    public void saveFilesToServe(String staticResource){
        Gson gson = new Gson();
        if (StringUtils.isNotEmpty(staticResource)) {
            Map<String, String> resource = gson.fromJson(staticResource, Map.class);
            for (Map.Entry<String, String> entry : resource.entrySet()) {
                String path = entry.getKey();
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                saveSingleFileToServe(fileName, entry.getValue());
            }
        }
    }

    public void saveSingleFileToServe(String fileName, String content) {
        Path uploadPath = Paths.get(staticDir.toString(), fileName);
        try {
            if (uploadPath.toFile().exists()) {
                LogUtil.info("file exists");
            } else {
                if (StringUtils.isNotEmpty(content)) {
                    Files.createFile(uploadPath);
                    FileCopyUtils.copy(Base64Decoder.decode(content), Files.newOutputStream(uploadPath));
                }
            }
        } catch (Exception e) {
            LogUtil.error("template static resource save error" + e.getMessage());
        }
    }

    public Map<String, String> findResourceAsBase64(StaticResourceRequest resourceRequest) {
        Map<String, String> result = new HashMap<>();
        if (CollectionUtil.isNotEmpty(resourceRequest.getResourcePathList())) {
            for (String path : resourceRequest.getResourcePathList()) {
                String value = StaticResourceUtils.getImgFileToBase64(path.substring(path.lastIndexOf("/") + 1));
                result.put(path, value);
            }
        }
        return  result;
    }

    public Map<String,String> findAllResourceAsBase64(){
        File file = new File("/opt/dataease/data/static-resource/");
        File[] files = file.listFiles();
        Map<String,String> result = new HashMap<>();
        for (File file1 : files) {
            String path = file1.toString();
            String value = StaticResourceUtils.getImgFileToBase64(path.substring(path.lastIndexOf("\\")+1,path.length()));
            result.put(path,value);
        }
        return  result;
    }

    public String[] findAllResourceName(){
        File file = new File("/opt/dataease/data/static-resource/");
        Map<String,String> result = new HashMap<>();
        String[] names = Arrays.stream(Objects.requireNonNull(file.listFiles())).sorted(Comparator.comparing(File::lastModified).reversed()).map(File::getName).toArray(String[]::new);
        return  names;
    }
    public List<PanelImageLibraryDTO> findResourceNameByCondition(String categoryId, String searchName){
        return  panelImageLibraryService.findResourceNameByCondition(categoryId, searchName);
    }

    @Transactional
    public int deleteImgByName(List<String> imgNames) {
        Path uploadPath = Paths.get(staticDir.toString());
        File fileDelete;
        int result = 0;
        try {
            for (String file : imgNames){
                String temp = new StringBuffer(uploadPath.toString()).append(File.separator).append(file).toString();
                fileDelete = new File(temp);
                fileDelete.delete();
                panelImageLibraryService.deleteImgByNames(file);
                result ++;
            }
        } catch (Exception e) {
            LogUtil.error("图片删除失败",e);
            DataEaseException.throwException("图片删除失败");
        }
        return result;
    }
}
