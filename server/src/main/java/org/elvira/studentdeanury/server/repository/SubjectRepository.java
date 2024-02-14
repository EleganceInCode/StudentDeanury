package org.elvira.studentdeanury.server.repository;

import org.elvira.studentdeanury.server.repository.dao.SubjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectDao,Long> {
}
