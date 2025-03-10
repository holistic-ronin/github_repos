package com.atipera.domain;

import com.atipera.infrastructure.DefaultGitHubService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GitHubFacade {

  @Inject DefaultGitHubService gitHubService;

  public Uni<List<RepositoryResponseDto>> getUserRepositories(final String username) {
    return gitHubService.getUserRepositoriesDto(username);
  }
}
