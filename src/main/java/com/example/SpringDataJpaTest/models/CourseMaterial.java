package com.example.SpringDataJpaTest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CourseMaterial {

    @Id
    @SequenceGenerator(name="courseMaterial_sequence_generator",
            sequenceName = "courseMaterial_sequence",
            allocationSize = 100,
            initialValue = 101)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "courseMaterial_sequence_generator")
    private Long courseMaterialId;
    private String courseMaterialUrl;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseId"
    )
    private Course course;

}
