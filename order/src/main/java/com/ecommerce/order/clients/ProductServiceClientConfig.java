package com.ecommerce.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.client.RestClient;
import java.util.Optional;
import org.springframework.http.HttpStatusCode;

@Configuration
public class ProductServiceClientConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ProductServiceClient productServiceClient(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient){
        RestClient.Builder builder = RestClient.builder(restTemplate);
        
        builder.requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient));
        
        RestClient restClient = builder
                .baseUrl("http://product-service")
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> Optional.empty()
                )
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(ProductServiceClient.class);
    }
}
