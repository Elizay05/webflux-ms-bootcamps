package com.example.webflux_ms_bootcamps.infrastructure.input.router;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.dto.response.BootcampPageResponse;
import com.example.webflux_ms_bootcamps.infrastructure.input.handler.BootcampHandler;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static com.example.webflux_ms_bootcamps.infrastructure.input.util.constants.ConstantsInput.*;

@Configuration
public class BootcampRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = PATH_BOOTCAMPS,
                    beanClass = BootcampHandler.class,
                    beanMethod = METHOD_CREATE,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = OP_CREATE_BOOTCAMP,
                            summary = SUMMARY_CREATE_BOOTCAMP,
                            description = DESC_CREATE_BOOTCAMP,
                            requestBody = @RequestBody(
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BootcampRequest.class),
                                            examples = @ExampleObject(
                                                    name = EXAMPLE_NAME_CREATE,
                                                    value = EXAMPLE_BOOTCAMP_CREATE
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = CODE_201,
                                            description = RESP_BOOTCAMP_CREATED
                                    ),
                                    @ApiResponse(responseCode = CODE_400,
                                            description = RESP_ERROR_VALIDATION,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(
                                                            value = EXAMPLE_ERROR_VALIDATION
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_409,
                                            description = RESP_ERROR_EXISTS,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(
                                                            value = EXAMPLE_ERROR_EXISTS
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_500,
                                            description = RESP_ERROR_SERVER
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PATH_BOOTCAMPS,
                    beanClass = BootcampHandler.class,
                    beanMethod = METHOD_GET,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = OP_GET_BOOTCAMPS,
                            summary = SUMMARY_GET_BOOTCAMPS,
                            description = DESC_GET_BOOTCAMPS,
                            parameters = {
                                    @Parameter(name = PARAM_PAGE, description = DESC_PAGE, example = EXAMPLE_PAGE, in = ParameterIn.QUERY),
                                    @Parameter(name = PARAM_SIZE, description = DESC_SIZE, example = EXAMPLE_SIZE, in = ParameterIn.QUERY),
                                    @Parameter(name = PARAM_ASC, description = DESC_ASC, example = EXAMPLE_ASC, in = ParameterIn.QUERY),
                                    @Parameter(name = PARAM_SORT_BY, description = DESC_SORT_BY, example = EXAMPLE_SORT_BY, in = ParameterIn.QUERY)
                            },
                            responses = {
                                    @ApiResponse(responseCode = CODE_200,
                                            description = RESP_BOOTCAMP_LIST,
                                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = BootcampPageResponse.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_400,
                                            description = RESP_BAD_REQUEST,
                                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> bootcampRoutes(BootcampHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_BOOTCAMPS, handler::createBootcamp)
                .GET(PATH_BOOTCAMPS, handler::getBootcamps)
                .build();
    }
}