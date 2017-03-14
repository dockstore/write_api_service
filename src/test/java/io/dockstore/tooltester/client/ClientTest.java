package io.dockstore.tooltester.client;

import java.io.IOException;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.ga4gh.reference.ServerApplication;
import io.ga4gh.reference.ServerConfiguration;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.GAGHApi;
import io.swagger.client.api.GAGHoptionalwriteApi;
import io.swagger.client.model.Metadata;
import io.swagger.client.model.Tool;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class ClientTest {

    @ClassRule
    public static final DropwizardAppRule<ServerConfiguration> RULE = new DropwizardAppRule<>(
            ServerApplication.class, ResourceHelpers.resourceFilePath("ref.yml"));

    @Test
    public void validateGA4GH() throws IOException, ApiException {
        GAGHApi api = getGaghApi();
        Metadata metadata = api.metadataGet();
        Assert.assertTrue(!metadata.getVersion().isEmpty());
        Assert.assertTrue(!metadata.getCountry().isEmpty());
    }

    @Test
    public void postDescriptorAndCreateRepo() throws IOException, ApiException {
        // TODO: clean repo if needed
        GAGHoptionalwriteApi api = getGaghOptionalApi();
        Tool tool = new Tool();
        tool.setId("dockstore-testing/test_repo");
        tool.setOrganization("dockstore-testing");
        tool.setToolname("test_repo");
        Tool createdTool = api.toolsPost(tool);
        Assert.assertTrue(createdTool.getOrganization().equals("dockstore-testing"));
    }

    private GAGHApi getGaghApi() {
        final int localPort = RULE.getLocalPort();
        ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:" + localPort + "/api/ga4gh/v1");
        return new GAGHApi(client);
    }

    private GAGHoptionalwriteApi getGaghOptionalApi() {
        final int localPort = RULE.getLocalPort();
        ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:" + localPort + "/api/ga4gh/v1");
        return new GAGHoptionalwriteApi(client);
    }

}
