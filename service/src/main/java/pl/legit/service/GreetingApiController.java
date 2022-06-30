package pl.legit.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class GreetingApiController implements GreetingApi {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public GreetingApiController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> getGreeting() {
        final URI clientUri = discoveryClient.getInstances("CLIENT").get(0).getUri();
        final ResponseEntity<String> forEntity = this.restTemplate.getForEntity(clientUri, String.class);
        return ResponseEntity.ok(forEntity.getBody());
    }
}
