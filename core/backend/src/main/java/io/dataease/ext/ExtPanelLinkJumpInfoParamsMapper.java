package io.dataease.ext;


import io.dataease.dto.panel.linkJump.PanelLinkJumpInfoParamsDTO;
import org.apache.ibatis.annotations.Param;


public interface ExtPanelLinkJumpInfoParamsMapper {

    void insertJumpInfoParams(@Param("bean") PanelLinkJumpInfoParamsDTO panelLinkJumpInfoParamsDTO);

    PanelLinkJumpInfoParamsDTO queryWithId(@Param("linkJumpInfoId") String linkJumpInfoId);

    int deleteBylinkJumpInfoId(@Param("linkJumpInfoId")String linkJumpInfoId);
}
