package io.swagger.api.factories;

import io.swagger.api.impl.ToolsApiServiceImpl;
import io.swagger.api.ToolsApiService;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-12T10:53:56.619-05:00")
public class ToolsApiServiceFactory {
    private final static ToolsApiService service = new ToolsApiServiceImpl();

    public static ToolsApiService getToolsApi() {
        return service;
    }
}
