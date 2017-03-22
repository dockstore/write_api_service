package io.ga4gh.reference;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 *
 */
public class ServerConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();
    @JsonProperty
    private String githubToken;
    @JsonProperty
    private String quayioToken;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public String getQuayioToken() {
        return quayioToken;
    }
}
