package io.swagger.api.factories;

import io.swagger.api.impl.ToolClassesApiServiceImpl;
import io.swagger.api.ToolClassesApiService;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-12T10:53:56.619-05:00")
public class ToolClassesApiServiceFactory {
    private final static ToolClassesApiService service = new ToolClassesApiServiceImpl();

    public static ToolClassesApiService getToolClassesApi() {
        return service;
    }
}
