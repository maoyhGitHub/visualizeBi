package io.dataease.dto.datasource;

import io.dataease.plugins.datasource.entity.JdbcConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class HiveConfiguration extends JdbcConfiguration {

    private String driver = "org.apache.hive.jdbc.HiveDriver";
    private String extraParams = "";

    public String getJdbc() {
        if(StringUtils.isEmpty(extraParams.trim())){
            return "jdbc:hive2://HOSTNAME:PORT/DATABASE"
                    .replace("HOSTNAME", getHost().trim())
                    .replace("PORT", getPort().toString().trim())
                    .replace("DATABASE", getDataBase().trim());
        }else {
            return "jdbc:hive2://HOSTNAME:PORT/DATABASE;EXTRA_PARAMS"
                    .replace("HOSTNAME", getHost().trim())
                    .replace("PORT", getPort().toString().trim())
                    .replace("DATABASE", getDataBase().trim())
                    .replace("EXTRA_PARAMS", getExtraParams().trim());
        }
    }
}