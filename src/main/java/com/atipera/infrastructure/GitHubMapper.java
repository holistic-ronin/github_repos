package com.atipera.infrastructure;

import com.atipera.domain.BranchResponseDto;
import com.atipera.domain.RepositoryResponseDto;
import java.util.List;
import java.util.stream.Collectors;

final class GitHubMapper {

  private GitHubMapper() {}

  static RepositoryResponseDto mapToRepositoryResponseDto(
      final GitHubRepo repo, final List<GitHubBranch> branches) {
    final var branchDtos =
        branches.stream().map(GitHubMapper::mapToBranchResponseDto).collect(Collectors.toList());
    return new RepositoryResponseDto(repo.name(), repo.owner().login(), branchDtos);
  }

  public static BranchResponseDto mapToBranchResponseDto(final GitHubBranch branch) {
    return new BranchResponseDto(branch.name(), branch.commitSha());
  }
}
