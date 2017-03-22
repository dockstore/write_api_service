package io.ga4gh.reference.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.swagger.server.model.ToolDockerfile;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Maps from SQL to the auto-generated Tool object
 */
public class ToolDockerfileMapper implements ResultSetMapper<ToolDockerfile> {

    @Override
    public ToolDockerfile map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ToolDockerfile dockerfile = new ToolDockerfile();
        dockerfile.setUrl(resultSet.getString("url"));
        dockerfile.setDockerfile(resultSet.getString("dockerfile"));
        return dockerfile;
    }
}
