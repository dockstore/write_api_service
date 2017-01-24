package io.swagger.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.MetadataApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.Metadata;

public class MetadataApiServiceImpl extends MetadataApiService {
    @Override
    public Response metadataGet(SecurityContext securityContext) throws NotFoundException {
        Metadata metadata = new Metadata();
        metadata.setApiVersion("1.0.0");
        metadata.setCountry("CAN");
        metadata.setFriendlyName("GA4GH Reference Server");
        metadata.setVersion("1.0.0");
        return Response.ok().entity(metadata).build();
    }
}
