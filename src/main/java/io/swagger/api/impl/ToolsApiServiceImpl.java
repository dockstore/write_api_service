package io.swagger.api.impl;

import java.util.Iterator;
import java.util.Objects;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.common.collect.Lists;
import io.ga4gh.reference.dao.ToolDescriptorDAO;
import io.ga4gh.reference.dao.ToolDockerfileDAO;
import io.ga4gh.reference.dao.ToolDAO;
import io.ga4gh.reference.dao.ToolVersionDAO;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.ToolsApiService;
import io.swagger.model.Tool;
import io.swagger.model.ToolVersion;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.jetty.http.HttpStatus;

public class ToolsApiServiceImpl extends ToolsApiService {

    private final ToolDAO toolDAO;
    private final ToolVersionDAO toolVersionDAO;
    private final ToolDescriptorDAO toolDescriptorDAO;
    private final ToolDockerfileDAO toolDockerfileDAO;

    public ToolsApiServiceImpl(ToolDAO dao, ToolVersionDAO toolVersionDAO, ToolDescriptorDAO toolDescriptorDAO, ToolDockerfileDAO toolDockerfileDAO){
        this.toolDAO = dao;
        this.toolVersionDAO = toolVersionDAO;
        this.toolDescriptorDAO = toolDescriptorDAO;
        this.toolDockerfileDAO = toolDockerfileDAO;
    }

    @Override
    public Response toolsGet(String id, String registry, String organization, String name, String toolname, String description, String author, String offset, Integer limit, SecurityContext securityContext) throws NotFoundException {
        Iterator<Tool> toolIterator = toolDAO.listAllTools();
        return Response.ok().entity(Lists.newArrayList(toolIterator)).build();
    }
    @Override
    public Response toolsIdGet(String id, SecurityContext securityContext) throws NotFoundException {
        Tool byId = toolDAO.findById(id);
        if (byId != null) {
            return Response.ok().entity(byId).build();
        }
        return Response.status(HttpStatus.NOT_FOUND_404).build();
    }
    @Override
    public Response toolsIdPut(String id, Tool body, SecurityContext securityContext) throws NotFoundException {
        // ensure that id matches
        if (!Objects.equals(id, body.getId())){
            return Response.notModified().build();
        }
        int update = toolDAO.update(body);
        if (update != 1){
            return Response.notModified().build();
        }
        return Response.ok().entity(toolDAO.findById(id)).build();
    }
    @Override
    public Response toolsIdVersionsGet(String id, SecurityContext securityContext) throws NotFoundException {
        Iterator<ToolVersion> toolVersionIterator = toolVersionDAO.listToolVersionsForTool(id);
        if (!toolVersionIterator.hasNext()){
            return Response.noContent().build();
        }
        return Response.ok().entity(Lists.newArrayList(toolVersionIterator)).build();
    }
    @Override
    public Response toolsIdVersionsPost(String id, ToolVersion body, SecurityContext securityContext) throws NotFoundException {
        int insert = toolVersionDAO.insert(id, body.getId());
        if (insert != 1){
            return Response.notModified().build();
        }
        ToolVersion byId = toolVersionDAO.findById(id, body.getId());
        if (byId == null){
            return Response.notModified().build();
        }
        return Response.ok().entity(byId).build();
    }
    @Override
    public Response toolsIdVersionsVersionIdDockerfileGet(String id, String versionId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdDockerfilePost(String id, String versionId, SecurityContext securityContext)
            throws NotFoundException {
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdGet(String id, String versionId, SecurityContext securityContext) throws NotFoundException {
        ToolVersion byId = toolVersionDAO.findById(id, versionId);
        if (byId == null){
            return Response.notModified().build();
        }
        return Response.ok().entity(byId).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdPut(String id, String versionId, ToolVersion body, SecurityContext securityContext)
            throws NotFoundException {
        // ensure that id matches
        if (!Objects.equals(versionId, body.getId())){
            return Response.notModified().build();
        }
        int update = toolVersionDAO.update(body);
        if (update != 1){
            return Response.notModified().build();
        }
        return Response.ok().entity(toolVersionDAO.findById(id, versionId)).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdTypeDescriptorGet(String type, String id, String versionId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdTypeDescriptorPost(String type, String id, String versionId, SecurityContext securityContext)
            throws NotFoundException {
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdTypeDescriptorRelativePathGet(String type, String id, String versionId, String relativePath, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdTypeDescriptorRelativePathPost(String type, String id, String versionId, String relativePath,
            SecurityContext securityContext) throws NotFoundException {
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsIdVersionsVersionIdTypeTestsGet(String type, String id, String versionId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response toolsPost(Tool body, SecurityContext securityContext) throws NotFoundException {
        toolDAO.insert(body.getId());
        Tool byId = toolDAO.findById(body.getId());
        if (byId == null){
            return Response.notModified().build();
        }
        return Response.ok().entity(byId).build();
    }
}
