package com.example.SpringDataJpaTest.repos;

import com.example.SpringDataJpaTest.models.Course;
import com.example.SpringDataJpaTest.models.Student;
import com.example.SpringDataJpaTest.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    public Page<Course> findByTeacher(Teacher teacher, Pageable pageable);
}
