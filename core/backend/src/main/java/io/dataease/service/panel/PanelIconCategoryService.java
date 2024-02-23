package io.dataease.service.panel;

import io.dataease.commons.constants.CommonConstants;
import io.dataease.commons.utils.AuthUtils;
import io.dataease.commons.utils.BeanUtils;
import io.dataease.controller.request.panel.PanelTemplateRequest;
import io.dataease.dto.panel.PanelImageLibraryDTO;
import io.dataease.dto.panel.PanelTemplateDTO;
import io.dataease.exception.DataEaseException;
import io.dataease.ext.ExtPanelTemplateMapper;
import io.dataease.ext.PanelImageLibraryMapper;
import io.dataease.i18n.Translator;
import io.dataease.plugins.common.base.domain.PanelTemplate;
import io.dataease.plugins.common.base.domain.PanelTemplateExample;
import io.dataease.plugins.common.base.domain.PanelTemplateWithBLOBs;
import io.dataease.plugins.common.base.mapper.PanelTemplateMapper;
import io.dataease.service.staticResource.StaticResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.dataease.commons.constants.StaticResourceConstants.UPLOAD_URL_PREFIX;

/**
 * Author: wangjiahao
 * Date: 2021-03-05
 * Description:
 */
@Service
public class PanelIconCategoryService {


    @Resource
    private PanelImageLibraryMapper panelImageLibraryMapper;

    @Transactional
    public PanelImageLibraryDTO save(PanelImageLibraryDTO request) {
        if (StringUtils.isEmpty(request.getId())) {
            request.setId(UUID.randomUUID().toString());
            panelImageLibraryMapper.insertPanelImageLibrary(request);
        } else {
            panelImageLibraryMapper.updatePanelImageLibrary(request);
        }
        return request;
    }

    public void delete(String id) {
        Assert.notNull(id, "id cannot be null");
        panelImageLibraryMapper.deleteIconCategory(id);
    }

    public List<PanelImageLibraryDTO> findCategory() {
        return panelImageLibraryMapper.getPanelCategoryList();
    }
}
