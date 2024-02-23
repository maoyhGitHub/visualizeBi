package io.dataease.service.panel;

import com.google.gson.Gson;
import io.dataease.dto.chart.ChartViewDTO;
import io.dataease.exception.DataEaseException;
import io.dataease.plugins.common.base.domain.PanelGroupExtendData;
import io.dataease.plugins.common.base.domain.PanelGroupExtendDataExample;
import io.dataease.plugins.common.base.mapper.PanelGroupExtendDataMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: wangjiahao
 * Date: 2022/3/15
 * Description:
 */
@Service
public class PanelGroupExtendDataService {

    private final static String transDataKey = "data" + "s";

    @Resource
    private PanelGroupExtendDataMapper panelGroupExtendDataMapper;

    public ChartViewDTO getChartDataInfo(String viewId, ChartViewDTO view) {
        Gson gson = new Gson();
        PanelGroupExtendDataExample extendDataExample = new PanelGroupExtendDataExample();
        extendDataExample.createCriteria().andViewIdEqualTo(viewId);
        List<PanelGroupExtendData> extendDataList = panelGroupExtendDataMapper.selectByExampleWithBLOBs(extendDataExample);
        if (CollectionUtils.isNotEmpty(extendDataList)) {
            ChartViewDTO chartViewTemplate = gson.fromJson(extendDataList.get(0).getViewDetails(), ChartViewDTO.class);
            Map<String, Object> dataInfo = chartViewTemplate.getData();
            if (dataInfo != null && dataInfo.get(transDataKey) != null) {
                dataInfo.put("data", dataInfo.get(transDataKey));
                dataInfo.remove(transDataKey);
            }
            view.setData(chartViewTemplate.getData());
        } else {
            DataEaseException.throwException("模板缓存数据中未获取指定视图数据：" + viewId);
        }
        return view;
    }


}
