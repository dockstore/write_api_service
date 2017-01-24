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
            + "name varchar(100), "
            + "verified boolean, "
            + "verifiedSource varchar(100), "
            + "metaVersion varchar(100), "
            + "tool_id varchar(100) references tool(tool_id)"
            + ")")
    void createToolVersionTable();

    @SqlUpdate("insert into tool (toolversion_id, tool_id) values (:toolversion_id,:toolid)")
    void insert(@Bind("toolversion_id") String toolVersionId, @Bind("tool_id") String toolid);

    @SqlQuery("select * from toolversion where tool_id = :tool_id")
    ToolVersion findById(@Bind("tool_id") String id);

    @SqlQuery("select * from toolversion where tool_id = :tool_id")
    Iterator<ToolVersion> listToolVersionsForTool(@Bind("tool_id") String toolid);

    @SqlUpdate("update tool set "
            + "name = :name,"
            + "url = :url,"
            + "image = :image,"
            + "verified = :verified,"
            + "verifiedSource = :verifiedSource,"
            + "metaVersion = :metaVersion,"
            + " where tool_id = :id")
    int update(@BindBean ToolVersion t);

}
