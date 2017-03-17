package io.ga4gh.reference;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;

/**
 *
 */
public class ServerConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty
    private String githubToken;

    public String getGithubToken() {
        return githubToken;
    }

    @JsonProperty
    private String quayioToken;

    public String getQuayioToken() {
        return quayioToken;
    }
}
