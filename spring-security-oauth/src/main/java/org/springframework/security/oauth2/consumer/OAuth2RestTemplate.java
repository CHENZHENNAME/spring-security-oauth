package org.springframework.security.oauth2.consumer;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Rest template that is able to make OAuth2-authenticated REST requests with the credentials of the provided resource.
 *
 * @author Ryan Heaton
 */
public class OAuth2RestTemplate extends RestTemplate {

  private final OAuth2ProtectedResourceDetails resource;

  public OAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
    this(new SimpleClientHttpRequestFactory(), resource);
  }

  public OAuth2RestTemplate(ClientHttpRequestFactory requestFactory, OAuth2ProtectedResourceDetails resource) {
    super();
    if (resource == null) {
      throw new IllegalArgumentException("An OAuth2 resource must be supplied.");
    }

    this.resource = resource;
    setRequestFactory(requestFactory);
  }

  @Override
  public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
    if (!(requestFactory instanceof OAuth2ClientHttpRequestFactory)) {
      requestFactory = new OAuth2ClientHttpRequestFactory(requestFactory, getResource());
    }
    super.setRequestFactory(requestFactory);
  }


  public OAuth2ProtectedResourceDetails getResource() {
    return resource;
  }
}
