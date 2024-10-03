package ru.custis.course_selection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.custis.course_selection.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
