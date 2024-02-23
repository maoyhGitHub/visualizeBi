package io.dataease.plugins.config;

import io.dataease.commons.utils.LogUtil;
import io.dataease.plugins.common.base.domain.MyPlugin;
import io.dataease.plugins.common.request.KeywordRequest;
import io.dataease.service.sys.PluginService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PluginRunner implements ApplicationRunner {

    private static final List<String> discardModules = new ArrayList<>();

    @Value("${dataease.plugin.dir:/opt/dataease/plugins/}")
    private String pluginDir;

    @Autowired
    private PluginService pluginService;

    @PostConstruct
    public void init() {
        discardModules.add("dataease-extensions-tabs-backend");
    }


    @Override
    public void run(ApplicationArguments args) {
        // 执行加载插件逻辑
        pluginService.systemUpgrade();
        KeywordRequest request = new KeywordRequest();
        List<MyPlugin> plugins = pluginService.query(request);
        if (CollectionUtils.isEmpty(plugins)) return;
        Map<Boolean, List<MyPlugin>> groupMap = plugins.stream().collect(Collectors.groupingBy(this::isDiscard));
        if (ObjectUtils.isEmpty(groupMap)) return;
        List<MyPlugin> discardPlugins = groupMap.get(true);
        if (CollectionUtils.isNotEmpty(discardPlugins)) {
            try {

                for (int i = 0; i < discardPlugins.size(); i++) {
                    MyPlugin plugin = discardPlugins.get(i);
                    pluginService.uninstall(plugin.getPluginId());
                }
            } catch (Exception e) {
                LogUtil.error(e.getMessage(), e);
            }

        }

        if (CollectionUtils.isEmpty(groupMap.get(false))) return;

        plugins.stream().forEach(plugin -> {

            String store = plugin.getStore();
            String version = plugin.getVersion();
            String moduleName = plugin.getModuleName();
            String fileName = moduleName + "-" + version + ".jar";
            String path = pluginDir + store + "/" + fileName;

            File jarFile = new File(path);


            String jarPath = jarFile.getAbsolutePath();
            if (!StringUtils.equals("default", store) && !jarFile.exists()) {
                version = "1.0-SNAPSHOT";
                path = pluginDir + store + "/" + moduleName + "-" + version + ".jar";
                plugin.setVersion(version);
                jarFile = new File(path);
                jarPath = jarFile.getAbsolutePath();
            }
            try {
                if (jarFile.exists()) {
                    pluginService.loadJar(jarPath, plugin);
                } else {
                    LogUtil.error("插件路径不存在 {} ", jarPath);
                }
            } catch (Exception e) {
                LogUtil.error(e);
            }
        });

    }

    private boolean isDiscard(MyPlugin myPlugin) {
        return discardModules.contains(myPlugin.getModuleName());
    }

}
