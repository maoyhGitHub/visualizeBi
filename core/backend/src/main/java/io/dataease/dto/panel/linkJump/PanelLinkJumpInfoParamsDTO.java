package io.dataease.dto.panel.linkJump;

import lombok.Data;


@Data
public class PanelLinkJumpInfoParamsDTO {

    private String id;
    private String linkJumpInfoId;
    private String targetPanelId;
    private String isFullscreen;
    private String dialogWidth;
    private String dialogHeight;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkJumpInfoId() {
        return linkJumpInfoId;
    }

    public void setLinkJumpInfoId(String linkJumpInfoId) {
        this.linkJumpInfoId = linkJumpInfoId;
    }

    public String getTargetPanelId() {
        return targetPanelId;
    }

    public void setTargetPanelId(String targetPanelId) {
        this.targetPanelId = targetPanelId;
    }

    public String getIsFullscreen() {
        return isFullscreen;
    }

    public void setIsFullscreen(String isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    public String getDialogWidth() {
        return dialogWidth;
    }

    public void setDialogWidth(String dialogWidth) {
        this.dialogWidth = dialogWidth;
    }

    public String getDialogHeight() {
        return dialogHeight;
    }

    public void setDialogHeight(String dialogHeight) {
        this.dialogHeight = dialogHeight;
    }
}
