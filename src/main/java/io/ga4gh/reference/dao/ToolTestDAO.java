package io.ga4gh.reference.dao;


import java.util.Iterator;

import io.ga4gh.reference.mapper.ToolTestMapper;
import io.swagger.server.model.ToolTests;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ToolTestMapper.class)
public interface ToolTestDAO {

    @SqlUpdate("create table tooltest ("
            + "url varchar(100) unique, "
            + "content clob, "
            + "tool_id varchar(100), "
            + "version varchar(100), "
            + "foreign key(tool_id, version) references toolversion(tool_id, version) "
            + ")")
    void createToolTestTable();

    @SqlUpdate("insert into tooltest (toolversion_id, content) values (:toolversion_id,:content)")
    int insert(@Bind("toolversion_id") String toolVersionId, @Bind("content") String content);

    @SqlQuery("select * from tooltest where toolversion_id = :toolversion_id")
    ToolTests findById(@Bind("toolversion_id") String toolVersionId);

    @SqlQuery("select * from tooltest where tool_id = :tool_id and toolversion_id = :toolversion_id")
    Iterator<ToolTests> listTestsForToolVersion(@Bind("tool_id") String toolid, @Bind("toolversion_id") String toolVersionId);
}
