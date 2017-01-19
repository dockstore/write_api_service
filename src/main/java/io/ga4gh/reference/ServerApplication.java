package io.ga4gh.reference;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.ga4gh.reference.dao.ToolDAO;
import io.swagger.api.MetadataApi;
import io.swagger.api.ToolClassesApi;
import io.swagger.api.ToolsApi;
import io.swagger.api.factories.ToolsApiServiceFactory;
import io.swagger.model.Tool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 *
 */
public class ServerApplication extends Application<ServerConfiguration>{
    public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "tool-registry-application";
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
    }

    @Override
    public void run(ServerConfiguration configuration,
            Environment environment) {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "derby");
        final ToolDAO dao = jdbi.onDemand(ToolDAO.class);

        try(Handle h = jdbi.open()){
            String createdbString = configuration.getDataSourceFactory().getProperties().getOrDefault("createdb", "false");
            boolean freshStart = Boolean.valueOf(createdbString);
            if (freshStart) {
                try {
                    h.execute("drop table tool");
                    h.execute("drop sequence id restrict");
                }catch(Exception e){
                    // do nothing if the table already exists
                }
                dao.createToolTable();
            }
        }

        ToolsApiServiceFactory.setToolDAO(dao);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck("Hello %s!");
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(new ToolClassesApi());
        environment.jersey().register(new ToolsApi());
        environment.jersey().register(new MetadataApi());

        dao.insert("quay.io/org1/test1");
        dao.insert("quay.io/org1/test2");

        Tool tool1 = dao.findById("quay.io/org1/test1");
        tool1.description("funky");
        dao.update(tool1);
        Tool tool2 = dao.findById("quay.io/org1/test2");

        System.out.println("hooked up");
    }
}
