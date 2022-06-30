package pl.legit.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface GreetingApi {
    @GetMapping("hello")
    ResponseEntity<String> getGreeting();
}
