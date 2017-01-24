package io.swagger.api.factories;

import io.ga4gh.reference.dao.ToolDescriptorDAO;
import io.ga4gh.reference.dao.ToolDockerfileDAO;
import io.ga4gh.reference.dao.ToolDAO;
import io.ga4gh.reference.dao.ToolVersionDAO;
import io.swagger.api.impl.ToolsApiServiceImpl;
import io.swagger.api.ToolsApiService;

public class ToolsApiServiceFactory {
    private static ToolDAO toolDAO;
    private static ToolVersionDAO toolVersionDAO;
    private static ToolDescriptorDAO toolDescriptorDAO;
    private static ToolDockerfileDAO toolDockerfileDAO;

    public static ToolsApiService getToolsApi() {
        if (toolDAO == null){
            throw new RuntimeException("toolDAO not setup");
        }
        if (toolVersionDAO == null){
            throw new RuntimeException("toolVersionDAO not setup");
        }
        if (toolDescriptorDAO == null){
            throw new RuntimeException("toolDescriptorDAO not setup");
        }
        if (toolDockerfileDAO == null){
            throw new RuntimeException("toolDockerfileDAO not setup");
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
}
