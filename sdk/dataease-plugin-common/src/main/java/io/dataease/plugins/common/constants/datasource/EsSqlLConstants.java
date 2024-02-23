package io.dataease.plugins.common.constants.datasource;


import static io.dataease.plugins.common.constants.DatasourceTypes.es;

public class EsSqlLConstants extends SQLConstants {
    public static final String KEYWORD_TABLE = es.getKeywordPrefix() + "%s" + es.getKeywordSuffix();

    public static final String KEYWORD_FIX = "%s." + es.getKeywordPrefix() + "%s" + es.getKeywordSuffix();

    public static final String ALIAS_FIX = es.getAliasPrefix() + "%s" + es.getAliasSuffix();

    public static final String UNIX_TIMESTAMP = "UNIX_TIMESTAMP(%s)";

    public static final String DATETIME_FORMAT = "DATETIME_FORMAT(%s,'%s')";

    public static final String STR_TO_DATE = " DATETIME_FORMAT(DATETIME_PARSE(%s,'%s'),'%s') ";

    public static final String CAST = "CAST(%s AS %s)";

    public static final String ROUND = "ROUND(%s, %s)";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String WHERE_VALUE_NULL = "(NULL,'')";

    public static final String WHERE_VALUE_VALUE = "'%s'";

    public static final String AGG_COUNT = "COUNT(*)";

    public static final String AGG_FIELD = "%s(%s)";

    public static final String WHERE_BETWEEN = "'%s' AND '%s'";

    public static final String BRACKETS = "(%s)";

    public static final String NAME = "es";

    public static final String GROUP_CONCAT = "FIRST(%s)";


}