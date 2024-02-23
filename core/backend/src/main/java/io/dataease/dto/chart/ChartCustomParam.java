package io.dataease.dto.chart;

import io.dataease.plugins.common.dto.chart.ChartFieldCustomFilterDTO;
import io.dataease.plugins.common.dto.chart.ChartViewFieldDTO;
import io.dataease.plugins.common.request.chart.ChartExtFilterRequest;
import io.dataease.plugins.common.request.permission.DataSetRowPermissionsTreeDTO;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

@Data
public class ChartCustomParam {

    private List<ChartViewFieldDTO> xAxis;

    private List<ChartViewFieldDTO> yAxis;
    private List<ChartFieldCustomFilterDTO> fieldCustomFilter;
    private List<DataSetRowPermissionsTreeDTO> rowPermissionsTree;
    private List<ChartExtFilterRequest> extFilterList;
}
