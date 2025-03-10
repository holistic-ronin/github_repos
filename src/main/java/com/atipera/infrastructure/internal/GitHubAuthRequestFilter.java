package com.atipera.infrastructure.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
@ApplicationScoped
public class GitHubAuthRequestFilter implements ClientRequestFilter {

  @Inject
  @ConfigProperty(name = "github.token", defaultValue = "")
  String token;

  @Override
  public void filter(final ClientRequestContext requestContext) {
    if (token != null && !token.isEmpty()) {
      requestContext.getHeaders().add("Authorization", "token " + token);
    }
  }
}
