package org.openapitools.swaggerapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-09T16:30:00.158783500+03:00[Europe/Moscow]")
@Controller
@RequestMapping("${openapi.openAPI.base-path:/api/v3}")
public class StudentApiController implements StudentApi {

    private final NativeWebRequest request;

    @Autowired
    public StudentApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
