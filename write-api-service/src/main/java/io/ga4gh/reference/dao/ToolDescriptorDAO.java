package io.ga4gh.reference.dao;


import java.util.Iterator;

import io.ga4gh.reference.mapper.ToolDescriptorMapper;
import io.swagger.server.model.ToolDescriptor;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ToolDescriptorMapper.class)
public interface ToolDescriptorDAO {

    @SqlUpdate("create table descriptor ("
            + "url varchar(100) unique, "
            + "descriptor clob, "
            + "type varchar(100), "
            + "descriptor_path varchar(100), "
            + "tool_id varchar(100), "
            + "version varchar(100), "
            + "foreign key(tool_id, version) references toolversion(tool_id, version) "
            + ")")
    void createToolDescriptorTable();

    @SqlUpdate("insert into descriptor (tool_id, version, type) values (:tool_id, :version, :type)")
    int insert(@Bind("tool_id") String toolId, @Bind("version") String version, @Bind("type") String type);

    @SqlQuery("select * from descriptor where tool_id = :tool_id and version = :version and type = :type")
    ToolDescriptor findById(@Bind("tool_id") String toolId, @Bind("version") String version, @Bind("type") String type);

    @SqlQuery("select * from descriptor where tool_id = :tool_id and version = :version and descriptor_path = :descriptor_path")
    ToolDescriptor findByPath(@Bind("tool_id") String toolId, @Bind("version") String version, @Bind("descriptor_path") String descriptorPath);

    @SqlQuery("select * from descriptor where toolversion_id = :toolversion_id")
    Iterator<ToolDescriptor> listDescriptorsForTool(@Bind("toolversion_id") String toolVersionId);

    @SqlUpdate("update descriptor set "
            + "url = :url,"
            + "type = :type,"
            + "descriptor_path = :descriptor_path,"
            + " where toolversion_id = :toolversion_id and descriptor_path = :descriptor_path")
    int update(@BindBean ToolDescriptor t, @Bind("toolversion_id") String toolVersionId, @Bind("descriptor_path") String path);

}
