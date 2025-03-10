package com.atipera.domain;

import java.util.List;

public record RepositoryResponseDto(
    String repositoryName, String ownerLogin, List<BranchResponseDto> branches) {}
