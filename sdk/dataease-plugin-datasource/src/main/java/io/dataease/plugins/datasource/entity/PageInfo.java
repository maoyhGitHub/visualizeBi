package io.dataease.plugins.datasource.entity;

import lombok.Data;

@Data
public class PageInfo {
    private Long goPage;
    private Long pageSize;
    private String dsVersion;
}
