package io.dataease.ext;

import io.dataease.dto.panel.PanelImageLibraryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PanelImageLibraryMapper {

    List<PanelImageLibraryDTO> findResourceNameByCondition(@Param("categoryId") String categoryId, @Param("searchName") String searchName);

    int insertPanelImageLibrary(PanelImageLibraryDTO panelImageLibraryDTO);

    int updatePanelImageLibrary(@Param("bean") PanelImageLibraryDTO panelImageLibraryDTO);

    List<PanelImageLibraryDTO> getPanelCategoryList();

    int deleteImgByNames(@Param("imgName")String imgName);

    int deleteIconCategory(@Param("id")String id);

}
