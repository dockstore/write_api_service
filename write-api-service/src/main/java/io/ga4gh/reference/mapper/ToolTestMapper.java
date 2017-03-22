package io.ga4gh.reference.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.swagger.server.model.ToolTests;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Maps from SQL to the auto-generated Tool object
 */
public class ToolTestMapper implements ResultSetMapper<ToolTests> {

    @Override
    public ToolTests map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ToolTests tests = new ToolTests();
        tests.setTest(resultSet.getString("content"));
        tests.setUrl(resultSet.getString("url"));
        return tests;
    }
}
