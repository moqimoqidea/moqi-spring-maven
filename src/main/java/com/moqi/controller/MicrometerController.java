package com.moqi.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * from: <a href="https://medium.com/@ProbablyOliver/connecting-spring-actuator-and-micrometer-metrics-to-graphite-and-grafana-fe5ce6137821">Connecting Spring Actuator and Micrometer Metrics to Graphite and Grafana</a>
 *
 * @author moqi
 * @date 2023/3/6 16:32
 */
@Slf4j
@RestController
public class MicrometerController {

    private MeterRegistry registry;

    public MicrometerController(MeterRegistry registry) {
        this.registry = registry;
    }

    Counter endpointCounter =
        Counter.builder("endpointCounter")
               .description("My first endpoint counter")
               .register(registry);

    @GetMapping("/api/myFirstEndpoint")
    public void getSomething(){
        endpointCounter.increment();
    }

}
