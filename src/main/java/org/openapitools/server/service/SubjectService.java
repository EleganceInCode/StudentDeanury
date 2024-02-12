package org.openapitools.server.service;

import lombok.NonNull;
import org.openapitools.server.controller.dto.CreateSubjectRequest;
import org.openapitools.server.controller.dto.SubjectResponse;
import org.openapitools.server.repository.dao.SubjectDao;

import java.util.List;

public interface SubjectService {
    List<SubjectDao> findAll();

    SubjectResponse findById(@NonNull Long subjectId);

    @NonNull
    SubjectResponse createSubject(@NonNull CreateSubjectRequest request);

    @NonNull
    SubjectResponse update(@NonNull CreateSubjectRequest request);

    void delete(@NonNull Long id);

    void deleteByIdIn(List<Long> ids);

}
