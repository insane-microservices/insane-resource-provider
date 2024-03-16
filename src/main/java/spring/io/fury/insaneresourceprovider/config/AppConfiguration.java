package spring.io.fury.insaneresourceprovider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import spring.io.fury.insaneresourceprovider.security.AppClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@Configuration
public class AppConfiguration {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.keycloak.authorization-grant-type}")
    private String grantType;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MultiValueMap<String, String> appClientCredentialsRequest() {
        MultiValueMap<String, String> clientCredentialsReq = new LinkedMultiValueMap<>();
        System.out.println(clientId);
        System.out.println(clientSecret);
        System.out.println(grantType);
        clientCredentialsReq.add("client_id", clientId);
        clientCredentialsReq.add("client_secret", clientSecret);
        clientCredentialsReq.add("grant_type", grantType);
        return clientCredentialsReq;
    }
}
