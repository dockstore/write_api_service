package io.ga4gh.reference.dao;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
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
            User user = uService.getUser();
            Repository repoTemplate = new Repository();
            repoTemplate.setName(repo);
            Repository repository = service.createRepository(repoTemplate);
            // need to initialize the new repo, oddly not possible via API
            HashMap<String, Object> map = new HashMap<>();
            byte[] encode = Base64.getEncoder().encode("Test".getBytes());
            map.put("content", new String(encode));
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
            // no API for creating files? weird
            HashMap<String, Object> map = new HashMap<>();
            byte[] encode = Base64.getEncoder().encode(content.getBytes());
            map.put("content", new String(encode));
            map.put("message","test");
            githubClient.put("/repos/" + organization + "/" + repo + "/contents/" + path, map, Map.class);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        GitHubBuilder builder = new GitHubBuilder(args[0]);
        if (!builder.repoExists("denis-yuen", "test_repo")) {
            builder.createRepo("denis-yuen", "test_repo");
        }
        builder.stashFile("denis-yuen","test_repo", "dockstore.test", "stuff2");
    }
}
