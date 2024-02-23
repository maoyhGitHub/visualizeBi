package io.dataease.service.panel;

import io.dataease.dto.panel.PanelImageLibraryDTO;
import io.dataease.ext.PanelImageLibraryMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PanelImageLibraryService {
    @Resource
    private PanelImageLibraryMapper panelImageLibraryMapper;

    public List<PanelImageLibraryDTO> findResourceNameByCondition(String categoryId, String searchName) {
        return panelImageLibraryMapper.findResourceNameByCondition(categoryId, searchName);
    }

    public int insertPanelImageLibrary(PanelImageLibraryDTO panelImageLibraryDTO){
        return panelImageLibraryMapper.insertPanelImageLibrary(panelImageLibraryDTO);
    }

    public int deleteImgByNames(String imgName) {
        return panelImageLibraryMapper.deleteImgByNames(imgName);
    }
}
