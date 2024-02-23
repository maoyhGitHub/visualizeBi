package io.dataease.dto.dataset;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataSetStaticData implements Serializable {
    private String panelId;
    private String viewId;
    private String viewName;
    private String tableId;
    private String excelKeys;
    private String jsonDetails;
    private Integer status;
    private String newJsonDetails;
    private String excelNewData;
    private String updateTime;

}
