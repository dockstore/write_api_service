package io.swagger.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.NotFoundException;
import io.swagger.api.ToolClassesApiService;
import io.swagger.model.ToolClass;

public class ToolClassesApiServiceImpl extends ToolClassesApiService {
    @Override
    public Response toolClassesGet(SecurityContext securityContext) throws NotFoundException {
        ToolClass toolClass = new ToolClass();
        toolClass.setDescription("CommandLineTool");
        toolClass.setId("0");
        toolClass.setName("CommandLineTool");
        return Response.ok().entity(toolClass).build();
    }
}
