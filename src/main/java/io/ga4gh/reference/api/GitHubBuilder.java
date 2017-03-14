package io.ga4gh.reference.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.DataService;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

/**
 * Create structures in the dockstore.org format.
 */
public class GitHubBuilder {
    private final GitHubClient githubClient;
    private final UserService uService;
    private final OrganizationService oService;
    private final ContentsService cService;
    private final RepositoryService service;
    private final DataService dService;

    public GitHubBuilder(String token){
        this.githubClient = new GitHubClient();
        githubClient.setOAuth2Token(token);

        uService = new UserService(githubClient);
        oService = new OrganizationService(githubClient);
        service = new RepositoryService(githubClient);
        cService = new ContentsService(githubClient);
        dService = new DataService(githubClient);
    }


    public boolean createRepo(String organization, String repo){

        try {
            Repository repoTemplate = new Repository();
            repoTemplate.setName(repo);
            service.createRepository(repoTemplate);
            // need to initialize the new repo, oddly not possible via API
            HashMap<String, Object> map = new HashMap<>();
            byte[] encode = Base64.getEncoder().encode("Test".getBytes(StandardCharsets.UTF_8));
            map.put("content", new String(encode, StandardCharsets.UTF_8));
            map.put("message","test");
            githubClient.put("/repos/" + organization + "/" + repo + "/contents/readme.md", map, Map.class);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean repoExists(String organization, String repo) {
        try {
            return service.getRepository(organization, repo) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean stashFile(String organization, String repo, String path, String content){
        try {
            Repository repository = service.getRepository(organization, repo);
            List<RepositoryContents> contents = new ArrayList<>();
            try {
                contents = cService.getContents(repository, path);
            } catch(IOException e) {

            }
            if (contents.isEmpty()) {
                // no API for creating files? weird
                HashMap<String, Object> map = new HashMap<>();
                byte[] encode = Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));
                map.put("content", new String(encode, StandardCharsets.UTF_8));
                map.put("message", "test");
                githubClient.put("/repos/" + organization + "/" + repo + "/contents/" + path, map, Map.class);
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean createRelease(String organization, String repo) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate localDate = LocalDate.now();
            String date = dtf.format(localDate);
            // no API for creating files on releases? weird
            HashMap<String, Object> map = new HashMap<>();
            map.put("tag_name", date);
            map.put("name", date);
            githubClient.post("/repos/" + organization + "/" + repo + "/releases", map, Map.class);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args){
        GitHubBuilder builder = new GitHubBuilder(args[0]);
        String organization = "denis-yuen";
        String repo = "test_repo";
        if (!builder.repoExists(organization, repo)) {
            builder.createRepo(organization, repo);
        }
        builder.stashFile(organization, repo, "Dockerfile", "FROM ubuntu:12.04");
        builder.createRelease(organization, repo);

    }
}
