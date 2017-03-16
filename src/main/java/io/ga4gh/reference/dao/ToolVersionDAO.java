package io.ga4gh.reference.dao;

import java.util.Iterator;

import io.ga4gh.reference.mapper.ToolVersionMapper;
import io.swagger.server.model.ToolVersion;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Represents a version of a tool.
 *
 */
@RegisterMapper(ToolVersionMapper.class)
public interface ToolVersionDAO {

    @SqlUpdate("create table toolversion ("
            + "version varchar(100), "
            + "url varchar(100) unique, "
            + "verified boolean, "
            + "verifiedSource varchar(100), "
            + "metaVersion varchar(100), "
            + "tool_id varchar(100) references tool(tool_id), "
            + "primary key (tool_id, version) "
            + ")")
    void createToolVersionTable();

    @SqlUpdate("insert into toolversion (tool_id, version) values (:tool_id,:version)")
    int insert(@Bind("tool_id") String toolid, @Bind("version") String version);

    @SqlQuery("select * from toolversion where tool_id = :tool_id and version = :version")
    ToolVersion findByToolVersion(@Bind("tool_id") String toolId, @Bind("version") String version);

    @SqlQuery("select * from toolversion where tool_id = :tool_id")
    Iterator<ToolVersion> listToolVersionsForTool(@Bind("tool_id") String toolid);

    @SqlUpdate("update tool set "
            + "url = :url, "
            + "verified = :verified, "
            + "verifiedSource = :verifiedSource, "
            + "metaVersion = :metaVersion, "
            + "where tool_id = :id and version = :version")
    int update(@BindBean ToolVersion t);

}
