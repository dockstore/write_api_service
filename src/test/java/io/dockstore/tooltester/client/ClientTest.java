package io.dockstore.tooltester.client;

import java.io.IOException;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.ga4gh.reference.ServerApplication;
import io.ga4gh.reference.ServerConfiguration;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.GAGHApi;
import io.swagger.client.model.Metadata;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class ClientTest {

    @ClassRule
    public static final DropwizardAppRule<ServerConfiguration> RULE = new DropwizardAppRule<>(
            ServerApplication.class, ResourceHelpers.resourceFilePath("ref.yml"));

    @Test
    public void validateGA4GH() throws IOException, ApiException {
        final int localPort = RULE.getLocalPort();
        ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:" + localPort + "/api/ga4gh/v1");
        GAGHApi api = new GAGHApi(client);
        Metadata metadata = api.metadataGet();
        Assert.assertTrue(!metadata.getVersion().isEmpty());
        Assert.assertTrue(!metadata.getCountry().isEmpty());
    }


}
