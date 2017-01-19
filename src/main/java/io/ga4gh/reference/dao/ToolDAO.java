package io.ga4gh.reference.dao;

import io.ga4gh.reference.mapper.ToolMapper;
import io.swagger.model.Tool;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ToolMapper.class)
public interface ToolDAO {

    @SqlUpdate("create table tool (id varchar(100) primary key, toolname varchar(100), url varchar(100) unique, organization varchar(100), description varchar(100), author varchar(100), verified boolean, "
            + "verifiedSource varchar(100), signed boolean, metaVersion varchar(100))")
    void createToolTable();

    @SqlUpdate("insert into tool (id) values (:id)")
    void insert(@Bind("id") String id);

    @SqlQuery("select id, toolname, url, organization, description, author, verified, verifiedSource, metaVersion, signed from tool where id = :id")
    Tool findById(@Bind("id") String id);

    @SqlUpdate("update tool set url = :url,"
            + "organization = :organization,"
            + "description = :description,"
            + "author = :author,"
            + "verified = :verified,"
            + "verifiedSource = :verifiedSource,"
            + "metaVersion = :metaVersion,"
            + "signed = :signed"
            + " where id = :id")
    int update(@BindBean Tool t);

}
