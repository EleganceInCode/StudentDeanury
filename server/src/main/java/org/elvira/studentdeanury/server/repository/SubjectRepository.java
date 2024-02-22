package org.elvira.studentdeanury.server.repository;

import org.elvira.studentdeanury.server.repository.dao.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel,Long> {
    Optional<SubjectModel> findBySubjectName(String subjectName);
}
