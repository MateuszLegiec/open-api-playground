package pl.legit.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@Profile("docker")
@FeignClient(name = "GREETING", url = "http://localhost:9303")
public interface LocalGreetingApiClient extends GreetingApi {
}
