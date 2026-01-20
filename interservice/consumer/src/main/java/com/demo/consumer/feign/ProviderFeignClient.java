package com.demo.consumer.feign;

@FeignClient(name = "producer-service", url = "http://localhost:8081")
public interface FeignClient {
    String getInstanceInfo();
}
