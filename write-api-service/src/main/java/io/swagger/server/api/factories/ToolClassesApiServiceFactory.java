package io.swagger.server.api.factories;

import io.swagger.server.api.impl.ToolClassesApiServiceImpl;
import io.swagger.server.api.ToolClassesApiService;

public class ToolClassesApiServiceFactory {
    private final static ToolClassesApiService service = new ToolClassesApiServiceImpl();

    public static ToolClassesApiService getToolClassesApi() {
        return service;
    }
}
