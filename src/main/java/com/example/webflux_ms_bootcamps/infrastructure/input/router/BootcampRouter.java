package com.example.webflux_ms_bootcamps.infrastructure.input.router;

import com.example.webflux_ms_bootcamps.infrastructure.input.handler.BootcampHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.example.webflux_ms_bootcamps.infrastructure.input.util.constants.ConstantsInput.PATH_BOOTCAMPS;

@Configuration
public class BootcampRouter {

    @Bean
    public RouterFunction<ServerResponse> bootcampRoutes(BootcampHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_BOOTCAMPS, handler::createBootcamp)
                .build();
    }
}
