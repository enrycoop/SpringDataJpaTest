package com.example.SpringDataJpaTest.service;

import com.example.SpringDataJpaTest.models.Student;
import com.example.SpringDataJpaTest.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository stRepo;

    @Transactional
    public void updateStudentNameByEmail(String email, String newName) throws Exception {
        Student s = stRepo.findByEmail(email).orElseThrow(() -> new Exception("Student Not Found!"));
        s.setStudentName(newName);
    }
}
