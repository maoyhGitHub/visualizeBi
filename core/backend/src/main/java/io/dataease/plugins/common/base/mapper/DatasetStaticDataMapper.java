package io.dataease.plugins.common.base.mapper;

import io.dataease.dto.dataset.DataSetStaticData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DatasetStaticDataMapper {

    int insert(DataSetStaticData param);

    DataSetStaticData selectByTableId(@Param("tableId") String tableId,@Param("viewId") String viewId);

    int updateByTableId(DataSetStaticData param);

    List<DataSetStaticData> staticDataList(DataSetStaticData param);
}
