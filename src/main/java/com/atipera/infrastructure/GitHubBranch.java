package com.atipera.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record GitHubBranch(String name, String commitSha) {

  @JsonCreator
  public GitHubBranch(
      @JsonProperty("name") final String name,
      @JsonProperty("commit") final Map<String, Object> commit) {
    this(name, commit != null ? (String) commit.get("sha") : null);
  }
}
