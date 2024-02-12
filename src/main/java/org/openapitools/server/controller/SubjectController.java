package org.openapitools.server.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.server.controller.dto.CreateSubjectRequest;
import org.openapitools.server.controller.dto.SubjectResponse;
import org.openapitools.server.repository.dao.SubjectDao;
import org.openapitools.server.service.SubjectService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<SubjectDao> findAll() {
        return subjectService.findAll();
    }

    @GetMapping(value = "/{subjectId}", produces = APPLICATION_JSON_VALUE)
    public SubjectResponse findById(@PathVariable Long subjectId) {
        return subjectService.findById(subjectId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public SubjectResponse createSubject(@RequestBody CreateSubjectRequest request) {
        return subjectService.createSubject(request);
    }

    @PatchMapping(value = "/{subjectId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public SubjectResponse update(@PathVariable Long subjectId, @RequestBody CreateSubjectRequest request) {
        return subjectService.update(request);
    }
}
