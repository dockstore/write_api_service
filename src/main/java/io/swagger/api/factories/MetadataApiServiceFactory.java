package io.swagger.api.factories;

import io.swagger.api.impl.MetadataApiServiceImpl;
import io.swagger.api.MetadataApiService;

public class MetadataApiServiceFactory {
    private final static MetadataApiService service = new MetadataApiServiceImpl();

    public static MetadataApiService getMetadataApi() {
        return service;
    }
}
