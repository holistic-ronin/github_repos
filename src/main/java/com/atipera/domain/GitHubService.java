package com.atipera.domain;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface GitHubService {
  Uni<List<RepositoryResponseDto>> getUserRepositoriesDto(final String username);
}
