package pl.legit.service;

import org.springframework.cloud.openfeign.FeignClient;
import pl.legit.api.GreetingApi;

@FeignClient(name = "greeting", path = "/api")
public interface GreetingApiClient extends GreetingApi {
}
