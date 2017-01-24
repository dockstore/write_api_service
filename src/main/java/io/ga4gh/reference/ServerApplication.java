package io.ga4gh.reference;

import java.util.EnumSet;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.ga4gh.reference.dao.ToolDAO;
import io.ga4gh.reference.dao.ToolDescriptorDAO;
import io.ga4gh.reference.dao.ToolDockerfileDAO;
import io.ga4gh.reference.dao.ToolVersionDAO;
import io.swagger.api.MetadataApi;
import io.swagger.api.ToolClassesApi;
import io.swagger.api.ToolsApi;
import io.swagger.api.factories.ToolsApiServiceFactory;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.model.Tool;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static javax.servlet.DispatcherType.REQUEST;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_HEADERS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_METHODS_PARAM;
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_ORIGINS_PARAM;

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
    public void run(ServerConfiguration configuration, Environment environment) {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setSchemes(new String[] { /** configuration.getScheme() */ "http" });
        beanConfig.setHost("localhost:8080" /**configuration.getHostname() + ':' + configuration.getPort()*/);
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("io.swagger.api");
        beanConfig.setScan(true);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "derby");
        final ToolDAO toolDAO = jdbi.onDemand(ToolDAO.class);
        final ToolVersionDAO toolVersionDAO = jdbi.onDemand(ToolVersionDAO.class);
        final ToolDescriptorDAO toolDescriptorDAO = jdbi.onDemand(ToolDescriptorDAO.class);
        final ToolDockerfileDAO toolDockerfileDAO = jdbi.onDemand(ToolDockerfileDAO.class);

        try(Handle h = jdbi.open()){
            String createdbString = configuration.getDataSourceFactory().getProperties().getOrDefault("createdb", "false");
            boolean freshStart = Boolean.valueOf(createdbString);
            if (freshStart) {
                try {
                    h.execute("drop table dockerfile");
                    h.execute("drop table descriptor");
                    h.execute("drop table toolversion");
                    h.execute("drop table tool");
                }catch(Exception e){
                    // do nothing if the table does not already exist
                    System.out.println(e.getMessage());
                }
                toolDAO.createToolTable();
                toolVersionDAO.createToolVersionTable();
                toolDescriptorDAO.createToolDescriptorTable();
                toolDockerfileDAO.createToolDockerfileTable();
            }
        }

        ToolsApiServiceFactory.setToolDAO(toolDAO);
        ToolsApiServiceFactory.setToolVersionDAO(toolVersionDAO);
        ToolsApiServiceFactory.setToolDescriptorDAO(toolDescriptorDAO);
        ToolsApiServiceFactory.setToolDockerfileDAO(toolDockerfileDAO);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck("Hello %s!");
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(new ToolClassesApi());
        environment.jersey().register(new ToolsApi());
        environment.jersey().register(new MetadataApi());

        // extra renderers
        environment.jersey().register(new CharsetResponseFilter());

        // swagger stuff
        // Swagger providers
        environment.jersey().register(ApiListingResource.class);
        environment.jersey().register(SwaggerSerializers.class);

        // optional CORS support
        // Enable CORS headers
        final FilterHolder filterHolder = environment.getApplicationContext().addFilter(CrossOriginFilter.class, "/*", EnumSet.of(REQUEST));
        // Configure CORS parameters
        filterHolder.setInitParameter(ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        filterHolder.setInitParameter(ALLOWED_METHODS_PARAM, "GET,POST,DELETE,PUT,OPTIONS");
        filterHolder.setInitParameter(ALLOWED_HEADERS_PARAM,
                "Authorization, X-Auth-Username, X-Auth-Password, X-Requested-With,Content-Type,Accept,Origin,Access-Control-Request-Headers,cache-control");


        toolDAO.insert("quay.io/org1/test1");
        toolDAO.insert("quay.io/org1/test2");

        Tool tool1 = toolDAO.findById("quay.io/org1/test1");
        tool1.description("funky");
        toolDAO.update(tool1);
        Tool tool2 = toolDAO.findById("quay.io/org1/test2");

        System.out.println("hooked up");
    }
}
