package com.example.SpringDataJpaTest.repos;

import com.example.SpringDataJpaTest.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    public Optional<Student> findByEmail(String email);

    public List<Student> findByEmailContaining(String pattern);

    public List<Student> findByAddressCityNotNull();

    @Query("select s.studentName from Student s where s.email = ?1")
    public Optional<String> findNameByEmail(String email);

    @Query(
            value = "SELECT first_name FROM student_class WHERE e_mail LIKE %:pattern%",
            nativeQuery = true
    )
    public List<String> findNamesByEmailPattern (@Param("pattern") String pattern);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.studentName = ?1 where s.email = ?2")
    public void updateStudentName(String name, String email) ;
}
