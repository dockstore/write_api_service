package io.swagger.server.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.server.api.MetadataApiService;
import io.swagger.server.api.NotFoundException;
import io.swagger.server.model.Metadata;

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
