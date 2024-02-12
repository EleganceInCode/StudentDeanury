package org.openapitools.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @Value("deanery")
    private String name;

    @GetMapping
    public String getNameApplication() {
        return "Вас приветствует проект: " + name;
    }
}
