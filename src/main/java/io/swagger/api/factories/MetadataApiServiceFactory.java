package io.swagger.api.factories;

import io.swagger.api.impl.MetadataApiServiceImpl;
import io.swagger.api.MetadataApiService;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-01-12T10:53:56.619-05:00")
public class MetadataApiServiceFactory {
    private final static MetadataApiService service = new MetadataApiServiceImpl();

    public static MetadataApiService getMetadataApi() {
        return service;
    }
}
