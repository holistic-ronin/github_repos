// File: src/main/java/com/atipera/infrastructure/GitHubClientFacade.java
package com.atipera.infrastructure;

import com.atipera.domain.*;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class DefaultGitHubService implements GitHubService {

  @Inject @RestClient GitHubClient client;

  public Uni<List<RepositoryResponseDto>> getUserRepositoriesDto(final String username) {
    return client
        .getUserRepositories(username)
        .onItem()
        .transformToUni(
            repos -> {
              if (repos == null || repos.isEmpty()) {
                return Uni.createFrom()
                    .failure(
                        new jakarta.ws.rs.NotFoundException(
                            "GitHub user not found or has no repositories"));
              }
              final List<Uni<RepositoryResponseDto>> repoUnis =
                  repos.stream()
                      .filter(repo -> !repo.fork())
                      .map(
                          repo ->
                              client
                                  .getBranches(repo.owner().login(), repo.name())
                                  .onItem()
                                  .transform(
                                      branches ->
                                          GitHubMapper.mapToRepositoryResponseDto(repo, branches)))
                      .collect(Collectors.toList());
              return Uni.join().all(repoUnis).andFailFast();
            });
  }
}
