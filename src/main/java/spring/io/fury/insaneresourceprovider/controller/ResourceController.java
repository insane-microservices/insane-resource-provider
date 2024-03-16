package spring.io.fury.insaneresourceprovider.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spring.io.fury.insaneresourceprovider.security.AppClientCredentialsRequest;
import spring.io.fury.insaneresourceprovider.security.AppTokenResponse;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
class Gpt4allResponse {
    private final String message;
}

@AllArgsConstructor
@Getter
@Setter
class Gpt4allRequest {
    private final String prompt;
}

@RestController
@RequestMapping("resources")
public class ResourceController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MultiValueMap<String, String> clientCredentialsRequest;

    @GetMapping("mine")
    public ResponseEntity<Gpt4allResponse> helloWord() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(clientCredentialsRequest, headers);
        AppTokenResponse tokenResponse = restTemplate.postForObject("http://keycloak.insane.com:8080/realms/insane-realm/protocol/openid-connect/token",
                 requestHttpEntity, AppTokenResponse.class);
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(Optional.ofNullable(tokenResponse).isPresent() ? tokenResponse.getAccessToken() : "");
        HttpEntity<Gpt4allRequest> httpEntity = new HttpEntity<>(new Gpt4allRequest("Provide recipe for chicken biryani"), headers1);
         Gpt4allResponse gpt4allResponse = restTemplate
                 .postForObject("http://insane-internal-oauth2-gateway:8089/gpt4all/prompt",
                         httpEntity, Gpt4allResponse.class);
         return ResponseEntity.ok(gpt4allResponse);
    }

    @GetMapping("yours")
    public ResponseEntity<String> yourWorld() {
        return ResponseEntity.ok("Hello world");
    }
}
