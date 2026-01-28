package com.ecommerce.order.clients;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
public class RestClientConfig {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;
    private final Propagator propagator;

    public RestClientConfig(
            @Autowired(required = false) ObservationRegistry observationRegistry,
            @Autowired(required = false) Tracer tracer,
            @Autowired(required = false) Propagator propagator) {
        this.observationRegistry = observationRegistry;
        this.tracer = tracer;
        this.propagator = propagator;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate  = new RestTemplate();

        if(observationRegistry != null){
            restTemplate.getInterceptors().add(createTracingInterceptor());
        }
        return restTemplate; 
    }

    private ClientHttpRequestInterceptor createTracingInterceptor() {
        return ((request, body, execution) -> {

            if(tracer != null && propagator != null && tracer.currentSpan() != null){
                propagator.inject(tracer.currentTraceContext().context(),
                        request.getHeaders(),
                        (carrier, key, value) -> carrier.add(key, value));
            }

            return execution.execute(request, body);

            }
        );
    }


}
