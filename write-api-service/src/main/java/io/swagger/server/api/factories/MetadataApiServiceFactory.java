package io.swagger.server.api.factories;

import io.swagger.server.api.impl.MetadataApiServiceImpl;
import io.swagger.server.api.MetadataApiService;

public class MetadataApiServiceFactory {
    private final static MetadataApiService service = new MetadataApiServiceImpl();

    public static MetadataApiService getMetadataApi() {
        return service;
    }
}
