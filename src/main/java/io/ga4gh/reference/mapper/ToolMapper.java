package io.ga4gh.reference.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.swagger.model.Tool;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Maps from SQL to the auto-generated Tool object
 */
public class ToolMapper implements ResultSetMapper<Tool> {

    @Override
    public Tool map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Tool tool = new Tool();
        tool.setUrl(resultSet.getString("url"));
        tool.setId(resultSet.getString("id"));
        tool.setOrganization(resultSet.getString("organization"));
        tool.setToolname(resultSet.getString("toolname"));
        // needs its own type
        //tool.setToolclass(resultSet.getString("id"));
        tool.setDescription(resultSet.getString("description"));
        tool.setAuthor(resultSet.getString("author"));
        tool.setMetaVersion(resultSet.getString("metaVersion"));
        // contains is multiple strings
        //tool.setId(resultSet.getString("id"));
        tool.setVerified(resultSet.getBoolean("verified"));
        tool.setVerifiedSource(resultSet.getString("verifiedSource"));
        tool.setSigned(resultSet.getBoolean("signed"));
        // need to properly hook up tool versions
        return tool;
    }
}
