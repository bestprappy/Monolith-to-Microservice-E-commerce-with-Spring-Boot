package com.demo.consumer.httpinterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {


    @Bean
    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder builder){

        WebClient webclient = builder.baseUrl("http://producer").build();

        WebClientAdapter adapter = WebClientAdapter.create(webclient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProviderHttpInterface.class);
    }

}
