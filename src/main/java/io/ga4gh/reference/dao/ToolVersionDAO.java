package io.ga4gh.reference.dao;

import java.util.Iterator;

import io.ga4gh.reference.mapper.ToolVersionMapper;
import io.swagger.model.ToolVersion;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ToolVersionMapper.class)
public interface ToolVersionDAO {

    @SqlUpdate("create table toolversion ("
            + "toolversion_id varchar(100) primary key, "
            + "url varchar(100) unique, "
            + "verified boolean, "
            + "verifiedSource varchar(100), "
            + "metaVersion varchar(100), "
            + "tool_id varchar(100) references tool(tool_id)"
            + ")")
    void createToolVersionTable();

    @SqlUpdate("insert into tool (toolversion_id, tool_id) values (:toolversion_id,:toolid)")
    int insert(@Bind("tool_id") String toolid, @Bind("toolversion_id") String toolVersionId);

    @SqlQuery("select * from toolversion where tool_id = :tool_id, toolversion_id = :toolversion_id")
    ToolVersion findById(@Bind("tool_id") String toolId, @Bind("toolversion_id") String toolVersionId);

    @SqlQuery("select * from toolversion where tool_id = :tool_id")
    Iterator<ToolVersion> listToolVersionsForTool(@Bind("tool_id") String toolid);

    @SqlUpdate("update tool set "
            + "url = :url, "
            + "image = :image, "
            + "verified = :verified, "
            + "verifiedSource = :verifiedSource, "
            + "metaVersion = :metaVersion, "
            + "where tool_id = :id and name = :toolversion_id")
    int update(@BindBean ToolVersion t);

}
