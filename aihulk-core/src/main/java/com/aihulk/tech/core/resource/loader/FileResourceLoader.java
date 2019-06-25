package com.aihulk.tech.core.resource.loader;

import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.resource.entity.Resource;
import com.aihulk.tech.core.resource.parser.DefaultResourceParser;
import com.aihulk.tech.core.resource.parser.ResourceParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FileResourceLoader
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 13:27
 * @Version 1.0
 */
public class FileResourceLoader implements ResourceLoader {

    private ResourceParser<String> resourceParser = new DefaultResourceParser();

    @Override
    public Resource loadResource(String version) {
        String path = RuleEngineConfig.getResourceFilePath();
        StringBuilder builder = new StringBuilder();
        try {
            List<String> strings = Files.readAllLines(Paths.get(path + "/" + version));
            strings.forEach(builder::append);
        } catch (IOException e) {
            throw new EngineInitException("资源加载失败,path = " + path);
        }
        return resourceParser.parse(builder.toString());
    }

    @Override
    public Map<String, Resource> loadAllResources() {
        throw new UnsupportedOperationException();
    }
}
