package io.dataease.commons.constants;

/**
 * Author: wangjiahao
 * Date: 2021-03-22
 * Description:
 */
public class PanelConstants {

    public final static String COMPONENT_TYPE_VIEW = "view";

    public final static String COMPONENT_TYPE_PUBLIC = "public";

    public final static String TEMPLATE_TYPE_SYSTEM = "system";

    public final static String TEMPLATE_TYPE_SELF = "self";


    public final static String PANEL_NODE_TYPE_FOlDER = "folder";

    public final static String PANEL_NODE_TYPE_PANEL = "panel";

    public final static String OPT_TYPE_INSERT = "insert";

    public final static String OPT_TYPE_UPDATE = "update";

    public final static String PANEL_GATHER_DEFAULT_PANEL = "default_panel";

    public final static String PANEL_GATHER_PANEL_LIST = "panel_list";


    //新建仪表板来源
    public static final class NEW_PANEL_FROM {

        // 直接新建
        public static final String NEW = "new";

        // 内部模板新建
        public static final String NEW_INNER_TEMPLATE = "new_inner_template";

        // 外部模板新建
        public static final String NEW_OUTER_TEMPLATE = "new_outer_template";

        // 模板市场新建
        public static final String NEW_MARKET_TEMPLATE = "new_market_template";

    }

    //仪表板类型
    public static final class PANEL_TYPE {

        // 普通仪表板
        public static final String SELF = "self";

        // 默认仪表板
        public static final String SYSTEM = "system";

    }

    //仪表板查询来源
    public static final class QUERY_FROM {

        // 普通查询
        public static final String NORMAL = "normal";

        // 编辑查询
        public static final String EDIT = "edit";

    }


    //应用数据源来源
    public static final class APP_DATASOURCE_FROM {

        // 新建
        public static final String NEW = "new";

        // 复用
        public static final String HISTORY = "history";

    }

}
