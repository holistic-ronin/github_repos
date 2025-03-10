package com.atipera.infrastructure;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "github-api")
interface GitHubClient {

  @GET
  @Path("/users/{login}/repos")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<List<GitHubRepo>> getUserRepositories(@PathParam("login") String username);

  @GET
  @Path("/repos/{owner}/{repo}/branches")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<List<GitHubBranch>> getBranches(
      @PathParam("owner") String owner, @PathParam("repo") String repo);
}
