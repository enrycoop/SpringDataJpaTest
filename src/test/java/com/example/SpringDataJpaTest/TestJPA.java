package com.example.SpringDataJpaTest;

import com.example.SpringDataJpaTest.models.Student;
import com.example.SpringDataJpaTest.repos.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestJPA {
        @Autowired
        private StudentRepository stRepo;

        @Test
        void jpaTest() {
            Student mary = Student.builder()
                    .studentName("Mary")
                    .email("mary.test@domain.com")
                    .dob(LocalDate.of(1996,02,12))
                    .build();
            Student ben = Student.builder()
                    .studentName("Ben")
                    .email("ben.test@domain.com")
                    .dob(LocalDate.of(1993,12,7))
                    .build();

            stRepo.saveAll(List.of(mary,ben));
            Student test = stRepo.getById(101L);
            assertEquals("Ben", test.getStudentName());
        }

}
