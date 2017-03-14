package io.ga4gh.reference.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.swagger.server.model.ToolDescriptor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Maps from SQL to the auto-generated Tool object
 */
public class ToolDescriptorMapper implements ResultSetMapper<ToolDescriptor> {

    @Override
    public ToolDescriptor map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ToolDescriptor toolDescriptor = new ToolDescriptor();
        toolDescriptor.setUrl(resultSet.getString("url"));
        toolDescriptor.setDescriptor(resultSet.getString("descriptor"));
        toolDescriptor.setType(ToolDescriptor.TypeEnum.valueOf(resultSet.getString("type")));
        return toolDescriptor;
    }
}
