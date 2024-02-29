package org.elvira.studentdeanury.server.repository;

import org.elvira.studentdeanury.server.repository.dao.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, UUID> {
}
