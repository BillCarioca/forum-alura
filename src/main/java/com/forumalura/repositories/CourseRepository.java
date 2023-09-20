package com.forumalura.repositories;

import com.forumalura.domain.courses.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByActiveDateTrue(Pageable pageable);
    long count();
}
