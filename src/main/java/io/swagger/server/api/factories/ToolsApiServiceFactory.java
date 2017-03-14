package io.swagger.server.api.factories;

import java.util.Objects;

import com.google.common.collect.Lists;
import io.ga4gh.reference.api.GitHubBuilder;
import io.ga4gh.reference.api.QuayIoBuilder;
import io.ga4gh.reference.dao.ToolDescriptorDAO;
import io.ga4gh.reference.dao.ToolDockerfileDAO;
import io.ga4gh.reference.dao.ToolDAO;
import io.ga4gh.reference.dao.ToolVersionDAO;
import io.swagger.server.api.impl.ToolsApiServiceImpl;
import io.swagger.server.api.ToolsApiService;

public class ToolsApiServiceFactory {
    private static ToolDAO toolDAO;
    private static ToolVersionDAO toolVersionDAO;
    private static ToolDescriptorDAO toolDescriptorDAO;
    private static ToolDockerfileDAO toolDockerfileDAO;
    private static GitHubBuilder gitHubBuilder;
    private static QuayIoBuilder quayIoBuilder;

    public static ToolsApiService getToolsApi() {
        Object[] stuff = {toolDAO, toolVersionDAO, toolDescriptorDAO, toolDockerfileDAO, gitHubBuilder, quayIoBuilder};
        if (Lists.newArrayList(stuff).stream().filter(Objects::isNull).count() > 0){
            throw new RuntimeException("dependency for ToolsApiServiceFactory was not setup");
        }
        return new ToolsApiServiceImpl(toolDAO, toolVersionDAO, toolDescriptorDAO, toolDockerfileDAO);
    }

    public static void setToolDAO(ToolDAO toolDAO) {
        ToolsApiServiceFactory.toolDAO = toolDAO;
    }
    public static void setToolVersionDAO(ToolVersionDAO toolVersionDAO){
        ToolsApiServiceFactory.toolVersionDAO = toolVersionDAO;
    }

    public static void setToolDescriptorDAO(ToolDescriptorDAO toolDescriptorDAO) {
        ToolsApiServiceFactory.toolDescriptorDAO = toolDescriptorDAO;
    }

    public static void setToolDockerfileDAO(ToolDockerfileDAO toolDockerfileDAO) {
        ToolsApiServiceFactory.toolDockerfileDAO = toolDockerfileDAO;
    }

    public static void setGitHubBuilder(GitHubBuilder gitHubBuilder) {
        ToolsApiServiceFactory.gitHubBuilder = gitHubBuilder;
    }

    public static void setQuayIoBuilder(QuayIoBuilder quayIoBuilder) {
        ToolsApiServiceFactory.quayIoBuilder = quayIoBuilder;
    }
}
