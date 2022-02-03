package com.example.SpringDataJpaTest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @SequenceGenerator(name="course_sequence_generator",
            sequenceName = "course_sequence",
            allocationSize = 100,
            initialValue = 101)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "course_sequence_generator")
    private Long courseId;
    private String courseName;
    private Integer credits;

    @OneToOne(
            mappedBy = "course"
    )
    private CourseMaterial courseMaterial;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "teacherId"
    )
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "students_attending_course",
            joinColumns = @JoinColumn(
                    name = "course_id",
                    referencedColumnName = "courseId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id"
            )
    )
    private List<Student> students;
}
