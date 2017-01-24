package io.swagger.api.factories;

import io.swagger.api.impl.ToolClassesApiServiceImpl;
import io.swagger.api.ToolClassesApiService;

public class ToolClassesApiServiceFactory {
    private final static ToolClassesApiService service = new ToolClassesApiServiceImpl();

    public static ToolClassesApiService getToolClassesApi() {
        return service;
    }
}
