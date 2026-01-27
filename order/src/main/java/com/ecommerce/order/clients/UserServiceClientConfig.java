package com.ecommerce.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {



    @Bean
    public UserServiceClient userServiceClient(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient){
        RestClient.Builder builder = RestClient.builder(restTemplate);

        builder.requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient));

        RestClient restClient = builder
                .baseUrl("http://user-service")
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> Optional.empty()
                )
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(UserServiceClient.class);
    }
}
