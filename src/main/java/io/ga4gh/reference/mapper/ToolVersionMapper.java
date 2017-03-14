package io.ga4gh.reference.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.swagger.server.model.ToolVersion;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Maps from SQL to the auto-generated Tool object
 */
public class ToolVersionMapper implements ResultSetMapper<ToolVersion> {

    @Override
    public ToolVersion map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ToolVersion toolVersion = new ToolVersion();
        toolVersion.setName(resultSet.getString("name"));
        toolVersion.setUrl(resultSet.getString("url"));
        toolVersion.setVerified(resultSet.getBoolean("verified"));
        toolVersion.setVerifiedSource(resultSet.getString("verifiedSource"));
        toolVersion.setMetaVersion(resultSet.getString("metaVersion"));
        return toolVersion;
    }
}
