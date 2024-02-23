package io.dataease.ext;
import io.dataease.dto.authModel.VAuthModelDTO;
import io.dataease.dto.panel.PanelIndicatorsDTO;

import java.util.List;

public interface PanelIndicatorsMapper {
    //查询树方法
    List<PanelIndicatorsDTO> findIndicatorsList();
    //查询指标列表方法
    List<PanelIndicatorsDTO> getTableList(String pids,String keyWord);
    //插入方法
    int insertIndicators(PanelIndicatorsDTO panelIndicatorsDTO);
    //更新方法
    int updateIndicators(PanelIndicatorsDTO panelIndicatorsDTO);
    //删除方法
    int deleteById(String id);
    //根据数据集id更新方法
    int updateIndicatorsByDatasetId(PanelIndicatorsDTO panelIndicatorsDTO);
    //查询已关联数据集的指标项
    List<VAuthModelDTO>  getIndicatorsList();

    //指标名称唯一性校验
    int checkName(String name,String id,String pid);
}
