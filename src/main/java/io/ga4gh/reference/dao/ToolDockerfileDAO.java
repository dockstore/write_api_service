package io.ga4gh.reference.dao;

import java.util.Iterator;

import io.ga4gh.reference.mapper.ToolDockerfileMapper;
import io.swagger.model.ToolDockerfile;
import io.swagger.model.ToolVersion;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ToolDockerfileMapper.class)
public interface ToolDockerfileDAO {

    @SqlUpdate("create table dockerfile ("
            + "url varchar(100) unique, "
            + "dockerfile clob, "
            + "toolversion_id varchar(100) unique references toolversion(toolversion_id)"
            + ")")
    void createToolDockerfileTable();

    @SqlUpdate("insert into dockerfile (toolversion_id, dockerfile) values (:toolversion_id,:dockerfile)")
    int insert(@Bind("toolversion_id") String toolVersionId, @Bind("dockerfile") String dockerfile);

    @SqlQuery("select * from dockerfile where toolversion_id = :toolversion_id")
    ToolDockerfile findById(@Bind("toolversion_id") String toolVersionId);

    @SqlQuery("select * from toolversion where tool_id = :tool_id")
    Iterator<ToolVersion> listToolVersionsForTool(@Bind("tool_id") String toolid);

    @SqlUpdate("update dockerfile set "
            + "url = :url "
            + "where id = :toolversion_id")
    int update(@BindBean ToolDockerfile t, @Bind("toolversion_id") String toolVersionId);

}
