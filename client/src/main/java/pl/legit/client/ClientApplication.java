package pl.legit.client;

import feign.Request;
import feign.Response;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.legit.api.GreetingApi;
import feign.Logger;
import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@EnableFeignClients({"pl.legit.service"})
@Configuration
public class ClientApplication {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    Logger.Level logLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    Logger feignLogger() {
        return new Logger() {
            @Override
            protected void log(String configKey, String format, Object... args) {
            }

            @Override
            protected Response logAndRebufferResponse(String configKey, Level logLevel,
                                                      Response response, long elapsedTime) throws IOException {
                return super.logAndRebufferResponse(configKey, logLevel, response,
                        elapsedTime);
            }

            @Override
            protected void logRequest(String configKey, Level logLevel, Request request) {
                log.debug(request.httpMethod() + " " + request.url());
                super.logRequest(configKey, logLevel, request);
            }
        };
    }

}

@Component
class HelloScheduler {

    private final GreetingApi greetingApi;

    HelloScheduler(GreetingApi greetingApi) {
        this.greetingApi = greetingApi;
    }

    @Scheduled(fixedDelay = 1000)
    public void process(){
        final var greeting = greetingApi.getGreeting();
        System.out.println(greeting.getBody());
    }

}
