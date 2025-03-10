package com.atipera.presentation;

import com.atipera.domain.*;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/repositories")
public class GitHubController {

  @Inject GitHubFacade gitHubFacade;

  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> getRepositories(@PathParam("username") final String username) {
    return gitHubFacade
        .getUserRepositories(username)
        .onItem()
        .transform(repos -> Response.ok(repos).build())
        .onFailure(WebApplicationException.class)
        .recoverWithItem(
            throwable -> {
              final var error = new ErrorResponseDto(404, throwable.getMessage());
              return Response.status(Response.Status.NOT_FOUND)
                  .entity(error)
                  .type(MediaType.APPLICATION_JSON)
                  .build();
            });
  }
}
