package io.dataease.dto.panel;



import io.dataease.dto.chart.ChartViewDTO;
import io.dataease.plugins.common.base.domain.PanelGroupWithBLOBs;
import io.dataease.plugins.common.model.ITreeBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class PanelIndicatorsDTO   implements ITreeBase<PanelIndicatorsDTO> {

    private String id;
    private String name;
    private String pid;
    private Integer level;
    private String nodeType;
    private String datasetTableId;
    private Integer sort;
    private String indicatorsNo;
    private String indicatorsExplain;
    private Long createTime;

    @ApiModelProperty("标签")
    private String label;
    @ApiModelProperty("是否叶子节点")
    private Boolean leaf;
    @ApiModelProperty("子节点")
    private List<PanelIndicatorsDTO> children;
    @ApiModelProperty("父级ID")
    private List<String> parents;

}
