package com.demo.consumer.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/web-client")
@RequiredArgsConstructor
public class WebClientController {
    private final ProviderWebClient providerWebClient;

    @GetMapping("/instance")
    public Mono<String> getInstance(){
        return providerWebClient.getInstanceInfo();
    }
}
