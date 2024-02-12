package org.openapitools.swaggerapi;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;

import org.springframework.context.annotation.Bean;

public class OpenApiComponent {

    @Bean(name = "org.openapitools.api.OpenApiGeneratorApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}
