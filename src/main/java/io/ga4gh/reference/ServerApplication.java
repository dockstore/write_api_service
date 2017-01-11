package io.ga4gh.reference;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.api.MetadataApi;
import io.swagger.api.ToolClassesApi;
import io.swagger.api.ToolsApi;

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
        // nothing to do yet
    }

    @Override
    public void run(ServerConfiguration configuration,
            Environment environment) {
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(new ToolClassesApi());
        environment.jersey().register(new ToolsApi());
        environment.jersey().register(new MetadataApi());

    }
}
